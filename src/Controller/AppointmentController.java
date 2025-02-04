package src.Controller;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import src.Enum.AppointmentStatus;
import src.Helper.Helper;
import src.Model.Admin;
import src.Model.Appointment;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.AdminView;
import src.View.DisplayAppointmentDetailView;

/**
 * The AppointmentController class is a controller class that acts as a
 * "middleman" between the view classes - {@link AdminView} and
 * {@link DisplayAppointmentDetailView}, and the model classes - {@link Admin}
 * and {@link Appointment}.
 * <p>
 * This class provides functionalities to manage appointments, including
 * creating, rescheduling, canceling, and viewing appointments. It also includes
 * helper methods to retrieve appointment details and available doctor
 * schedules.
 * </p>
 *
 * <p>
 * <b>Key Responsibilities:</b></p>
 * <ul>
 * <li>Create appointments for patients and doctors.</li>
 * <li>View and manage appointments for patients and doctors.</li>
 * <li>Reschedule or cancel appointments.</li>
 * <li>Fetch appointment details and available schedules for doctors.</li>
 * </ul>
 *
 * @see AdminView
 * @see DisplayAppointmentDetailView
 * @see Admin
 * @see Appointment
 * @author Jasmine Tye Jia Wen, Bryan
 * @version 1.0
 * @since 2024-11-17
 */
public class AppointmentController {

    private static List<Appointment> appointments = new ArrayList<>();

    /**
     * Retrieves all appointments currently in the system.
     *
     * @author Jasmine Tye
     * @return A list of all {@link Appointment} objects.
     */
    public static List<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     * Generates a unique appointment ID for a new appointment.
     *
     * @author Jasmine Tye
     * @return A unique appointment ID as a string.
     */
    public static String generateAppointmentId() {
        String prefix = "A";
        int uniqueId = Helper.generateUniqueId(Repository.APPOINTMENT_LIST);
        return prefix + String.format("%03d", uniqueId);
    }

    /**
     * Creates a new appointment for a patient and doctor within the given
     * schedule and time slot. Validates the input data, checks for schedule
     * conflicts, and persists the new appointment.
     *
     * @author Jasmine Tye
     * @param patient The {@link Patient} associated with the appointment.
     * @param doctor The {@link Doctor} attending the appointment.
     * @param schedule The {@link Schedule} of the doctor.
     * @param startTime The start time of the appointment.
     * @param endTime The end time of the appointment.
     * @return {@code true} if the appointment was successfully created,
     * {@code false} otherwise.
     */
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

    /**
     * Retrieves a list of appointments for a specific patient.
     *
     * @author Jasmine Tye
     * @param patient The {@link Patient} whose appointments are to be viewed.
     * @return A list of {@link Appointment} objects associated with the
     * patient.
     */
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

    /**
     * Reschedules an existing appointment to a new time slot within the
     * doctor's schedule.
     *
     * @author Jasmine Tye
     * @param appointment The {@link Appointment} to be rescheduled.
     * @param newSchedule The new {@link Schedule} for the appointment.
     * @return {@code true} if the appointment was successfully rescheduled,
     * {@code false} otherwise.
     */
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

    /**
     * Cancels an existing appointment by updating its status to
     * {@link AppointmentStatus#CANCELLED}.
     *
     * @author Jasmine Tye
     * @param appointment The {@link Appointment} to be canceled.
     * @return {@code true} if the appointment was successfully canceled,
     * {@code false} otherwise.
     */
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

    /**
     * Retrieves a list of appointments for a specific doctor.
     *
     * @author Jasmine Tye
     * @param doctor The {@link Doctor} whose appointments are to be viewed.
     * @return A list of {@link Appointment} objects associated with the doctor.
     */
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

    /**
     * Confirms an appointment for a doctor by updating its status to
     * {@link AppointmentStatus#CONFIRMED}. Also updates the doctor's
     * availability to account for the appointment time.
     *
     * @author Jasmine Tye
     * @param doctor The {@link Doctor} confirming the appointment.
     * @param appointment The {@link Appointment} to be confirmed.
     */
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

