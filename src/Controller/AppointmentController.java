package src.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.Enum.AppointmentStatus;
import src.Model.Appointment;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;
import src.Repository.FileType;
import src.Repository.Repository;

public class AppointmentController {

    private static List<Appointment> appointments = new ArrayList<>();

    public static List<Appointment> getAllAppointments() {
        return appointments;
    }

    public static boolean makeAppointment(Patient patient, Doctor doctor, Schedule schedule, LocalDateTime startTime, LocalDateTime endTime) {
        if (schedule == null || patient == null || doctor == null || startTime == null || endTime == null) {
            System.out.println("Invalid data. One or more required fields are missing.");
            return false;
        }

        // Check if the selected start and end time fit within the available schedule
        if (startTime.isBefore(schedule.getStartTime()) || endTime.isAfter(schedule.getEndTime())) {
            System.out.println("The selected times are outside the available slot.");
            return false;
        }

        // Calculate the duration of the appointment
        long duration = java.time.Duration.between(startTime, endTime).toMinutes();
        if (duration <= 0) {
            System.out.println("The end time must be after the start time.");
            return false;
        }

        // Load existing appointments
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Create and add the new appointment
        Appointment appointment = new Appointment(patient, doctor, startTime, endTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);

        // Persist the updated data
        Repository.persistData(FileType.APPOINTMENT_LIST);

        System.out.println("Appointment successfully created!");
        return true;
    }

