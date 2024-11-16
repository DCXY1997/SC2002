package src.Controller;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import src.Enum.AppointmentStatus;
import src.Helper.Helper;
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

    public static String generateAppointmentId() {
        String prefix = "A";
        int uniqueId = Helper.generateUniqueId(Repository.APPOINTMENT_LIST);
        return prefix + String.format("%03d", uniqueId);
    }

    public static boolean makeAppointment(Patient patient, Doctor doctor, Schedule schedule, LocalDateTime startTime, LocalDateTime endTime) {
        if (schedule == null || patient == null || doctor == null || startTime == null || endTime == null) {
            System.out.println("Invalid data. One or more required fields are missing.");
            return false;
        }

        // Check if the selected start and end time fit within the available schedule
        if (startTime.isBefore(schedule.getStartTime()) || endTime.isAfter(schedule.getEndTime())) {
            System.out.println("The selected times are outside the available slot: " + schedule.getStartTime() + " - " + schedule.getEndTime());
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

        // Check for conflicting appointments within the selected schedule
        for (Appointment existingAppointment : Repository.APPOINTMENT_LIST.values()) {
            if (existingAppointment.getAttendingDoctor().equals(doctor)
                    && existingAppointment.getStatus() != AppointmentStatus.CANCELLED) {
                LocalDateTime existingStart = existingAppointment.getAppointmentStartDate();
                LocalDateTime existingEnd = existingAppointment.getAppointmentEndDate();
                // Check if the time overlaps with any existing appointment
                if (startTime.isBefore(existingEnd) && endTime.isAfter(existingStart)) {
                    System.out.println("\nThe selected time slot is already booked from " + existingStart + " to " + existingEnd);
                    return false;
                }
            }
        }

        // Create and add the new appointment
        Appointment appointment = new Appointment(patient, doctor, startTime, endTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setAppointmentId(generateAppointmentId());

        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);

        // Persist the updated appointment list
        Repository.persistData(FileType.APPOINTMENT_LIST);
        System.out.println("Appointment " + appointment.getAppointmentId() + " successfully created!");
        return true;
    }

    public static List<Appointment> viewPatientAppointments(Patient patient) {
        List<Appointment> patientAppointments = new ArrayList<>();
        Repository.readData(FileType.APPOINTMENT_LIST);
        for (Appointment appointment : Repository.APPOINTMENT_LIST.values()) {
            if (appointment.getPatient().equals(patient)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

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

        LocalDateTime newStartTime = newSchedule.getStartTime();
        LocalDateTime newEndTime = newSchedule.getEndTime();
        appointment.setAppointmentStartDate(newStartTime);
        appointment.setAppointmentEndDate(newEndTime);
        appointment.setStatus(AppointmentStatus.PENDING);

        // Update the repository with the rescheduled appointment
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
        // Fetch available slots for the selected doctor (with pending appointments excluded)
        List<Schedule> availableSlots = AppointmentController.getAvailableSlotsForDoctorExcludingAppointment(doctor, appointment);

        // Update the doctor's availability for the specific appointment date
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        boolean updateSuccessful = DoctorController.updateDoctorAvailability(
                doctor,
                appointmentStart,
                appointmentEnd,
                availableSlots
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
        System.out.println("\nAppointment accepted. Doctor's availability has been updated to:");
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

    public static List<Appointment> viewConfirmAppointments(Doctor doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

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

    public static List<Appointment> viewCompleteAppointments(Doctor doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

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

    public static List<Appointment> viewCompleteAppointments(Patient patient) {
        List<Appointment> patientAppointments = new ArrayList<>();

        // Load appointments from the Repository (appointmentlist.dat)
        Repository.readData(FileType.APPOINTMENT_LIST);

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

    public static List<Schedule> getAvailableSlotsForDoctor(Doctor doctor) {
        List<Schedule> availableSlots = new ArrayList<>();
        List<Schedule> doctorSchedule = doctor.getAvailability();
        List<Appointment> allAppointments = viewDoctorAppointments(doctor);
        // Filter for pending and cancelled appointments
        List<Appointment> relevantAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.PENDING)
                .collect(Collectors.toList());

        relevantAppointments.sort(Comparator.comparing(Appointment::getAppointmentStartDate));
        for (Schedule schedule : doctorSchedule) {
            LocalDateTime currentStart = schedule.getStartTime();
            LocalDateTime currentEnd = schedule.getEndTime();
            // Iterate through all relevant appointments and check for overlaps
            for (Appointment appointment : relevantAppointments) {
                LocalDateTime appointmentStart = appointment.getAppointmentStartDate();
                LocalDateTime appointmentEnd = appointment.getAppointmentEndDate();

                // If the appointment overlaps with the schedule slot
                if (appointmentStart.isBefore(currentEnd) && appointmentEnd.isAfter(currentStart)) {
                    // If the appointment starts after the current start time, add the slot before it
                    if (appointmentStart.isAfter(currentStart)) {
                        availableSlots.add(new Schedule(currentStart, appointmentStart));
                    }
                    // Set the new start time to the end of the current appointment
                    currentStart = appointmentEnd;
                }
            }

            // After checking all relevant appointments, if there's remaining time, add it as an available slot
            if (currentStart.isBefore(currentEnd)) {
                availableSlots.add(new Schedule(currentStart, currentEnd));
            }
        }
        return availableSlots;
    }

    public static List<Schedule> getAvailableSlotsForDoctorExcludingAppointment(Doctor doctor, Appointment appointmentToAccept) {
        List<Schedule> availableSlots = new ArrayList<>();
        List<Schedule> doctorSchedule = DoctorController.getSchedule(doctor);

        // Sort doctor’s availability for consistency
        doctorSchedule.sort(Comparator.comparing(Schedule::getStartTime));

        // Start and end time of the appointment to accept
        LocalDateTime appointmentStart = appointmentToAccept.getAppointmentStartDate();
        LocalDateTime appointmentEnd = appointmentToAccept.getAppointmentEndDate();

        for (Schedule schedule : doctorSchedule) {
            LocalDateTime currentStart = schedule.getStartTime();
            LocalDateTime currentEnd = schedule.getEndTime();

            // Check if the appointment overlaps with this schedule slot
            if (appointmentStart.isBefore(currentEnd) && appointmentEnd.isAfter(currentStart)) {
                // If the appointment starts after the current start time, add the slot before it
                if (appointmentStart.isAfter(currentStart)) {
                    availableSlots.add(new Schedule(currentStart, appointmentStart));
                }

                // If the appointment ends before the current end time, add the slot after it
                if (appointmentEnd.isBefore(currentEnd)) {
                    availableSlots.add(new Schedule(appointmentEnd, currentEnd));
                }
            } else {
                // If no overlap, add the entire schedule slot
                availableSlots.add(schedule);
            }
        }
        return availableSlots;
    }

}
