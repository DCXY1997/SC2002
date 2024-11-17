package src.View;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import src.Controller.AppointmentController;
import src.Controller.DoctorController;
import src.Enum.AppointmentStatus;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;
import src.Model.Specialization;

/**
 * The {@code DisplayPatientAppointment} class manages the patient's interface
 * for handling appointments.
 * <p>
 * This class allows patients to:
 * <ul>
 * <li>Schedule appointments with available doctors</li>
 * <li>View their appointments</li>
 * <li>Reschedule pending appointments</li>
 * <li>Cancel pending appointments</li>
 * </ul>
 * <p>
 * This class is part of the {@code View} layer in the application.
 * </p>
 *
 * @author Jasmine Tye Jia Wen
 * @version 1.0
 * @since 2024-11-17
 */
public class DisplayPatientAppointment extends MainView {

    private Patient patient;

    /**
     * Constructs a {@code DisplayPatientAppointment} object for the specified
     * patient.
     *
     * @param patient The patient associated with this view.
     * @throws IllegalArgumentException If the patient is {@code null}.
     */
    public DisplayPatientAppointment(Patient patient) {
        if (patient == null) {
            System.out.println("Error: Patient is null in DisplayPatientAppointment.");
            throw new IllegalArgumentException("Patient cannot be null");
        }
        this.patient = patient;
        System.out.println("DisplayPatientAppointment initialized with patient: " + patient.getPatientId());
    }

    /**
     * Prints the available actions for the patient.
     */
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

