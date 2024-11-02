package src.Repository;

public enum FileType {
    STAFF("Staff"),
    INVENTORY("Inventory"),
    REPLENISHMENT_REQUEST("Replenishment_Request"); 

    public final String fileName;

    private FileType(String name) {
        this.fileName = name;
    }
}
