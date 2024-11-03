package src.Repository;

public enum FileType {
    STAFF("Staff"),
    INVENTORY("Inventory"),
    PATIENT("Patient"),
    REPLENISHMENT_REQUEST("Replenishment_Request"),
	APPOINTMENT_OUTCOME("Appointment_Outcome");

    public final String fileName;

    private FileType(String name) {
        this.fileName = name;
    }
}