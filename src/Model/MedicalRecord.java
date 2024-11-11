package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;
	private String recordID;
	private List<AppointmentOutcome> apptOutcomes;

	// Constructor
	public MedicalRecord(String recordID) { // Removed Patient parameter as it wasn't used
		this.recordID = recordID;
		this.apptOutcomes = new ArrayList<>(); // Corrected syntax and used generics
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
	public List<AppointmentOutcome> getApptOutcomes() {
		return new ArrayList<>(apptOutcomes);
	}

	// Method to add an appointment outcome
	public void addApptOutcome(AppointmentOutcome apptOutcome) {
		if (apptOutcome != null) {
			this.apptOutcomes.add(apptOutcome);
		}
	}
}
