package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a schedule with a specific start time and end time.
 *
 * <p>
 * The {@code Schedule} class is used to define the availability or timing for
 * appointments, meetings, or other scheduled events.</p>
 *
 * <p>
 * This class implements {@link Serializable} to allow instances of
 * {@code Schedule} to be serialized and stored persistently.</p>
 *
 * @author Jasmine Tye
 * @version 1.0
 * @since 2024-11-17
 */
public class Schedule implements Serializable {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    // Attributes
    /**
     * The start time of the schedule.
     */
    private LocalDateTime startTime;

    /**
     * The end time of the schedule.
     */
    private LocalDateTime endTime;

    // Constructors
    /**
     * Constructs a new {@code Schedule} with the specified start time and end
     * time.
     *
     * @param startTime The start time of the schedule.
     * @param endTime The end time of the schedule.
     */
    public Schedule(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    /**
     * Returns the start time of the schedule.
     *
     * @return The start time.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the schedule.
     *
     * @return The end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    // Setters
    /**
     * Sets the start time of the schedule.
     *
     * @param startTime The new start time.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the schedule.
     *
     * @param endTime The new end time.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
