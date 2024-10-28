package src.Repository;

public enum FileType {
    STAFF("Staff"),
    MEDICINE("Medicine"),
    PATIENT("Patient"), 
    ADMIN("Admin");

    public final String fileName;

    private FileType(String name) {
        this.fileName = name;
    }
}
