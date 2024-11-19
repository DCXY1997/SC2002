package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import src.Enum.AppointmentStatus;

/**
 * The Appointment class represents a scheduled meeting between a
 * {@link Patient} and a {@link Doctor}. It includes details such as appointment
 * ID, participants, date, time, status, and outcome.
 * <p>
 * This class is serializable, allowing it to be saved and retrieved from
 * persistent storage.
 * </p>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Tracks the start and end times of an appointment.</li>
 * <li>Maintains the status of the appointment using
 * {@link AppointmentStatus}.</li>
 * <li>Allows the recording of an appointment's outcome.</li>
 * </ul>
 *
 * @see Patient
 * @see Doctor
 * @see AppointmentStatus
 * @see AppointmentOutcome
 * @author Jasmine Tye
 * @version 1.0
 * @since 2024-11-17
 */
public class Appointment implements Serializable {

    /**
     * For Java Serializable. Ensures compatibility during serialization and
     * deserialization processes.
     */
    private static final long serialVersionUID = 1L;

    // Attributes
    private String appointmentId;
    private Patient patient;
    private Doctor attendingDoctor;
    private LocalDateTime appointmentStartDate;
    private LocalDateTime appointmentEndDate;
    private AppointmentStatus status;
    private AppointmentOutcome outcome;

    /**
     * Constructs a new Appointment object with the specified patient, doctor,
     * start time, and end time.
     *
     * @param patient The {@link Patient} associated with the appointment.
     * @param attendingDoctor The {@link Doctor} attending the appointment.
     * @param appointmentStartDate The start time of the appointment.
     * @param appointmentEndDate The end time of the appointment.
     */
    public Appointment(Patient patient, Doctor attendingDoctor, LocalDateTime appointmentStartDate, LocalDateTime appointmentEndDate) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.attendingDoctor = attendingDoctor;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        // this.status = AppointmentStatus.PENDING;
    }

    /**
     * Retrieves the appointment ID.
     *
     * @return The unique identifier of the appointment.
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentId The unique identifier of the appointment.
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Retrieves the patient associated with the appointment.
     *
     * @return The {@link Patient} associated with the appointment.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the patient for the appointment.
     *
     * @param patient The {@link Patient} to associate with the appointment.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Retrieves the doctor attending the appointment.
     *
     * @return The {@link Doctor} attending the appointment.
     */
    public Doctor getAttendingDoctor() {
        return attendingDoctor;
    }

    /**
     * Sets the doctor for the appointment.
     *
     * @param attendingDoctor The {@link Doctor} attending the appointment.
     */
    public void setAttendingDoctor(Doctor attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }

    /**
     * Retrieves the start time of the appointment.
     *
     * @return The start time of the appointment.
     */
    public LocalDateTime getAppointmentStartDate() {
        return appointmentStartDate;
    }

    /**
     * Sets the start time of the appointment.
     *
     * @param appointmentStartDate The start time of the appointment.
     */
    public void setAppointmentStartDate(LocalDateTime appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    /**
     * Retrieves the end time of the appointment.
     *
     * @return The end time of the appointment.
     */
    public LocalDateTime getAppointmentEndDate() {
        return appointmentEndDate;
    }

    /**
     * Sets the end time of the appointment.
     *
     * @param appointmentEndDate The end time of the appointment.
     */
    public void setAppointmentEndDate(LocalDateTime appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    /**
     * Retrieves the outcome of the appointment.
     *
     * @return The {@link AppointmentOutcome} of the appointment.
     */
    public AppointmentOutcome getOutcome() {
        return outcome;
    }

    /**
     * Sets the outcome of the appointment.
     *
     * @param outcome The {@link AppointmentOutcome} of the appointment.
     */
    public void setOutcome(AppointmentOutcome outcome) {
        this.status = AppointmentStatus.COMPLETED;
        this.outcome = outcome;
    }

    /**
     * Retrieves the status of the appointment.
     *
     * @return The {@link AppointmentStatus} of the appointment.
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status The {@link AppointmentStatus} of the appointment.
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
