package src.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    <<<<<<<<< Temporary merge branch 1
    public static HashMap<String, Patient> PATIENT = new HashMap<>();
    =========
    public static HashMap<String, InventoryList> INVENTORY = new HashMap<>();
    public static HashMap<String, Patient> PATIENT = new HashMap<>();
    public static HashMap<String, ReplenishmentRequest> REPLENISHMENT_REQUEST = new HashMap<>();

    >>>>>>>>> Temporary merge branch

    2

    public static void persistData(FileType fileType) {
        writeSerializedObject(fileType);
    }

    public static void readData(FileType fileType) {
        readSerializedObject(fileType);
    }

    public static void saveAllFiles() {
        persistData(FileType.STAFF);
         << << << << < Temporary merge branch 1
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
         == == == ==
                = persistData(FileType.INVENTORY);
        persistData(FileType.REPLENISHMENT_REQUEST);
         >>> >>> >>> Temporary merge branch 

    

    

    

    

    2
    }

    public static boolean clearDatabase() {
        // Initialize empty data
        STAFF = new HashMap<>();
         << << << << < Temporary merge branch 1
        PATIENT = new HashMap<>();
         == == == ==
                = INVENTORY = new HashMap<>();
        REPLENISHMENT_REQUEST = new HashMap<>();
         >>> >>> >>> Temporary merge branch 2
        writeSerializedObject(FileType.STAFF);
        writeSerializedObject(FileType.INVENTORY);
        writeSerializedObject(FileType.REPLENISHMENT_REQUEST);
        return true;
    }

    private static boolean writeSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/Repository/" + folder + "/" + fileType.fileName + fileExtension;
        try {
            // Create the directory if it doesn't exist
            new File("./src/Repository/" + folder).mkdirs();

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            switch (fileType) {
                case STAFF:
                    objectOutputStream.writeObject(STAFF);
                    break;
                case INVENTORY:
                    objectOutputStream.writeObject(INVENTORY);
                    break;
                case REPLENISHMENT_REQUEST:
                    objectOutputStream.writeObject(REPLENISHMENT_REQUEST);
                    break;
                default:
                    System.out.println("Unsupported file type: " + fileType);
                    return false;
            }
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (Exception err) {
            System.out.println("Error writing " + fileType.fileName + ": " + err.getMessage());
            err.printStackTrace();
            return false;
        }
    }

    private static boolean readSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/Repository/" + folder + "/" + fileType.fileName + fileExtension;
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println(fileType.fileName + ".dat does not exist. Creating a new file.");
            switch (fileType) {
                case STAFF:
                    STAFF = new HashMap<>();
                    break;
                case INVENTORY:  // Initialize INVENTORY if file is missing
                    INVENTORY = new HashMap<>();
                    break;
                case REPLENISHMENT_REQUEST:
                    REPLENISHMENT_REQUEST = new HashMap<>();
                    break;
            }
            writeSerializedObject(fileType);
            return true;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();

            switch (fileType) {
                case STAFF:
                    STAFF = (HashMap<String, Staff>) object;
                    break;
                case INVENTORY:  // Deserialize inventory
                    INVENTORY = (HashMap<String, InventoryList>) object;
                    break;
                case REPLENISHMENT_REQUEST:  // Deserialize inventory
                    REPLENISHMENT_REQUEST = (HashMap<String, ReplenishmentRequest>) object;
                    break;
                case APPOINTMENT_OUTCOME:
                    APPOINTMENT_OUTCOME = (HashMap<String, AppointmentOutcome>) object;
                    break;
                case APPOINTMENT_LIST:
                    APPOINTMENT_LIST = (HashMap<String, Appointment>) object;
                    break;
                default:
                    System.out.println("Unsupported file type: " + fileType);
                    return false;
            }

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean initializeDummyStaff() {
        if (!Repository.STAFF.isEmpty()) {
            return false;
        }

        // Staff staff1 = new Staff("John Smith", "password", StaffType.DOCTOR,
        // Gender.MALE, 45, "D001");
        // Staff staff2 = new Staff("Emily Clarke", "password", StaffType.DOCTOR,
        // Gender.FEMALE, 38, "D002");
        Staff staff1 = new Doctor("John Doe", "password", Gender.MALE, 30, "D001", null, null, null);
        Staff staff2 = new Doctor("Emily Clarke", "password", Gender.FEMALE, 28, "D002", null, null, null);
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


<<<<<<<<< Temporary merge branch 1
    }

    // New method to initialize dummy inventory data
    public static boolean initializeDummyInventory() {
        if (!Repository.INVENTORY.isEmpty()) {
            return false;
        }

        // Dummy data based on provided table
        Medicine paracetamol = new Medicine("001", "Paracetamol", 5, 10, "To treat fever");
        InventoryList inventoryParacetamol = new InventoryList(paracetamol, 100, 20);

        Medicine ibuprofen = new Medicine("002", "Ibuprofen", 4, 15, "To treat fever");
        InventoryList inventoryIbuprofen = new InventoryList(ibuprofen, 5, 10);

        Medicine amoxicillin = new Medicine("003", "Amoxicillin", 3, 7, "To treat fever");
        InventoryList inventoryAmoxicillin = new InventoryList(amoxicillin, 75, 15);

        // Add to INVENTORY with String keys
        Repository.INVENTORY.put(String.valueOf(paracetamol.getMedicineId()), inventoryParacetamol);
        Repository.INVENTORY.put(String.valueOf(ibuprofen.getMedicineId()), inventoryIbuprofen);
        Repository.INVENTORY.put(String.valueOf(amoxicillin.getMedicineId()), inventoryAmoxicillin);

        System.out.println("Dummy inventory data initialized successfully.");

        // Return true indicating dummy data is initialized
        return true;
    }

    public static boolean initializeDummyReplenishmentRequest() {
        if (!Repository.REPLENISHMENT_REQUEST.isEmpty()) {
            return false;
        }

        ReplenishmentRequest replenishmentRequest1 = new ReplenishmentRequest("R001", "001", 50, InventoryRequestStatus.PENDING);
        ReplenishmentRequest replenishmentRequest2 = new ReplenishmentRequest("R002", "002", 50, InventoryRequestStatus.PENDING);

        // Add to the repository
        Repository.REPLENISHMENT_REQUEST.put(replenishmentRequest1.getRequestId(), replenishmentRequest1);
        Repository.REPLENISHMENT_REQUEST.put(replenishmentRequest2.getRequestId(), replenishmentRequest2);

        // Return true indicating dummy data is initialized
        return true;
    }
    
    public static boolean initializeDummyAppointmentOutcome() {
        if (!Repository.APPOINTMENT_OUTCOME.isEmpty()) {
            return false;
        }

        // Create sample Medicine objects
        Medicine medicine1 = new Medicine("001", "Paracetamol", 5, "To treat fever");
        Medicine medicine2 = new Medicine("002", "Ibuprofen", 4, "To treat inflammation");

        // Create independent copies of the medicines for each AppointmentOutcome
        List<Medicine> prescribedMedicinesList1 = Arrays.asList(new Medicine(medicine1), new Medicine(medicine2));
        List<Medicine> prescribedMedicinesList2 = Arrays.asList(new Medicine(medicine1));

        // Create dummy Diagnosis objects
        Diagnosis diagnosis1 = new Diagnosis(101, "Hypertension", "High blood pressure requiring regular monitoring");
        Diagnosis diagnosis2 = new Diagnosis(102, "Diabetes Type 2", "Chronic condition affecting blood sugar regulation");

        // Add Diagnosis objects to a list
        List<Diagnosis> diagnosisList1 = Arrays.asList(diagnosis1, diagnosis2);
        List<Diagnosis> diagnosisList2 = Arrays.asList(diagnosis2);

        // Create dummy AppointmentOutcome instances
        AppointmentOutcome appointmentOutcome1 = new AppointmentOutcome(
            "101", 
            prescribedMedicinesList1, 
            diagnosisList1, 
            "Patient needs rest and fluids.", 
            LocalDateTime.now()
        );

        AppointmentOutcome appointmentOutcome2 = new AppointmentOutcome(
            "102", 
            prescribedMedicinesList2, 
            diagnosisList2, 
            "Prescribed light medication and rest.", 
            LocalDateTime.now().minusDays(1)
        );

        // Add to the repository
        Repository.APPOINTMENT_OUTCOME.put(appointmentOutcome1.getOutcomeId(), appointmentOutcome1);
        Repository.APPOINTMENT_OUTCOME.put(appointmentOutcome2.getOutcomeId(), appointmentOutcome2);

        // Return true indicating dummy data is initialized
        return true;
    }

}
