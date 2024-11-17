package src.Repository;

/**
 * The FileType enum represents different types of files used in the system.
 * <p>
 * Each enum constant is associated with a specific file name that corresponds
 * to a particular category of data managed by the system.
 * </p>
 *
 * <p>
 * <b>Enum Constants:</b></p>
 * <ul>
 * <li>{@link #STAFF} - Represents the staff data file.</li>
 * <li>{@link #INVENTORY} - Represents the medical inventory data file.</li>
 * <li>{@link #REPLENISHMENT_REQUEST} - Represents the replenishment request
 * data file.</li>
 * <li>{@link #PATIENT} - Represents the patient data file.</li>
 * <li>{@link #MEDICINE} - Represents the medicine data file.</li>
 * <li>{@link #APPOINTMENT_OUTCOME} - Represents the appointment outcome data
 * file.</li>
 * <li>{@link #APPOINTMENT_LIST} - Represents the appointment list data
 * file.</li>
 * <li>{@link #MEDICAL_RECORD} - Represents the medical record data file.</li>
 * <li>{@link #DIAGNOSIS} - Represents the diagnosis data file.</li>
 * <li>{@link #TREATMENT} - Represents the treatment data file.</li>
 * </ul>
 *
 * <p>
 * Each constant has an associated file name that is used to identify the file
 * in the system.</p>
 *
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public enum FileType {
    /**
     * Represents the staff data file.
     */
    STAFF("Staff"),
    /**
     * Represents the medical inventory data file.
     */
    INVENTORY("Inventory"),
    /**
     * Represents the replenishment request data file.
     */
    REPLENISHMENT_REQUEST("Replenishment_Request"),
    /**
     * Represents the patient data file.
     */
    PATIENT("Patient"),
    /**
     * Represents the medicine data file.
     */
    MEDICINE("Medicine"),
    /**
     * Represents the appointment outcome data file.
     */
    APPOINTMENT_OUTCOME("Appointment_Outcome"),
    /**
     * Represents the appointment list data file.
     */
    APPOINTMENT_LIST("Appointment_List"),
    /**
     * Represents the medical record data file.
     */
    MEDICAL_RECORD("Medical_Record"),
    /**
     * Represents the diagnosis data file.
     */
    DIAGNOSIS("Diagnosis"),
    /**
     * Represents the treatment data file.
     */
    TREATMENT("Treatment");

    /**
     * The file name associated with the file type.
     */
    public final String fileName;

    /**
     * Constructs a FileType enum constant with the specified file name.
     *
     * @param name The file name associated with the file type.
     */
    private FileType(String name) {
        this.fileName = name;
    }
}
