package src.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Model.Patient;
import src.Model.Staff;

public class Repository {
    private static final String folder = "Data";

    public static HashMap<String, Staff> STAFF = new HashMap<>();
    public static HashMap<String, Patient> PATIENT = new HashMap<>();

    public static void persistData(FileType fileType) {
        writeSerializedObject(fileType);
    }

    public static void readData(FileType fileType) {
        readSerializedObject(fileType);
    }

    public static void saveAllFiles() {
        persistData(FileType.STAFF);
        persistData(FileType.PATIENT);
        persistData(FileType.MEDICINE);
    }

    private static boolean readSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/Repository/" + folder + "/" + fileType.fileName + fileExtension;

        // Check if Data directory exists, create it if necessary
        File dataDir = new File("./src/Repository/" + folder);
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create the directory if it doesn't exist
        }

        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println(fileType.fileName + ".dat does not exist. Creating a new file.");

            // If the file does not exist, create an empty HashMap and save it
            switch (fileType) {
                case STAFF:
                    STAFF = new HashMap<>();
                    break;
                case PATIENT:
                    PATIENT = new HashMap<>();
                    break;
            }
            writeSerializedObject(fileType); // Save the empty HashMap to a new file
            return true;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();

            if (!(object instanceof HashMap)) {
                System.out.println("Error: " + fileType.fileName + " is not a valid HashMap.");
                objectInputStream.close();
                return false;
            }

            // Deserialize data into the appropriate HashMap
            switch (fileType) {
                case STAFF:
                    STAFF = (HashMap<String, Staff>) object;
                    break;
                case PATIENT:
                    PATIENT = (HashMap<String, Patient>) object;
                    break;
            }

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean writeSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/Repository/" + folder + "/" + fileType.fileName + fileExtension;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            if (fileType == FileType.STAFF) {
                objectOutputStream.writeObject(STAFF);
            } else if (fileType == FileType.PATIENT) {
                objectOutputStream.writeObject(PATIENT);
            }
            objectOutputStream.close();
            fileOutputStream.close();
            return true;

        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
            return false;
        }
    }

    public static boolean clearDatabase() {
        // Initialize empty data
        STAFF = new HashMap<>();
        PATIENT = new HashMap<>();
        writeSerializedObject(FileType.STAFF);
        writeSerializedObject(FileType.PATIENT);
        writeSerializedObject(FileType.MEDICINE);
        return true;
    }

    public static boolean initializeDummyStaff() {
        if (!Repository.STAFF.isEmpty()) {
            return false;
        }

        Staff staff1 = new Staff("John Smith", "password", StaffType.DOCTOR, Gender.MALE, 45, "D001");
        Staff staff2 = new Staff("Emily Clarke", "password", StaffType.DOCTOR, Gender.FEMALE, 38, "D002");
        Staff staff3 = new Staff("Mark Lee", "password", StaffType.PHARMACIST, Gender.MALE, 29, "P001");
        Staff staff4 = new Staff("Sarah Lee", "password", StaffType.ADMIN, Gender.FEMALE, 40, "A001");

        // Add to the repository
        Repository.STAFF.put(staff1.getHospitalId(), staff1);
        Repository.STAFF.put(staff2.getHospitalId(), staff2);
        Repository.STAFF.put(staff3.getHospitalId(), staff3);
        Repository.STAFF.put(staff4.getHospitalId(), staff4);

        // Return true indicating dummy data is initialized
        return true;

    }

    public static boolean initializeDummyPatient() {
        if (!Repository.PATIENT.isEmpty()) {
            return false;
        }

        Patient patient1 = new Patient("P001", "Alice Brown", "password", 44, LocalDate.of(1980, 5, 14), Gender.FEMALE,
                "alice.brown@example.com", "A+");
        // New Patient object for Bob Stone
        Patient patient2 = new Patient("P002", "Bob Stone", "password", 48,
                LocalDate.of(1975, 11, 22), Gender.MALE, "bob.stone@example.com", "B+");

        // New Patient object for Charlie White
        Patient patient3 = new Patient("P003", "Charlie White", "password", 33,
                LocalDate.of(1990, 7, 8), Gender.MALE, "charlie.white@example.com", "O-");

        // Add to the repository
        Repository.PATIENT.put(patient1.getPatientId(), patient1);
        Repository.PATIENT.put(patient2.getPatientId(), patient2);
        Repository.PATIENT.put(patient3.getPatientId(), patient3);

        // Return true indicating dummy data is initialized
        return true;

    }
}