    public static List<Appointment> viewPatientAppointments(Patient patient) {
        List<Appointment> patientAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Check if the appointments are loaded correctly
        //System.out.println("Loaded appointments: " + Repository.APPOINTMENT_LIST.size());
        // Get the appointments from the Repository
        for (Appointment appointment : Repository.APPOINTMENT_LIST.values()) {
            if (appointment.getPatient().equals(patient)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

    // FIX THIS!!!
    public static boolean rescheduleAppointment(Appointment appointment, Schedule newSchedule) {
        if (appointment == null || newSchedule == null) {
            System.out.println("Invalid appointment or schedule.");
            return false;
        }

        // Check if the appointment is pending
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            System.out.println("Appointment cannot be rescheduled because it is not pending.");
            return false;
        }

        // Get the doctor and their availability
        Doctor doctor = appointment.getAttendingDoctor();
        List<Schedule> doctorSchedules = doctor.getAvailability();

        // Check if the new schedule fits within the doctor's availability
        boolean fits = false;
        LocalDateTime newStartTime = newSchedule.getStartTime();
        LocalDateTime newEndTime = newSchedule.getEndTime();

        for (Schedule schedule : doctorSchedules) {
            if (!newStartTime.isBefore(schedule.getStartTime()) && !newEndTime.isAfter(schedule.getEndTime())) {
                fits = true;
                break;
            }
        }

        if (!fits) {
            System.out.println("The new appointment time exceeds the doctor's available schedule.");
            return false;
        }

        // If it fits, update the appointment's start and end times
        appointment.setAppointmentStartDate(newStartTime);
        appointment.setAppointmentEndDate(newEndTime);
        appointment.setStatus(AppointmentStatus.PENDING); // Reset status for rescheduling

        // Save the updated appointment to the repository
        Repository.readData(FileType.APPOINTMENT_LIST);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        Repository.persistData(FileType.APPOINTMENT_LIST);

        System.out.println("Appointment successfully rescheduled!");
        return true;
    }

    public static boolean cancelAppointment(Appointment appointment) {
        if (appointment == null) {
            System.out.println("Invalid appointment.");
            return false;
        }

        // Check if the appointment is pending
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            System.out.println("Appointment cannot be canceled because it is not pending.");
            return false;
        }

        // Set the status to CANCELLED
        appointment.setStatus(AppointmentStatus.CANCELLED);

        // Save the updated appointment
        Repository.readData(FileType.APPOINTMENT_LIST);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        Repository.persistData(FileType.APPOINTMENT_LIST);

        return true;
    }

    // Doctor
    public static List<Appointment> viewDoctorAppointments(Doctor doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Check if the appointments are loaded correctly
        //System.out.println("Loaded appointments: " + Repository.APPOINTMENT_LIST.size());
        // Get the appointments from the Repository
        for (Appointment appointment : Repository.APPOINTMENT_LIST.values()) {
            // Check if the appointment is for the given doctor
            if (appointment.getAttendingDoctor().equals(doctor)) {
                doctorAppointments.add(appointment);
            }
        }
        // Return the list of appointments for the doctor
        return doctorAppointments;
    }

    public static void acceptAppointment(Doctor doctor, Appointment appointment) {
        LocalDateTime appointmentStart = appointment.getAppointmentStartDate();
        LocalDateTime appointmentEnd = appointment.getAppointmentEndDate();

        LocalDate appointmentDate = appointmentStart.toLocalDate();
        System.out.println("-------------------------------------------------------------");
        System.out.println("\nDoctor's current availability for " + appointmentDate + ": ");

        // Fetch the doctor's full schedule
        List<Schedule> doctorSchedules = DoctorController.getSchedule(doctor);

        // Filter the schedules for the appointment date
        List<Schedule> filteredSchedules = new ArrayList<>();
        for (Schedule schedule : doctorSchedules) {
            if (schedule.getStartTime().toLocalDate().isEqual(appointmentDate)) {
                filteredSchedules.add(schedule);
                System.out.println("  Available from " + schedule.getStartTime() + " to " + schedule.getEndTime());
            }
        }

        // If the appointment's times fit into the available schedule, proceed with the acceptance
        boolean fits = false;
        for (Schedule schedule : filteredSchedules) {
            if (!appointmentStart.isBefore(schedule.getStartTime()) && !appointmentEnd.isAfter(schedule.getEndTime())) {
                fits = true;
                break;
            }
        }

        if (!fits) {
            System.out.println("The selected appointment does not fit within the doctor's availability.");
            return;
        }

        // Proceed with accepting the appointment (update availability and status)
        appointment.setStatus(AppointmentStatus.CONFIRMED);

        // Update the doctor's availability for the specific appointment date
        boolean updateSuccessful = DoctorController.updateDoctorAvailability(
                doctor,
                appointmentStart,
                appointmentEnd,
                filteredSchedules
        );

        if (!updateSuccessful) {
            System.out.println("Update doctor availability error.");
            return;
        }

        // Persist the updated appointment status and doctor's schedule
        Repository.readData(FileType.APPOINTMENT_LIST);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        Repository.persistData(FileType.APPOINTMENT_LIST);

        // Print updated doctor's availability after the appointment
        System.out.println("Appointment accepted. Doctor's availability has been updated to:");
        for (Schedule updatedSchedule : doctor.getAvailability()) {
            System.out.println("  Available from " + updatedSchedule.getStartTime() + " to " + updatedSchedule.getEndTime());
        }
    }

    public static void declineAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CANCELLED);
        Repository.readData(FileType.APPOINTMENT_LIST);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        Repository.persistData(FileType.APPOINTMENT_LIST);

    }

    public static List<Appointment> viewConfirmAppointments(Doctor doctor){
        List<Appointment> doctorAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Check if the appointments are loaded correctly
        //System.out.println("Loaded appointments: " + Repository.APPOINTMENT_LIST.size());
        // Get the appointments from the Repository
        for (Appointment appointment : Repository.APPOINTMENT_LIST.values()) {
            // Check if the appointment is for the given doctor
            if (appointment.getAttendingDoctor().equals(doctor) && appointment.getStatus() == AppointmentStatus.CONFIRMED) {
                doctorAppointments.add(appointment);
            }
        }
        // Return the list of appointments for the doctor
        return doctorAppointments;
    }

    public static List<Appointment> viewCompleteAppointments(Doctor doctor){
        List<Appointment> doctorAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Check if the appointments are loaded correctly
        //System.out.println("Loaded appointments: " + Repository.APPOINTMENT_LIST.size());
        // Get the appointments from the Repository
        for (Appointment appointment : Repository.APPOINTMENT_LIST.values()) {
            // Check if the appointment is for the given doctor
            if (appointment.getAttendingDoctor().equals(doctor) && appointment.getStatus() == AppointmentStatus.COMPLETED) {
                doctorAppointments.add(appointment);
            }
        }
        // Return the list of appointments for the doctor
        return doctorAppointments;
    }

    public static List<Appointment> viewCompleteAppointments(Patient patient){
        List<Appointment> patientAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Check if the appointments are loaded correctly
        //System.out.println("Loaded appointments: " + Repository.APPOINTMENT_LIST.size());
        // Get the appointments from the Repository
        for (Appointment appointment : Repository.APPOINTMENT_LIST.values()) {
            // Check if the appointment is for the given doctor
            if (appointment.getPatient().equals(patient) && appointment.getStatus() == AppointmentStatus.COMPLETED) {
                patientAppointments.add(appointment);
            }
        }
        // Return the list of appointments for the doctor
        return patientAppointments;
    }
    // public static List<Appointment> getUpcomingAppointmentsForDoctor(Doctor doctor) {
    //     List<Appointment> upcomingAppointments = new ArrayList<>();
    //     LocalDateTime now = LocalDateTime.now();
    //     for (Appointment appointment : appointments) {
    //         if (appointment.getAttendingDoctor().equals(doctor) && appointment.getAppointmentDate().isAfter(now)) {
    //             upcomingAppointments.add(appointment);
    //         }
    //     }
    //     return upcomingAppointments;
    // }
    // public static List<Appointment> getPastAppointmentsForDoctor(Doctor doctor) {
    //     List<Appointment> pastAppointments = new ArrayList<>();
    //     LocalDateTime now = LocalDateTime.now();
    //     for (Appointment appointment : appointments) {
    //         if (appointment.getAttendingDoctor().equals(doctor) && appointment.getAppointmentDate().isBefore(now)) {
    //             pastAppointments.add(appointment);
    //         }
    //     }
    //     return pastAppointments;
    // }
}
