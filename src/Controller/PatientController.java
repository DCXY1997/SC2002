package src.Controller;

import src.Model.Patient;
import src.Repository.FileType;
import src.Repository.Repository;

public class PatientController {

    public static Patient getPatientById(String loginId) {
        return Repository.PATIENT.get(loginId);
    }

    public static boolean authenticate(String id, String password) {
        // Retrieve the Patient object from the PATIENT map
        Patient patient = Repository.PATIENT.get(id);

        // Check if the patient exists
        if (patient == null) {
            return false; // Patient not found
        }

        // Verify password
        if (!patient.getPassword().equals(password)) {
            return false; // Incorrect password
        }

        // Authentication successful
        return true;
    }

    public static boolean changePassword(Patient patient, String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            patient.setPassword(confirmPassword);
            Repository.persistData(FileType.PATIENT); // Persist the updated password
            return true;
        } else {
            return false;
        }
    }

    public static void updateContactInformation(Patient patient, String newContactInformation) {
        // Update the patient's contact information
        patient.setContactInformation(newContactInformation);
        // Persist the updated patient details
        Repository.persistData(FileType.PATIENT);
    }

    public static void displayPersonalInformation(String loginId) {
        Patient patient = Repository.PATIENT.get(loginId);

        if (patient != null) {
            System.out.println("Patient ID: " + patient.getPatientId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Age: " + patient.getAge());
            System.out.println("Birth Date: " + patient.getDateOfBirth());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Email: " + patient.getContactInformation());
        } else {
            System.out.println("Patient not found.");
        }
    }
}
