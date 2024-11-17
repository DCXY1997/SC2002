package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a medical treatment associated with a diagnosis.
 * 
 * <p>The {@code Treatment} class stores information about medications prescribed for
 * a specific treatment and the associated quantities.</p>
 * 
 * <p>This class implements {@link Serializable} to allow instances of {@code Treatment}
 * to be serialized and stored persistently.</p>
 * 
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Stores the treatment ID to identify the treatment uniquely.</li>
 *   <li>Maintains a list of medications associated with the treatment.</li>
 *   <li>Tracks the quantities of the prescribed medications.</li>
 * </ul>
 * 
 * @author Bryan, Darren
 * @version 1.0
 * @since 2024-11-17
 */
public class Treatment implements Serializable {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    // Attributes

    /**
     * The unique identifier for the treatment.
     */
    private int treatmentId;

    /**
     * The list of medications prescribed as part of the treatment.
     */
    private List<Medicine> medications;

    /**
     * The list of quantities corresponding to the prescribed medications.
     */
    private List<Integer> medicineAmount;

    // Constructors

    /**
     * Constructs a new {@code Treatment} with the specified ID, medications, and their quantities.
     * 
     * @param treatmentId   The unique identifier for the treatment.
     * @param medications   The list of prescribed medications.
     * @param medicineAmount The list of quantities corresponding to the medications.
     */
    public Treatment(int treatmentId, List<Medicine> medications, List<Integer> medicineAmount) {
        this.treatmentId = treatmentId;
        this.medications = new ArrayList<>(medications); // Initialize with a copy of the medications
        this.medicineAmount = medicineAmount;
    }

    // Getters and Setters

    /**
     * Returns the treatment ID.
     * 
     * @return The unique identifier for the treatment.
     */
    public int getTreatmentId() {
        return treatmentId;
    }

    /**
     * Sets the treatment ID.
     * 
     * @param treatmentId The new treatment ID.
     */
    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    /**
     * Returns the list of medications associated with the treatment.
     * 
     * @return The list of medications.
     */
    public List<Medicine> getMedications() {
        return medications;
    }

    /**
     * Adds a medication to the treatment.
     * 
     * @param medicine The medication to add.
     */
    public void addMedication(Medicine medicine) {
        if (medicine != null) {
            this.medications.add(medicine);
        }
    }

    /**
     * Removes a medication from the treatment.
     * 
     * @param medicine The medication to remove.
     */
    public void removeMedication(Medicine medicine) {
        this.medications.remove(medicine);
    }

    /**
     * Returns the list of quantities corresponding to the medications.
     * 
     * @return The list of quantities for each medication.
     */
    public List<Integer> getMedicineAmount() {
        return medicineAmount;
    }

    /**
     * Sets the quantities of the prescribed medications.
     * 
     * @param medicineAmount The new list of quantities.
     */
    public void setMedicineAmount(List<Integer> medicineAmount) {
        this.medicineAmount = medicineAmount;
    }
}
