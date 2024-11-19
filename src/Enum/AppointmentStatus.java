package src.Enum;

/**
 * The {@code AppointmentStatus} enum represents the various states an
 * appointment can be in within the hospital management system.
 *
 * <p>
 * <b>Enum Constants:</b></p>
 * <ul>
 * <li>{@link #PENDING} - Indicates the appointment is awaiting
 * confirmation.</li>
 * <li>{@link #CONFIRMED} - Indicates the appointment has been confirmed.</li>
 * <li>{@link #COMPLETED} - Indicates the appointment has been completed.</li>
 * <li>{@link #CANCELLED} - Indicates the appointment has been cancelled.</li>
 * </ul>
 *
 * <p>
 * This enum is used throughout the system to manage and track the status of
 * appointments.</p>
 *
 * @author Jasmine Tye Jia Wen
 * @version 1.0
 * @since 2024-11-17
 */
public enum AppointmentStatus {
    /**
     * Indicates the appointment is awaiting confirmation.
     */
    PENDING,
    /**
     * Indicates the appointment has been confirmed.
     */
    CONFIRMED,
    /**
     * Indicates the appointment has been completed.
     */
    COMPLETED,
    /**
     * Indicates the appointment has been cancelled.
     */
    CANCELLED;
}
