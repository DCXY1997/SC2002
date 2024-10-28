package src.Repository;

import java.util.HashMap;
import java.util.Set;
import src.Enum.*;
import src.Model.*;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Model.Admin;
import src.Model.Staff;
import src.Controller.*;

import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Repository {
    private static final String folder = "Data";
    
    public static HashMap<String, Staff> STAFF = new HashMap<>();
    public static HashMap<String, Admin> ADMIN= new HashMap<>();

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
        persistData(FileType.ADMIN);
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
            case ADMIN:
                ADMIN = new HashMap<>();
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
            case ADMIN:
                ADMIN = (HashMap<String, Admin>) object;
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
            } else if (fileType == FileType.ADMIN) {
                objectOutputStream.writeObject(ADMIN);
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
        ADMIN = new HashMap<>();
        writeSerializedObject(FileType.STAFF);
        writeSerializedObject(FileType.PATIENT);
        writeSerializedObject(FileType.MEDICINE);
        writeSerializedObject(FileType.ADMIN);
        return true;
    }

    public static boolean initializeDummyStaff() {
    	if (!Repository.STAFF.isEmpty()) {
            return false;
        }

        Staff staff1 = new Staff("Alice Brown", "password123", StaffType.DOCTOR, Gender.FEMALE, 45, "D001");
        Staff staff2 = new Staff("Bob Stone", "securePass", StaffType.DOCTOR, Gender.MALE, 40, "A002");
        Staff staff3 = new Staff("Charlie White", "pass456", StaffType.PHARMACIST, Gender.MALE, 35, "N003");

        // Add to the repository
        Repository.STAFF.put(staff1.getHospitalId(), staff1);
        Repository.STAFF.put(staff2.getHospitalId(), staff2);
        Repository.STAFF.put(staff3.getHospitalId(), staff3);
    
        // Return true indicating dummy data is initialized
        return true;

    }

    public static boolean initializeDummyAdmin() {
    	if(ADMIN.size() !=0 ) {
    		return false;
    	}
		Admin admin = new Admin("Boss", "password", Gender.FEMALE, 62, "boss");
		Repository.ADMIN.put(admin.getHospitalId(), admin);
		Repository.persistData(FileType.ADMIN);
		
		return true;
    }
    

  
}