    /**
     * Handles the patient's interactions with appointments.
     */
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
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Reschedule Appointment");
                    promptRescheduleAppointment();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Cancel Appointment");
                    cancelAppointment();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 5) {
                Helper.pressAnyKeyToContinue();
            }
        } while (true);
    }

    /**
     * Prompts the patient to reschedule a pending appointment.
     */
    private void promptRescheduleAppointment() {
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
            System.out.println((i + 1) + ". Appointment ID - " + appointment.getAppointmentId() + ":");
            System.out.println("Patient: " + appointment.getPatient().getName());
            System.out.println("Doctor: " + appointment.getAttendingDoctor().getName());
            System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println();
        }

        System.out.println("\nSelect an appointment to reschedule (1-" + pendingAppointments.size() + "): ");
        int choice = Helper.readInt(1, pendingAppointments.size());
        Appointment selectedAppointment = pendingAppointments.get(choice - 1);

        System.out.println("You selected Appointment - " + selectedAppointment.getAppointmentId() + ":");
        System.out.println("Patient: " + selectedAppointment.getPatient().getName());
        System.out.println("From: " + selectedAppointment.getAppointmentStartDate() + " to " + selectedAppointment.getAppointmentEndDate());
        System.out.println("Status: " + selectedAppointment.getStatus() + "\n");

        // Prompt for new appointment time within the doctor's availability
        Schedule newSchedule = promptNewScheduleForReschedule(selectedAppointment.getAttendingDoctor(), selectedAppointment);
        if (newSchedule != null) {
            boolean rescheduled = AppointmentController.rescheduleAppointment(selectedAppointment, newSchedule);
            if (!rescheduled) {
                System.out.println("Failed to reschedule the appointment. The selected time may not be within the doctor's availability.");
            }
        }
    }

    /**
     * Prompts the patient to reschedule a new appointment.
     */
    private Schedule promptNewScheduleForReschedule(Doctor doctor, Appointment currentAppointment) {
        List<Schedule> availableSchedules = doctor.getAvailability();
        if (availableSchedules.isEmpty()) {
            System.out.println("The doctor has no available schedules for rescheduling.");
            return null;
        }

        // Fetch available slots for the selected doctor
        List<Schedule> availableSlots = AppointmentController.getAvailableSlotsForDoctor(doctor);
        availableSlots.add(new Schedule(currentAppointment.getAppointmentStartDate(), currentAppointment.getAppointmentEndDate()));

        availableSlots.sort(Comparator.comparing(Schedule::getStartTime));
        List<Schedule> mergedSlots = new ArrayList<>();
        Schedule tempSlot = availableSlots.get(0);

        // Merge overlapping slots
        for (int i = 1; i < availableSlots.size(); i++) {
            Schedule currentSlot = availableSlots.get(i);
            if (!currentSlot.getStartTime().isAfter(tempSlot.getEndTime())) {
                tempSlot = new Schedule(tempSlot.getStartTime(),
                        currentSlot.getEndTime().isAfter(tempSlot.getEndTime()) ? currentSlot.getEndTime() : tempSlot.getEndTime());
            } else {
                mergedSlots.add(tempSlot);
                tempSlot = currentSlot;
            }
        }
        mergedSlots.add(tempSlot);

        Map<String, List<Schedule>> groupedSlots = new HashMap<>();
        for (Schedule slot : mergedSlots) {
            String date = slot.getStartTime().toLocalDate().toString();
            groupedSlots.computeIfAbsent(date, k -> new ArrayList<>()).add(slot);
        }

        System.out.println("Available Appointment Slots For Dr. " + doctor.getName().toUpperCase() + ":");
        int index = 1;
        for (Map.Entry<String, List<Schedule>> entry : groupedSlots.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println("-------------");

            for (Schedule slot : entry.getValue()) {
                String timeRange = slot.getStartTime().toLocalTime() + " - " + slot.getEndTime().toLocalTime();
                System.out.println("(" + index + ") From: " + timeRange);
                index++;
            }
            System.out.println();
        }

        System.out.print("Please select an appointment slot by entering the number: ");
        int option = Helper.readInt(1, mergedSlots.size());
        Schedule selectedSchedule = mergedSlots.get(option - 1);

        String scheduleDate = selectedSchedule.getStartTime().toLocalDate().toString();
        LocalDateTime[] appointmentTimes = Helper.validateAppointmentTime(scheduleDate,
                "Enter the start time for your appointment (Format: HH:mm): ",
                "Enter the end time for your appointment (Format: HH:mm): ");
        LocalDateTime startTime = appointmentTimes[0];
        LocalDateTime endTime = appointmentTimes[1];

        // Check if the selected time is within the doctor's availability
        boolean isTimeAvailable = false;
        for (Schedule schedule : availableSchedules) {
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
        List<Appointment> doctorAppointments = AppointmentController.viewDoctorAppointments(doctor).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.PENDING || appointment.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());

        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentId().equals(currentAppointment.getAppointmentId())) {
                // Allow rescheduling if the time slot overlaps only with the current appointment
                if (startTime.isBefore(appointment.getAppointmentEndDate()) && endTime.isAfter(appointment.getAppointmentStartDate())) {
                    //System.out.println("Rescheduling allowed as the time slot overlaps with the current appointment.");
                    continue;
                }

                if (startTime.isBefore(appointment.getAppointmentEndDate()) && endTime.isAfter(appointment.getAppointmentStartDate())) {
                    System.out.println("The selected time slot is already occupied by another appointment.");
                    return null;
                }
            }
        }

        return new Schedule(startTime, endTime);
    }

    /**
     * Prompts the patient to cancel a pending appointment.
     */
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
            System.out.println((i + 1) + ". Appointment ID - " + appointment.getAppointmentId() + ":");
            System.out.println("Patient: " + appointment.getPatient().getName());
            System.out.println("Doctor: " + appointment.getAttendingDoctor().getName());
            System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println();
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

    /**
     * Displays the patient's appointment requests grouped by date.
     */
    private void viewAppointmentRequests() {
        List<Appointment> appointmentRequests = AppointmentController.viewPatientAppointments(patient);

        if (appointmentRequests.isEmpty()) {
            System.out.println("No appointment requests found for you.");
            return;
        }

        // Group appointments by the date (appointment start date)
        Map<LocalDate, List<Appointment>> appointmentsByDate = new HashMap<>();
        for (Appointment appointment : appointmentRequests) {
            LocalDate appointmentDate = appointment.getAppointmentStartDate().toLocalDate();
            appointmentsByDate.computeIfAbsent(appointmentDate, k -> new ArrayList<>()).add(appointment);
        }

        System.out.println("Your Appointment Requests:");
        for (LocalDate date : appointmentsByDate.keySet()) {
            System.out.println(date);
            System.out.println("-------------");
            List<Appointment> appointmentsOnDate = appointmentsByDate.get(date);
            for (int i = 0; i < appointmentsOnDate.size(); i++) {
                Appointment appointment = appointmentsOnDate.get(i);
                System.out.println((i + 1) + ". Appointment ID - " + appointment.getAppointmentId() + ":");
                System.out.println("Patient: " + appointment.getPatient().getName());
                System.out.println("Doctor: " + appointment.getAttendingDoctor().getName());
                System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Prompts the patient to schedule an appointment.
     */
    private void promptScheduleAppointment() {
        boolean scheduledAppointment = false;
        do {
            System.out.println("(1) View Appointments by Doctor");
            System.out.println("(2) View All Available Appointments");
            System.out.println("(3) Back");

            int opt = Helper.readInt(1, 3);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Schedule Appointment");
                    scheduledAppointment = promptDisplayApptByDoctor();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Patient Dashboard > Schedule Appointment");
                    scheduledAppointment = promptAllAvailableAppt();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (!scheduledAppointment) {
                Helper.pressAnyKeyToContinue();
                Helper.clearScreen();
                printBreadCrumbs("Hospital Management App View > Patient Dashboard > Schedule Appointment");
            }
        } while (!scheduledAppointment);
    }

    /**
     * Prompts the patient to view available appointments by doctor.
     *
     * @return {@code true} if an appointment was scheduled successfully;
     * {@code false} otherwise.
     */
    public boolean promptDisplayApptByDoctor() {
        List<Doctor> availableDoc = DoctorController.getAllDoctors();

        if (availableDoc.isEmpty()) {
            System.out.println("No doctors available.");
            return false;
        }

        System.out.println("Available Doctors:");
        for (int i = 0; i < availableDoc.size(); i++) {
            Doctor doctor = availableDoc.get(i);
            List<Specialization> specializations = doctor.getDocSpecialization();
            String specializationNames = specializations.isEmpty() ? "NIL"
                    : specializations.stream()
                            .map(Specialization::getSpecializationName)
                            .collect(Collectors.joining(", "));

            System.out.println((i + 1) + ". " + doctor.getName() + " (" + doctor.getHospitalId() + ") - " + specializationNames);
        }

        System.out.println("\nPlease select a doctor to view available appointments: ");
        int choice = Helper.readInt(1, availableDoc.size());

        Doctor selectedDoctor = availableDoc.get(choice - 1);
        List<Specialization> selectedSpecializations = selectedDoctor.getDocSpecialization();
        String selectedSpecializationNames = selectedSpecializations.isEmpty() ? "NIL"
                : selectedSpecializations.stream()
                        .map(Specialization::getSpecializationName)
                        .collect(Collectors.joining(", "));

        System.out.println("You selected Dr. " + selectedDoctor.getName() + " (" + selectedDoctor.getHospitalId() + ") - " + selectedSpecializationNames);
        System.out.println("-----------------------------------------------------------");
        List<Schedule> doctorSchedule = DoctorController.getSchedule(selectedDoctor);

        if (doctorSchedule.isEmpty()) {
            System.out.println("No available appointment slots for this doctor.");
            return false;
        }

        // Fetch available slots for the selected doctor (with pending appointments excluded)
        List<Schedule> availableSlots = AppointmentController.getAvailableSlotsForDoctor(selectedDoctor);

        if (availableSlots.isEmpty()) {
            System.out.println("No available appointment slots for this doctor.");
            return false;
        }

        // Group available slots by date
        Map<String, List<Schedule>> groupedSlots = new HashMap<>();
        for (Schedule slot : availableSlots) {
            String date = slot.getStartTime().toLocalDate().toString();
            groupedSlots.computeIfAbsent(date, k -> new ArrayList<>()).add(slot);
        }

        System.out.println("Available Appointment Slots:");
        int index = 1;
        // Display available slots grouped by date
        for (Map.Entry<String, List<Schedule>> entry : groupedSlots.entrySet()) {
            System.out.println(entry.getKey()); // Date
            System.out.println("-------------");

            for (Schedule slot : entry.getValue()) {
                // Display the time range for the slot
                String timeRange = slot.getStartTime().toLocalTime() + " - " + slot.getEndTime().toLocalTime();
                System.out.println("(" + index + ") From: " + timeRange);
                index++;
            }
            System.out.println();
        }

        // Ask the user to select a slot
        System.out.print("Please select an appointment slot by entering the number: ");
        int option = Helper.readInt(1, availableSlots.size());

        Schedule selectedSchedule = availableSlots.get(option - 1);
        return scheduleAppointmentInput(selectedDoctor, selectedSchedule);
    }

    /**
     * Prompts the patient to view all available appointments.
     *
     * @return {@code true} if an appointment was scheduled successfully;
     * {@code false} otherwise.
     */
    public boolean promptAllAvailableAppt() {
        List<Doctor> availableDoctors = DoctorController.getAllDoctors();

        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors available.");
            return false;
        }

        boolean hasAvailableSlots = false;
        System.out.println("All Available Appointment Slots:");

        // Map to hold doctor's name as the key and a map of dates to their available slots
        Map<String, Map<String, List<Schedule>>> doctorSchedules = new HashMap<>();

        // Loop through all available doctors
        for (Doctor doctor : availableDoctors) {
            // Fetch available slots for each doctor (excluding pending appointments)
            List<Schedule> availableSlots = AppointmentController.getAvailableSlotsForDoctor(doctor);

            if (!availableSlots.isEmpty()) {
                hasAvailableSlots = true;

                // Group the available slots by date for the current doctor
                Map<String, List<Schedule>> groupedSchedulesByDate = new HashMap<>();
                for (Schedule schedule : availableSlots) {
                    String date = schedule.getStartTime().toLocalDate().toString();
                    groupedSchedulesByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(schedule);
                }

                // Add the grouped schedules to the main map for the doctor
                doctorSchedules.put(doctor.getName(), groupedSchedulesByDate);
            }
        }

        if (!hasAvailableSlots) {
            System.out.println("No available appointment slots for any doctor.");
            return false;
        }

        int index = 1;

        // Display the available slots grouped by doctor and date
        for (Map.Entry<String, Map<String, List<Schedule>>> doctorEntry : doctorSchedules.entrySet()) {
            String doctorName = doctorEntry.getKey();
            Map<String, List<Schedule>> dateSchedules = doctorEntry.getValue();

            // Display the doctor's name
            System.out.println("\nDr. " + doctorName.toUpperCase());

            // Display the available slots grouped by date
            for (Map.Entry<String, List<Schedule>> dateEntry : dateSchedules.entrySet()) {
                String date = dateEntry.getKey();
                List<Schedule> schedules = dateEntry.getValue();

                // Display the date and its available slots
                System.out.println(date);
                System.out.println("-------------");

                // Display each available slot for the current date
                for (Schedule schedule : schedules) {
                    String timeRange = schedule.getStartTime().toLocalTime() + " - " + schedule.getEndTime().toLocalTime();
                    System.out.println("(" + index + ") From: " + timeRange);
                    index++;
                }
            }
        }

        System.out.println("\nPlease select an appointment slot by entering the number: ");
        int choice = Helper.readInt(1, doctorSchedules.values().stream().flatMap(dateSchedules -> dateSchedules.values().stream()).mapToInt(List::size).sum());

        int selectedIndex = 1;
        Schedule selectedSchedule = null;
        Doctor selectedDoctor = null;
        for (Map.Entry<String, Map<String, List<Schedule>>> doctorEntry : doctorSchedules.entrySet()) {
            for (Map.Entry<String, List<Schedule>> dateEntry : doctorEntry.getValue().entrySet()) {
                for (Schedule schedule : dateEntry.getValue()) {
                    if (selectedIndex == choice) {
                        selectedSchedule = schedule;
                        selectedDoctor = availableDoctors.stream().filter(doc -> doc.getName().equals(doctorEntry.getKey())).findFirst().orElse(null);
                        break;
                    }
                    selectedIndex++;
                }
                if (selectedSchedule != null) {
                    break;
                }
            }
            if (selectedSchedule != null) {
                break;
            }
        }
        // Call the scheduleAppointment method to proceed with scheduling
        return scheduleAppointmentInput(selectedDoctor, selectedSchedule);
    }

    /**
     * Helper method to schedule an appointment based on the selected doctor and
     * schedule.
     *
     * @param selectedDoctor The selected doctor.
     * @param selectedSchedule The selected schedule.
     * @return {@code true} if the appointment was scheduled successfully;
     * {@code false} otherwise.
     */
    private boolean scheduleAppointmentInput(Doctor selectedDoctor, Schedule selectedSchedule) {
        // Prompt user for the desired start and end time
        String scheduleDate = selectedSchedule.getStartTime().toLocalDate().toString();

        // Use Helper to validate both start and end times
        LocalDateTime[] appointmentTimes = Helper.validateAppointmentTime(scheduleDate,
                "Enter the start time for your appointment (Format: HH:mm): ",
                "Enter the end time for your appointment (Format: HH:mm): ");

        LocalDateTime startTime = appointmentTimes[0];
        LocalDateTime endTime = appointmentTimes[1];

        // Proceed with making the appointment
        boolean appointmentMade = AppointmentController.makeAppointment(patient, selectedDoctor, selectedSchedule, startTime, endTime);

        if (appointmentMade) {
            return true;
        } else {
            System.out.println("Failed to make the appointment. Please try again.");
            return false;
        }
    }

}
