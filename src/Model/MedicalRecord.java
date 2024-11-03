package src.Model;

public class MedicalRecord {
	public String recordID;
	//public ArrayList<AppointOutcome> apptOutcome;
	
	MedicalRecord(String recordID, Patient patient){
		this.recordID = recordID;
		this.apptOutcome = new ArrayList();
	}
	
	public String getRecordID() { return recordID; }
	public void setRecordID(String recordID) { this.recordID = recordID; }
	
	public AppointOutcom getApptOutcome() { return apptOutcome; }
	public void addApptOutcome(AppointOutcom apptOutcome) { this.apptOutcome.add(apptOutcome); }
}
