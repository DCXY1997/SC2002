package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    private int treatmentId; // Reference to the associated diagnosis
    private List<Medicine> medications; // List of medications for the treatment
    private List<String> frequency;

    // Constructor
    public Treatment(int treatmentId, List<Medicine> medications, List<String> frequency) {
        this.treatmentId = treatmentId;
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

    public List<String> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<String> frequency) {
        this.frequency = frequency;
    }
}
