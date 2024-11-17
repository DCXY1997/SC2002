package src.Enum;

/**
 * The {@code MedicineStatus} enum represents the status of a medicine in the
 * hospital management system.
 *
 * <p>
 * <b>Enum Constants:</b></p>
 * <ul>
 * <li>{@link #PENDING} - Indicates the medicine has been prescribed but not yet
 * dispensed.</li>
 * <li>{@link #DISPENSED} - Indicates the medicine has been successfully
 * dispensed to the patient.</li>
 * </ul>
 *
 * <p>
 * This enum is used to track the dispensing process of medicines associated
 * with appointments.</p>
 *
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public enum MedicineStatus {
    /**
     * Indicates the medicine has been prescribed but not yet dispensed.
     */
    PENDING,
    /**
     * Indicates the medicine has been successfully dispensed to the patient.
     */
    DISPENSED;
}
