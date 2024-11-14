package src.Model;

import java.time.LocalDateTime;

public class Schedule {

    // Attributes
    private Doctor doctor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Constructor
    public Schedule(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    // Setters
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
