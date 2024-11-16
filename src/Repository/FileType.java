package src.Repository;

public enum FileType {
    STAFF("Staff"),
    INVENTORY("Inventory"),
    REPLENISHMENT_REQUEST("Replenishment_Request"),
    PATIENT("Patient"),
    MEDICINE("Medicine"),
    APPOINTMENT_OUTCOME("Appointment_Outcome"),
    APPOINTMENT_LIST("Appointment_List");

    public final String fileName;

    private FileType(String name) {
        this.fileName = name;
    }
}