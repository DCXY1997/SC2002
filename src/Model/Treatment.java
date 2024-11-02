package src.Model;

import java.util.ArrayList;
import java.util.List;

public class Treatment {

    // Attributes
    private int treatmentId;
    private Diagnosis diagnosis; // Reference to the associated diagnosis
    private List<Medicine> medications; // List of medications for the treatment
    private String frequency;

    // Constructor
    public Treatment(int treatmentId, Diagnosis diagnosis, List<Medicine> medications, String frequency) {
        this.treatmentId = treatmentId;
        this.diagnosis = diagnosis;
        this.medications = new ArrayList<>(medications); // Initialize with a copy of the medications
        this.frequency = frequency;
    }

    // Getters and Setters
    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Medicine> getMedications() {
        return medications; // Return the list of medications
    }

    public void addMedication(Medicine medicine) {
        if (medicine != null) {
            this.medications.add(medicine);
        }
    }

    public void removeMedication(Medicine medicine) {
        this.medications.remove(medicine);
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
