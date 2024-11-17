package src.Controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.Enum.AppointmentStatus;
import src.Model.Appointment;
import src.Model.AppointmentOutcome;
import src.Model.Diagnosis;
import src.Model.MedicalRecord;
import src.Model.Medicine;
import src.Model.Patient;
import src.Model.Treatment;
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

    public static void displayPatientRecord(String loginId) {
    	Patient patient = Repository.PATIENT.get(loginId);
    	
        System.out.println("Patient ID: " + patient.getPatientId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Information: " + patient.getContactInformation());
        System.out.println("Blood Type: " + patient.getBloodType());
        
        // Display Diagnoses and Treatments
        MedicalRecord record = patient.getMedicalRecord(); // Assuming Patient has a method to retrieve MedicalRecord
        if (record != null) {
            System.out.println("Diagnoses and Treatments:");
            List<Diagnosis> diagnoses = record.getDiagnoses(); // Assuming outcome links to a list of Diagnosis
            
            for (Diagnosis diagnosis : diagnoses) {
                System.out.println("  Diagnosis ID: " + diagnosis.getDiagnosisId());
                System.out.println("  Name: " + diagnosis.getDiagnosisName());
                System.out.println("  Description: " + diagnosis.getDescription());
                
                for (Treatment treatment : diagnosis.getTreatments()) {
                    System.out.println("  Treatment ID: " + treatment.getTreatmentId());
                        
                    for (int i = 0; i < treatment.getMedications().size(); i++) {
                        System.out.println("   Medicine: " + treatment.getMedications().get(i).getMedicineName());
                        System.out.println("   Description: " + treatment.getMedications().get(i).getMedicineDescription());
                        System.out.println("   Medicine Amount: " + treatment.getMedicineAmount().get(i));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");
                }
                System.out.println();
                System.out.println("===================================");
                
            }
            System.out.println();
        } else {
            System.out.println("No medical record found for this patient.");
        }
    }
    
    public static void viewPastAppointmentOutcome(String patientId) {
        Patient patient = Repository.PATIENT.get(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        List<Appointment> appointments = AppointmentController.viewPatientAppointments(patient);
        List<AppointmentOutcome> pastAppointments = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                pastAppointments.add(appointment.getOutcome());
            }
        }

        if (pastAppointments.isEmpty()) {
            System.out.println("No past appointment outcomes found for this patient.");
            return;
        }
        else {
            System.out.println("Past Appointment Outcomes for Patient ID: " + patientId);
            for (AppointmentOutcome outcome : pastAppointments) {
                System.out.println("\nOutcome ID: " + outcome.getOutcomeId());
                System.out.println("Date Diagnosed: " + outcome.getDateDiagnosed().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                System.out.println("Doctor's Notes: " + outcome.getDoctorNotes()); // Include doctor notes here

                List<Diagnosis> diagnoses = outcome.getPatientDiagnosis();
                if (diagnoses != null && !diagnoses.isEmpty()) {
                    System.out.println("Diagnoses:");
                    for (Diagnosis diagnosis : diagnoses) {
                        System.out.println("  - Diagnosis ID: " + diagnosis.getDiagnosisId());
                        System.out.println("    Name: " + diagnosis.getDiagnosisName());
                        System.out.println("    Description: " + diagnosis.getDescription());

                        List<Treatment> treatments = diagnosis.getTreatments();
                        if (treatments != null && !treatments.isEmpty()) {
                            System.out.println("    Treatments:");
                            for (Treatment treatment : treatments) {
                                System.out.println("      Treatment ID: " + treatment.getTreatmentId());
                                System.out.println("      Medicine Amount: " + treatment.getMedicineAmount());

                                List<Medicine> medicines = treatment.getMedications();
                                if (medicines != null && !medicines.isEmpty()) {
                                    System.out.println("      Medicines:");
                                    for (Medicine medicine : medicines) {
                                        System.out.println("        Medicine: " + medicine.getMedicineName());
                                        System.out.println("        Description: " + medicine.getMedicineDescription());
                                    }
                                } else {
                                    System.out.println("        No medicines prescribed.");
                                }
                            }
                        } else {
                            System.out.println("    No treatments available.");
                        }
                    }
                } else {
                    System.out.println("No diagnoses recorded for this outcome.");
                }
            }
        }
    }

    public static int getLastDiagId(Patient patient){
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        if(medicalRecord != null){
            if (!medicalRecord.getDiagnoses().isEmpty()){
                int lastPatientDiag = patient.getMedicalRecord().getDiagnoses().size()-1;
                return patient.getMedicalRecord().getDiagnoses().get(lastPatientDiag).getDiagnosisId();
            }
        }
        return 0;
    }



    // }
}
