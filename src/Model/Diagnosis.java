package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Diagnosis} class represents a medical diagnosis for a patient. 
 * Each diagnosis is uniquely identified and can have multiple treatments associated with it.
 *
 * <p>This class implements the {@link Serializable} interface, allowing it to be serialized for
 * data persistence. It maintains details about the diagnosis, such as its ID, name, description,
 * and a list of treatments.</p>
 *
 * @author Bryan, Darren Chia
 * @version 1.0
 * @since 2024-11-17
 */
public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for this diagnosis.
     */
    private int diagnosisId;

    /**
     * The name of the diagnosis.
     */
    private String diagnosisName;

    /**
     * A description of the diagnosis.
     */
    private String description;

    /**
     * A list of treatments associated with this diagnosis.
     */
    private List<Treatment> treatment;

    /**
     * Constructs a new {@code Diagnosis} with the specified ID, name, and description.
     *
     * @param diagnosisId   The unique identifier for the diagnosis.
     * @param diagnosisName The name of the diagnosis.
     * @param description   A description of the diagnosis.
     */
    public Diagnosis(int diagnosisId, String diagnosisName, String description) {
        this.diagnosisId = diagnosisId;
        this.diagnosisName = diagnosisName;
        this.description = description;
        this.treatment = new ArrayList<>(); // Initialize the treatment list
    }

    /**
     * Returns the unique identifier for this diagnosis.
     *
     * @return The diagnosis ID.
     */
    public int getDiagnosisId() {
        return diagnosisId;
    }

    /**
     * Returns the name of the diagnosis.
     *
     * @return The diagnosis name.
     */
    public String getDiagnosisName() {
        return diagnosisName;
    }

    /**
     * Sets the name of the diagnosis.
     *
     * @param diagnosisName The new name of the diagnosis.
     */
    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    /**
     * Returns the description of the diagnosis.
     *
     * @return The diagnosis description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the diagnosis.
     *
     * @param description The new description of the diagnosis.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the list of treatments associated with this diagnosis.
     *
     * @return A list of {@link Treatment} objects.
     */
    public List<Treatment> getTreatments() {
        return treatment;
    }

    /**
     * Adds a treatment to the list of treatments for this diagnosis.
     *
     * @param treatment The {@link Treatment} to add.
     *                  If {@code null}, the treatment will not be added.
     */
    public void addTreatment(Treatment treatment) {
        if (treatment != null) {
            this.treatment.add(treatment);
        }
    }

    /**
     * Removes a treatment from the list of treatments for this diagnosis.
     *
     * @param treatment The {@link Treatment} to remove.
     */
    public void removeTreatment(Treatment treatment) {
        this.treatment.remove(treatment);
    }
}
