package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import src.Enum.AppointmentStatus;

public class Appointment implements Serializable {

    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;
    private static int idCounter = 1;

    // Attributes
    private String appointmentId;
    private Patient patient;
    private Doctor attendingDoctor;
    private LocalDateTime appointmentStartDate;
    private LocalDateTime appointmentEndDate;
    private AppointmentOutcome outcome;
    private AppointmentStatus status;

    // Constructor
    public Appointment(Patient patient, Doctor attendingDoctor, LocalDateTime appointmentStartDate, LocalDateTime appointmentEndDate) {
        this.appointmentId = "A" + idCounter++;
        this.patient = patient;
        this.attendingDoctor = attendingDoctor;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        // this.status = AppointmentStatus.PENDING;
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getAttendingDoctor() {
        return attendingDoctor;
    }

    public void setAttendingDoctor(Doctor attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }

    public LocalDateTime getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public void setAppointmentStartDate(LocalDateTime appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    public LocalDateTime getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public void setAppointmentEndDate(LocalDateTime appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    public AppointmentOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(AppointmentOutcome outcome) {
        this.outcome = outcome;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
