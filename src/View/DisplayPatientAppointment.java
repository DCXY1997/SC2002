package src.View;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.Controller.AppointmentController;
import src.Controller.DoctorController;
import src.Enum.AppointmentStatus;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;

public class DisplayPatientAppointment extends MainView {

    private Patient patient;

    public DisplayPatientAppointment(Patient patient) {
        if (patient == null) {
            System.out.println("Error: Patient is null in DisplayPatientAppointment.");
            throw new IllegalArgumentException("Patient cannot be null");
        }
        this.patient = patient;
        System.out.println("DisplayPatientAppointment initialized with patient: " + patient.getPatientId());
    }

    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Patient View > View Available Appointments");
        System.out.println("What would you like to do?");
        System.out.println("(1) Schedule Appointment");
        System.out.println("(2) View Your Appointments");
        System.out.println("(3) Reschedule Appointment");
        System.out.println("(4) Cancel Appointment");
        System.out.println("(5) Back");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 5);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Schedule Appointment");
                    promptScheduleAppointment();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > View Appointments");
                    viewAppointmentRequests();
                    break;
                case 3:
                    // Reschedule an appointment
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Reschedule Appointment");
                    promptRescheduleAppointment();
                    break;
                case 4:
                    // Cancel an appointment
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Cancel Appointment");
                    cancelAppointment();
                    break;
                case 5:
                    return; // Return from the method to exit the loop
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 5) {
                Helper.pressAnyKeyToContinue();
            }
        } while (true);
    }

    private void promptRescheduleAppointment() {
        // Display appointments to the user
        List<Appointment> appointments = AppointmentController.viewPatientAppointments(patient);
        List<Appointment> pendingAppointments = new ArrayList<>();

        // Filter only pending appointments
        for (Appointment appointment : appointments) {
            if (appointment.getStatus() == AppointmentStatus.PENDING) {
                pendingAppointments.add(appointment);
            }
        }

        if (pendingAppointments.isEmpty()) {
            System.out.println("You have no pending appointments to reschedule.");
            return;
        }

        System.out.println("Your Pending Appointments:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            Appointment appointment = pendingAppointments.get(i);
            System.out.println((i + 1) + ". " + appointment.getAppointmentId() + " - Doctor: " + appointment.getAttendingDoctor().getName()
                    + " from " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
        }

        System.out.println("Select an appointment to reschedule (1-" + pendingAppointments.size() + "): ");
        int choice = Helper.readInt(1, pendingAppointments.size());
        Appointment selectedAppointment = pendingAppointments.get(choice - 1);

        System.out.println("You selected appointment ID " + selectedAppointment.getAppointmentId() + " with Dr. "
                + selectedAppointment.getAttendingDoctor().getName());

        // Prompt for new appointment time within the doctor's availability
        Schedule newSchedule = promptNewScheduleForReschedule(selectedAppointment.getAttendingDoctor(), selectedAppointment);
        if (newSchedule != null) {
            boolean rescheduled = AppointmentController.rescheduleAppointment(selectedAppointment, newSchedule);
            if (rescheduled) {
                System.out.println("Your appointment has been rescheduled successfully.");
            } else {
                System.out.println("Failed to reschedule the appointment. The selected time may not be within the doctor's availability.");
            }
        }
    }

    private Schedule promptNewScheduleForReschedule(Doctor doctor, Appointment currentAppointment) {
        List<Schedule> availableSchedules = doctor.getAvailability();
        if (availableSchedules.isEmpty()) {
            System.out.println("The doctor has no available schedules for rescheduling.");
            return null;
        }

        // Display the available schedule times for reference
        System.out.println("The doctor's available time slots for rescheduling are:");
        for (int i = 0; i < availableSchedules.size(); i++) {
            Schedule schedule = availableSchedules.get(i);
            System.out.println((i + 1) + ". From " + schedule.getStartTime() + " to " + schedule.getEndTime());
        }
        System.out.println("------------------------------------------------------------");

        // Prompt user to input a new start and end time for their appointment
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Ask for the appointment date input (format: yyyy-MM-dd)
        System.out.print("Enter the new appointment date (Format: yyyy-MM-dd): ");
        String dateStr = Helper.readString();  // Ensure Helper.readString() gets the date input correctly
        LocalDate appointmentDate = LocalDate.parse(dateStr);  // Convert input string to LocalDate

        // Ask for the start time input
        System.out.print("Enter the new start time for your appointment (Format: HH:mm): ");
        String startTimeStr = Helper.readString();
        String fullStartTimeStr = appointmentDate.toString() + " " + startTimeStr;
        LocalDateTime startTime = LocalDateTime.parse(fullStartTimeStr, formatter);

        // Ask for the end time input
        System.out.print("Enter the new end time for your appointment (Format: HH:mm): ");
        String endTimeStr = Helper.readString();
        String fullEndTimeStr = appointmentDate.toString() + " " + endTimeStr;
        LocalDateTime endTime = LocalDateTime.parse(fullEndTimeStr, formatter);

        // Helper method 1: Check if the time is within doctor's availability
        boolean isTimeAvailable = false;
        for (Schedule schedule : availableSchedules) {
            // Check if the requested appointment's start and end times are within the doctor's available time
            if (!startTime.isBefore(schedule.getStartTime()) && !endTime.isAfter(schedule.getEndTime())) {
                isTimeAvailable = true;
                break;
            }
        }

        if (!isTimeAvailable) {
            System.out.println("The selected time is not within the doctor's available schedule.");
            return null;
        }

// Check if the selected time slot overlaps with any other appointments
        List<Appointment> doctorAppointments = AppointmentController.viewDoctorAppointments(doctor);
        for (Appointment appointment : doctorAppointments) {
            // Skip the current appointment (the one being rescheduled)
            if (appointment.getAppointmentId().equals(currentAppointment.getAppointmentId())) {
                continue; // Skip the current appointment
            }

            // Check for overlap with other appointments
            if (startTime.isBefore(appointment.getAppointmentEndDate()) && endTime.isAfter(appointment.getAppointmentStartDate())) {
                System.out.println("The selected time slot is already occupied by another appointment.");
                return null;
            }
        }

        // If everything is fine, create and return a new Schedule object
        Schedule newSchedule = new Schedule(startTime, endTime);

        return newSchedule;
    }

    private void cancelAppointment() {
        List<Appointment> appointments = AppointmentController.viewPatientAppointments(patient);
        List<Appointment> pendingAppointments = new ArrayList<>();

        // Filter only pending appointments
        for (Appointment appointment : appointments) {
            if (appointment.getStatus() == AppointmentStatus.PENDING) {
                pendingAppointments.add(appointment);
            }
        }

        if (pendingAppointments.isEmpty()) {
            System.out.println("You have no pending appointments to cancel.");
            return;
        }

        System.out.println("Your Pending Appointments:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            Appointment appointment = pendingAppointments.get(i);
            System.out.println((i + 1) + ". Appointment ID: " + appointment.getAppointmentId() + " - Doctor: "
                    + appointment.getAttendingDoctor().getName() + " from " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
        }

        System.out.print("Select an appointment to cancel (1-" + pendingAppointments.size() + "): ");
        int choice = Helper.readInt(1, pendingAppointments.size());
        Appointment selectedAppointment = pendingAppointments.get(choice - 1);

        boolean cancelled = AppointmentController.cancelAppointment(selectedAppointment);
        if (cancelled) {
            System.out.println("Your appointment has been cancelled successfully.");
        } else {
            System.out.println("Failed to cancel the appointment. Please try again.");
        }
    }

    private void viewAppointmentRequests() {
        List<Appointment> appointmentRequests = AppointmentController.viewPatientAppointments(patient);

        if (appointmentRequests.isEmpty()) {
            System.out.println("No appointment requests found for you.");
        } else {
            System.out.println("Your Appointment Requests:");

            for (Appointment appointment : appointmentRequests) {
                System.out.println("Appointment ID - " + appointment.getAppointmentId() + ":");
                System.out.println("Patient: " + appointment.getPatient().getName());
                System.out.println("Doctor: " + appointment.getAttendingDoctor().getName());
                System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println();
            }
        }
    }

    private void promptScheduleAppointment() {
        boolean scheduledAppointment = false;
        do {
            System.out.println("(1) View Appointments by Doctor");
            System.out.println("(2) View All Available Appointments");
            System.out.println("(3) Back");

            int opt = Helper.readInt(1, 3);
            switch (opt) {
                case 1:
                    scheduledAppointment = promptDisplayApptByDoctor();
                    break;
                case 2:
                    scheduledAppointment = promptAllAvailableAppt();
                    break;
                case 3:
                    return; // Return from the method to exit the loop
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (!scheduledAppointment) {
                Helper.pressAnyKeyToContinue();
            }
        } while (!scheduledAppointment);
    }

    private boolean promptDisplayApptByDoctor() {
        List<Doctor> availableDoc = DoctorController.getAllDoctors();

        if (availableDoc.isEmpty()) {
            System.out.println("No doctors available.");
            return false;
        }

        System.out.println("Available Doctors:");
        for (int i = 0; i < availableDoc.size(); i++) {
            Doctor doctor = availableDoc.get(i);
            System.out.println((i + 1) + ". " + doctor.getName() + " (" + doctor.getHospitalId() + ") - " + doctor.getDocSpecialization());
        }

        System.out.println("\nPlease select a doctor to view available appointments: ");
        int choice = Helper.readInt(1, availableDoc.size());

        Doctor selectedDoctor = availableDoc.get(choice - 1);
        System.out.println("You selected Dr. " + selectedDoctor.getName() + " (" + selectedDoctor.getDocSpecialization() + ")");
        System.out.println("-----------------------------------------------------------");
        List<Schedule> doctorSchedule = DoctorController.getSchedule(selectedDoctor);

        if (doctorSchedule.isEmpty()) {
            System.out.println("No available appointment slots for this doctor.");
            return false;
        }

        System.out.println("Available Appointment Slots:");
        int index = 1;
        for (Schedule schedule : doctorSchedule) {
            System.out.println(index + ". From: " + schedule.getStartTime() + " To: " + schedule.getEndTime());
            index++;
        }

        // Now let the user select a slot
        System.out.print("Please select an appointment slot by entering the number: ");
        choice = Helper.readInt(1, doctorSchedule.size());

        Schedule selectedSchedule = doctorSchedule.get(choice - 1);
        return scheduleAppointment(selectedDoctor, selectedSchedule);
    }

    private boolean promptAllAvailableAppt() {
        List<Doctor> availableDoctors = DoctorController.getAllDoctors();

        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors available.");
            return false;
        }

        boolean hasAvailableSlots = false;
        System.out.println("All Available Appointment Slots:");

        List<Schedule> allAvailableSchedules = new ArrayList<>();
        List<Doctor> associatedDoctors = new ArrayList<>();
        int index = 1;

        // Loop through all available doctors
        for (Doctor doctor : availableDoctors) {
            List<Schedule> doctorSchedule = DoctorController.getSchedule(doctor);
            if (!doctorSchedule.isEmpty()) {
                hasAvailableSlots = true;
                System.out.println("Dr. " + doctor.getName() + " (" + doctor.getDocSpecialization() + "):");

                // List all the available time slots for each doctor
                for (Schedule schedule : doctorSchedule) {
                    System.out.println(index + ". From: " + schedule.getStartTime() + " To: " + schedule.getEndTime());
                    allAvailableSchedules.add(schedule);
                    associatedDoctors.add(doctor);
                    index++;
                }
                System.out.println();
            }
        }

        if (!hasAvailableSlots) {
            System.out.println("No available appointment slots for any doctor.");
            return false;
        }

        // Now, let the user select a slot
        System.out.println("\nPlease select an appointment slot by entering the number: ");
        int choice = Helper.readInt(1, allAvailableSchedules.size());

        // Call the scheduleAppointment method to proceed with scheduling
        Schedule selectedSchedule = allAvailableSchedules.get(choice - 1);
        Doctor selectedDoctor = associatedDoctors.get(choice - 1);
        return scheduleAppointment(selectedDoctor, selectedSchedule);
    }

    private boolean scheduleAppointment(Doctor selectedDoctor, Schedule selectedSchedule) {
        // Prompt user for the desired start time and end time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String scheduleDate = selectedSchedule.getStartTime().toLocalDate().toString();

        System.out.print("Enter the start time for your appointment (Format: HH:mm): ");
        String startTimeStr = Helper.readString();
        String fullStartTimeStr = scheduleDate + " " + startTimeStr;
        LocalDateTime startTime = LocalDateTime.parse(fullStartTimeStr, formatter);

        System.out.print("Enter the end time for your appointment (Format: HH:mm): ");
        String endTimeStr = Helper.readString();
        String fullEndTimeStr = scheduleDate + " " + endTimeStr;
        LocalDateTime endTime = LocalDateTime.parse(fullEndTimeStr, formatter);

        boolean appointmentMade = AppointmentController.makeAppointment(patient, selectedDoctor, selectedSchedule, startTime, endTime);

        if (appointmentMade) {
            return true;
        } else {
            System.out.println("Failed to make the appointment. Please try again.");
            return false;
        }
    }
}
