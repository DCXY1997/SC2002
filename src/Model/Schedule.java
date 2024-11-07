package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Schedule implements Serializable {

    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;

    // Attributes
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Constructor
    public Schedule(LocalDateTime startTime, LocalDateTime endTime) {
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

    // public Doctor getDoctor() {
    //     return doctor;
    // }
    // public void setDoctor(Doctor doctor) {
    //     this.doctor = doctor;
    // }
    // Override toString to display meaningful information about the schedule
    // @Override
    // public String toString() {
    //     return "Start Time: " + startTime
    //             + "End Time: " + endTime;
    // }
}
