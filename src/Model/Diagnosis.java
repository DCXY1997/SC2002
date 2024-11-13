package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    private int diagnosisId;
    private String diagnosisName;
    private String description;
    private List<Treatment> treatment; // List of treatments associated with the diagnosis

    // Constructor
    public Diagnosis(int diagnosisId, String diagnosisName, String description) {
        this.diagnosisId = diagnosisId;
        this.diagnosisName = diagnosisName;
        this.description = description;
        this.treatment = new ArrayList<>(); // Initialize the treatment list
    }

    // Getters
    public int getDiagnosisId() {
        return diagnosisId;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public String getDescription() {
        return description;
    }

    public List<Treatment> getTreatments() {
        return treatment; // Return the list of treatments
    }

    // Setters
    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Methods to manage treatments
    public void addTreatment(Treatment treatment) {
        if (treatment != null) {
            this.treatment.add(treatment);
        }
    }

    // Additional method to remove a treatment if needed
    public void removeTreatment(Treatment treatment) {
        this.treatment.remove(treatment);
    }
}