    /**
     * Declines an appointment by updating its status to
     * {@link AppointmentStatus#CANCELLED}.
     *
     * @author Jasmine Tye
     * @param appointment The {@link Appointment} to be declined.
     */
    public static void declineAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CANCELLED);
        Repository.readData(FileType.APPOINTMENT_LIST);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        Repository.persistData(FileType.APPOINTMENT_LIST);
    }

    /**
     * Retrieves a list of confirmed appointments for a specific doctor.
     * <p>
     * This method loads the appointments from the repository and filters them
     * to include only those that are confirmed and associated with the given
     * doctor.
     * </p>
     *
     * @author Bryan
     * @param doctor The doctor for whom to retrieve confirmed appointments.
     * @return A list of confirmed appointments for the specified doctor.
     */
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

    /**
     * Retrieves the available time slots for a doctor based on their schedule
     * and pending appointments.
     * <p>
     * This method checks the doctor's schedule and filters out any time slots
     * that overlap with pending appointments. It considers only pending
     * appointments and dynamically calculates available slots for the doctor.
     * </p>
     *
     * @author Bryan
     * @param doctor The doctor whose available slots need to be retrieved.
     * @return A list of available time slots as {@link Schedule} objects.
     */
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

    /**
     * Retrieves a list of completed appointments for a specific patient.
     * <p>
     * This method loads the appointments from the repository and filters them
     * to include only those that are completed and associated with the given
     * patient.
     * </p>
     *
     * @author Bryan
     * @param patient The patient for whom to retrieve completed appointments.
     * @return A list of completed appointments for the specified patient.
     */
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

    /**
     * Retrieves a list of completed appointments for a specific patient.
     * <p>
     * This method loads the appointments from the repository and filters them
     * to include only those that are completed and associated with the given
     * patient.
     * </p>
     *
     * @author Jasmine Tye
     * @param patient The patient for whom to retrieve completed appointments.
     * @return A list of completed appointments for the specified patient.
     */
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

    /**
     * Retrieves the available time slots for a specific doctor while excluding
     * a specific appointment time.
     *
     * @author Jasmine Tye
     * @param doctor The {@link Doctor} whose schedule is to be checked.
     * @param appointmentToAccept The {@link Appointment} to be excluded from
     * the schedule check.
     * @return A list of available {@link Schedule} slots.
     */
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

    /**
     * Retrieves the details of an appointment based on the provided appointment
     * ID. This method reads the latest data from the repository and searches
     * for the appointment in the APPOINTMENT_LIST map. If found, it formats and
     * returns the details. If not found, it returns a "not found" message.
     *
     * @author Bryan
     * @param appointmentId The unique identifier of the appointment to
     * retrieve.
     * @return A formatted string containing the details of the appointment, or
     * a "not found" message if the appointment does not exist.
     */
    public static String getAppointmentDetails(String appointmentId) {
        // Load appointments from the Repository (to ensure the latest data is used)
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Search for the appointment in the APPOINTMENT_LIST map
        Appointment appointment = Repository.APPOINTMENT_LIST.get(appointmentId);

        // If found, format and return the details
        if (appointment != null) {
            return formatAppointmentDetails(appointment);
        }

        // If not found, return a "not found" message
        return "Appointment with ID " + appointmentId + " not found.";
    }

    /**
     * Formats the details of an appointment into a readable string. This helper
     * function extracts key information such as the appointment ID, patient
     * name, doctor name, start time, end time, and status.
     *
     * @author Bryan
     * @param appointment The appointment object to format.
     * @return A formatted string containing the details of the appointment.
     */
    // Helper function to format appointment details
    private static String formatAppointmentDetails(Appointment appointment) {
        return String.format(
                "Appointment ID: %s\nPatient Name: %s\nDoctor Name: %s\nStart Time: %s\nEnd Time: %s\nStatus: %s",
                appointment.getAppointmentId(),
                appointment.getPatient().getName(), // Get the patient's name
                appointment.getAttendingDoctor().getName(), // Get the doctor's name
                appointment.getAppointmentStartDate(),
                appointment.getAppointmentEndDate(),
                appointment.getStatus()
        );
    }

    /**
     * Retrieves a list of all appointment IDs from the repository. This method
     * ensures the latest data is loaded from the repository and returns all
     * keys (appointment IDs) from the APPOINTMENT_LIST map.
     *
     * @author Bryan
     * @return A list of appointment IDs currently available in the repository.
     */
    public static List<String> getAllAppointmentIds() {
        // Load appointments from the Repository
        Repository.readData(FileType.APPOINTMENT_LIST);

        // Collect all appointment IDs
        return new ArrayList<>(Repository.APPOINTMENT_LIST.keySet());
    }

}
