package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the medical record of a patient in the hospital management system.
 * 
 * <p>The {@code MedicalRecord} class contains details about a patient's 
 * diagnoses. Each medical record is associated with a unique record ID.</p>
 * 
 * <p>This class implements {@link Serializable} to allow medical records to be
 * serialized for persistence.</p>
 * 
 * @author Darren, Bryan
 * @version 1.0
 * @since 2024-11-17
 */
public class MedicalRecord implements Serializable {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the medical record.
     */
    private String recordID;

    /**
     * The list of diagnoses associated with the medical record.
     */
    private List<Diagnosis> diagnosis;

    /**
     * Constructs a new {@code MedicalRecord} with the specified record ID.
     * <p>Initializes the list of diagnoses as an empty list.</p>
     * 
     * @param recordID The unique identifier for the medical record.
     */
    public MedicalRecord(String recordID) {
        this.recordID = recordID;
        this.diagnosis = new ArrayList<>(); // Initialize an empty list of diagnoses
    }

    /**
     * Returns the unique identifier of the medical record.
     * 
     * @return The record ID.
     */
    public String getRecordID() {
        return recordID;
    }

    /**
     * Sets the unique identifier for the medical record.
     * 
     * @param recordID The new record ID.
     */
    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    /**
     * Returns the list of diagnoses associated with the medical record.
     * 
     * @return A list of {@link Diagnosis} objects.
     */
    public List<Diagnosis> getDiagnoses() {
        return diagnosis;
    }

    /**
     * Adds a new diagnosis to the medical record.
     * 
     * @param diagnosis The {@link Diagnosis} to add.
     *                  If {@code null}, the diagnosis will not be added.
     */
    public void addDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            this.diagnosis.add(diagnosis);
        }
    }
}
