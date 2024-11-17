package src.Enum;

/**
 * The {@code PaymentStatus} enum represents the status of a payment in the hospital management system.
 * 
 * <p><b>Enum Constants:</b></p>
 * <ul>
 *   <li>{@link #PENDING} - Indicates the payment for the appointment or service is yet to be made.</li>
 *   <li>{@link #COMPLETED} - Indicates the payment for the appointment or service has been successfully completed.</li>
 * </ul>
 * 
 * <p>This enum is used to track the payment process for appointments and related services in the system.</p>
 * 
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public enum PaymentStatus {
    /**
     * Indicates the payment for the appointment or service is yet to be made.
     */
    PENDING,

    /**
     * Indicates the payment for the appointment or service has been successfully completed.
     */
    COMPLETED;
}