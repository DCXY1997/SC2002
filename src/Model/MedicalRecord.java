package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;
	private String recordID;
	private List<Diagnosis> diagnosis;

	// Constructor
	public MedicalRecord(String recordID) { // Removed Patient parameter as it wasn't used
		this.recordID = recordID;
		this.diagnosis = new ArrayList<>(); // Corrected syntax and used generics
	}

	// Getter for recordID
	public String getRecordID() {
		return recordID;
	}

	// Setter for recordID
	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	// Getter for appointment outcomes
	public List<Diagnosis> getDiagnoses() {
		return diagnosis;
	}

	// Method to add an appointment outcome
	public void addDiagnosis(Diagnosis diagnosis) {
		if (diagnosis != null) {
			this.diagnosis.add(diagnosis);
		}
	}
}
