package src.Model;

import java.time.LocalDateTime;
import src.Enum.AppointmentStatus;

public class Appointment {
    private static int idCounter = 1;

    // Attributes
    private int appointmentId;
    private Patient patient;
    private Doctor attendingDoctor;
    private LocalDateTime appointmentDate;
    private AppointmentOutcome outcome;
    private AppointmentStatus status;

    // Constructor
    public Appointment(Patient patient, Doctor attendingDoctor, LocalDateTime appointmentDate) {
        this.appointmentId = idCounter++;
        this.patient = patient;
        this.attendingDoctor = attendingDoctor;
        this.appointmentDate = appointmentDate;
        // this.status = AppointmentStatus.PENDING;
    }

    // Getters and Setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
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
