package src.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.Controller.AppointmentController;
import src.Controller.AppointmentOutcomeController;
import src.Controller.DoctorController;
import src.Controller.PatientController;
import src.Enum.ServiceType;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.AppointmentOutcome;
import src.Model.Diagnosis;
import src.Model.Doctor;
import src.Model.MedicalRecord;
import src.Model.Medicine;
import src.Model.Patient;
import src.Model.Schedule;
import src.Model.Specialization;
import src.Model.Treatment;
import src.Repository.FileType;
import src.Repository.Repository;

public class DoctorView extends MainView {

    private Doctor doctor;
    private DisplayDoctorAppointment displayDoctorAppointmentView;

    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
        if (doctor != null) {
            this.displayDoctorAppointmentView = new DisplayDoctorAppointment(doctor);
        } else {
            System.out.println("Error: Doctor is null in DoctorView.");
        }
    }

    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Doctor View");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Patient Medical Records");
        System.out.println("(2) Update Patient Medical Records");
        System.out.println("(3) View Personal Schedule");
        System.out.println("(4) Set Availability for Appointments");
        System.out.println("(5) Handle Appointments");
        System.out.println("(6) Record Appointment Outcome");
        System.out.println("(7) View Personal Information");
        System.out.println("(8) Add Specialization");
        System.out.println("(9) Logout");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 10);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Patient Medical Record");
                    promptGetPatientRecords(doctor);
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Update Patient Medical Record");
                    promptUpdatePatientRecords(doctor);
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Personal Schedule");
                    promptDisplaySchedule(doctor); // Pass hospitalId
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Set Availability");
                    promptAddAvailability(doctor);
                    break;
                case 5:
                    Helper.clearScreen();
                    displayDoctorAppointmentView.viewApp();
                    break;
                case 6:
                	Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Record Appointment Outcome");
                	recordAppointmentOutcome(doctor);
                	break;
                case 7:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Personal Information");
                    promptPersonalInformation();
                    break;
                case 8:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Add Specialization");
                    promptSpecialization(doctor);
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 9) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 9);
    }

    private String promptGetPatientRecords(Doctor doctor) {
        List<Patient> patientList = DoctorController.getAllPatients(doctor); // Update to get patients by hospitalId
        if (patientList.isEmpty()) {
            System.out.println("This doctor has no patients.");
            return "";
        }
        else{
            for (int i = 0; i<patientList.size(); i++) {
                System.out.println("Patients under you.");
                System.out.println((i+1) + ".");
                System.out.println("Patient ID: " + patientList.get(i).getPatientId());
                System.out.println("Patient Name: " + patientList.get(i).getName());
                System.out.println("Enter the Patient number you want to view: ");
                
            }
            int patientIndex = Helper.readInt(1, patientList.size());
            Patient patient = patientList.get(patientIndex-1);
            PatientController.displayPatientRecord(patient.getPatientId());
            return patient.getPatientId();
        }
        
    }

    private void promptUpdatePatientRecords(Doctor doctor){
        String pid = promptGetPatientRecords(doctor);
        if(pid.length() == 0){
            return;
        }
        Patient patient = Repository.PATIENT.get(pid);
        System.out.println("Enter new Diagnosis and Treatment plan:");
        System.out.println("Enter Diagnosis:");
        String diagnosisName = Helper.readString();
        System.out.println("Enter Description:");
        String description = Helper.readString();

        int diagId = PatientController.getLastDiagId(patient);
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        if (medicalRecord == null) {
            medicalRecord = new MedicalRecord("MR" + patient.getPatientId());
            patient.setMedicalRecord(medicalRecord);
        }
        Diagnosis diagnosis = new Diagnosis(++diagId, diagnosisName, description);

        // Prompt for medications and create a unique treatment
        

        System.out.println("Enter number of Treatments: ");
        int numTreatments = Helper.readInt();
        for(int a = 0; a < numTreatments; a++){
            List<Medicine> treatmentMedicines = new ArrayList<>();
            List<Integer> medicineAmount = new ArrayList<>();
            System.out.println("Enter number of medicines prescribed:");
            int numMedicines = Helper.readInt();

            for (int i = 0; i < numMedicines; i++) {
                System.out.println("Enter Medicine ID or Name:");
                String medKey = Helper.readString();

                Medicine medicine = Repository.INVENTORY.get(medKey).getMedicine();
                if (medicine != null) {
                    treatmentMedicines.add(new Medicine(medicine));
                    System.out.println("Enter amount of Medicine:");
                    int amount = Helper.readInt();
                    medicineAmount.add(amount);
                    
                } else {
                    System.out.println("Warning: Medicine with ID '" + medKey + "' not found.");
                }
            }

            // Create a unique Treatment for each Diagnosis
            Treatment treatment = new Treatment(a, treatmentMedicines, medicineAmount);
            // Add the treatment to the diagnosis
            diagnosis.addTreatment(treatment);
        }
        medicalRecord.addDiagnosis(diagnosis);
        System.out.println("Diagnosis added");
        Repository.PATIENT.put(patient.getPatientId(), patient);
        Repository.persistData(FileType.PATIENT);
    }

    private void promptDisplaySchedule(Doctor doctor) {
        // Retrieve the schedule using the DoctorController method
        List<Schedule> docSchedule = DoctorController.getSchedule(doctor);

        if (docSchedule.isEmpty()) {
            System.out.println("No schedules found for Dr " + doctor.getName());
        } else {
            Map<String, List<Schedule>> groupedSchedules = new HashMap<>();
            for (Schedule schedule : docSchedule) {
                String date = schedule.getStartTime().toLocalDate().toString();
                groupedSchedules.computeIfAbsent(date, k -> new ArrayList<>()).add(schedule);
            }

            System.out.println("Your Schedule: ");

            int index = 1;
            for (Map.Entry<String, List<Schedule>> entry : groupedSchedules.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println("-------------");

                for (Schedule schedule : entry.getValue()) {
                    String startTime = schedule.getStartTime().toLocalTime().toString();
                    String endTime = schedule.getEndTime().toLocalTime().toString();
                    System.out.println("(" + index + ") " + startTime + " - " + endTime);
                    index++;
                }
                System.out.println();
            }
        }
    }

    private void promptAddAvailability(Doctor doctor) {
        LocalDateTime[] availability = Helper.promptAndValidateTimeRange(
                "Enter availability START date and time:",
                "Enter availability END date and time:",
                Helper.TimeValidationRule.FUTURE_DATE,
                Helper.TimeValidationRule.BUSINESS_HOURS,
                Helper.TimeValidationRule.MAX_DURATION_24H
        );

        if (availability != null) {
            DoctorController.addAvailability(doctor, availability[0], availability[1]);
        }
    }

    private void promptPersonalInformation() {
        // Retrieve and display personal information using the DoctorController
        DoctorController.displayPersonalInformation(doctor.getHospitalId());
    }

    public void promptSpecialization(Doctor doctor) {
        String specialization = null;

        // List of predefined specializations
        System.out.println("Please select a specialization from the list below:");
        System.out.println("1. Cardiology");
        System.out.println("2. Dermatology");
        System.out.println("3. Neurology");
        System.out.println("4. Pediatrics");
        System.out.println("5. Orthopedics");
        System.out.println("6. General Surgery");

        System.out.print("Enter the number of your choice: ");
        int choice = Helper.readInt(1, 7);

        if (choice >= 1 && choice <= 6) {
            // Assign the selected predefined specialization
            Specialization specializationObj = null;
            switch (choice) {
                case 1:
                    specializationObj = new Specialization("Cardiology", "Cardiology is a branch of medicine that deals with heart conditions.");
                    break;
                case 2:
                    specializationObj = new Specialization("Dermatology", "Dermatology is the branch of medicine focused on skin diseases.");
                    break;
                case 3:
                    specializationObj = new Specialization("Neurology", "Neurology is the branch of medicine that deals with the nervous system.");
                    break;
                case 4:
                    specializationObj = new Specialization("Pediatrics", "Pediatrics is the branch of medicine dealing with children and infants.");
                    break;
                case 5:
                    specializationObj = new Specialization("Orthopedics", "Orthopedics is the branch of medicine concerned with the musculoskeletal system.");
                    break;
                case 6:
                    specializationObj = new Specialization("General Surgery", "General surgery involves surgery of the abdominal organs, skin, and soft tissues.");
                    break;
            }

            if (specializationObj != null) {
                DoctorController.addSpecialization(doctor, specializationObj);
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    }

    private void promptGetPatientRecords() {
        System.out.println("Key in PatientID of Patient");
        String pid = Helper.readString();
        PatientController.displayPatientRecord(pid);
    }

    private void promptUpdatePatientRecords() {
        System.out.println("Key in PatientID of Patient");
        String pid = Helper.readString();
        Patient patient = Repository.PATIENT.get(pid);
        System.out.println("Enter new Diagnosis and Treatment plan:");
        System.out.println("Enter Diagnosis:");
        String diagnosisName = Helper.readString();
        System.out.println("Enter Description:");
        String description = Helper.readString();

        int diagId = PatientController.getLastDiagId(patient);
        Diagnosis diagnosis = new Diagnosis(++diagId, diagnosisName, description);

        // Prompt for medications and create a unique treatment
        List<Medicine> treatmentMedicines = new ArrayList<>();
        List<Integer> medicineAmount = new ArrayList<>();

        System.out.println("Enter number of medicines prescribed:");
        int numMedicines = Helper.readInt();
        for (int i = 0; i < numMedicines; i++) {
            System.out.println("Enter Medicine ID or Name:");
            String medKey = Helper.readString();

            Medicine medicine = Repository.INVENTORY.get(medKey).getMedicine();
            if (medicine != null) {
                treatmentMedicines.add(new Medicine(medicine));

                System.out.println("Enter amount of Medicine:");
                int amount = Helper.readInt();
                medicineAmount.add(amount);
                // Create a unique Treatment for each Diagnosis
                Treatment treatment = new Treatment(i, treatmentMedicines, medicineAmount);
                // Add the treatment to the diagnosis
                diagnosis.addTreatment(treatment);
            } else {
                System.out.println("Warning: Medicine with ID '" + medKey + "' not found.");
            }
        }
        patient.getMedicalRecord().addDiagnosis(diagnosis);
        System.out.println("Diagnosis added");
        Repository.PATIENT.put(patient.getPatientId(), patient);
        Repository.persistData(FileType.PATIENT);
    }

    public void recordAppointmentOutcome(Doctor doctor) {
        List<Appointment> docAppointments = new ArrayList<>();

        // Filter appointments for the specified doctor
        docAppointments = AppointmentController.viewConfirmAppointments(doctor);

        // Check if the doctor has any appointments
        if (docAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found for Dr. " + doctor.getName());
            return;
        }

        // Display appointments and prompt the user to select one
        System.out.println("Select an appointment to record outcome:");
        for (int i = 0; i < docAppointments.size(); i++) {
            Appointment appoint = docAppointments.get(i);
            System.out.println((i + 1) + ". Appointment ID: " + appoint.getAppointmentId()
                    + ", Patient: " + appoint.getPatient().getName()
                    + ", Date: " + appoint.getAppointmentStartDate());
        }

        int selectedIndex = Helper.readInt(1, docAppointments.size()) - 1;
        Appointment appointment = docAppointments.get(selectedIndex);
        Patient patient = Repository.PATIENT.get(appointment.getPatient().getPatientId());

        // Checking for existing medical record
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        int diagId = PatientController.getLastDiagId(patient);
        if (medicalRecord == null) {
            medicalRecord = new MedicalRecord("MR" + patient.getPatientId());
            patient.setMedicalRecord(medicalRecord);
        }

        List<Integer> medicineAmounts = new ArrayList<>();
        // Prompt for diagnosis details
        List<Diagnosis> diagList = new ArrayList<>();
        System.out.println("Enter number of Diagnoses: ");
        int diagnoses = Helper.readInt();
        List<Medicine> prescribedMedicines = new ArrayList<>();
        for (int a = 0; a < diagnoses; a++) {
            System.out.println("Enter Diagnosis:");
            String diagnosisName = Helper.readString();
            System.out.println("Enter Description:");
            String description = Helper.readString();

            Diagnosis diagnosis = new Diagnosis(++diagId, diagnosisName, description);

            

            System.out.println("Enter number of Treatments: ");
            int numTreatments = Helper.readInt();
            for(int b = 0; b < numTreatments; b++){
                // Prompt for medications and create a unique treatment
                List<Medicine> treatmentMedicines = new ArrayList<>();
                List<Integer> medicineAmount = new ArrayList<>();
                System.out.println("Treatment " + (b+1) + ": ");
                System.out.println("Enter number of medicines prescribed:");
                int numMedicines = Helper.readInt();
                for (int i = 0; i < numMedicines; i++) {
                    System.out.println("Enter Medicine ID or Name:");
                    String medKey = Helper.readString();

                    Medicine medicine = Repository.INVENTORY.get(medKey).getMedicine();
                    if (medicine != null) {
                        treatmentMedicines.add(new Medicine(medicine));
                        prescribedMedicines.add(new Medicine(medicine));

                        System.out.println("Enter amount of Medicine:");
                        int amount = Helper.readInt();
                        medicineAmount.add(amount);
                        medicineAmounts.add(amount);
                    } else {
                        System.out.println("Warning: Medicine with ID '" + medKey + "' not found.");
                    }
                }
                // Create a unique Treatment for each Diagnosis
                Treatment treatment = new Treatment(b, treatmentMedicines, medicineAmount);
                // Add the treatment to the diagnosis
                diagnosis.addTreatment(treatment);
            }
            medicalRecord.addDiagnosis(diagnosis);
            diagList.add(diagnosis);
        }

        // Getting services provided
        List<ServiceType> services = new ArrayList<>();
        System.out.println("Enter Number of Services provided: ");
        int numServices = Helper.readInt();
        for (int z = 0; z < numServices; z++){
            ServiceType service = null;
            while (service == null){
                System.out.println("Enter Service provided: ");
                String serviceInput = Helper.readString();
                try {
                    // Convert the input to uppercase and then get the corresponding enum
                    service = ServiceType.valueOf(serviceInput.toUpperCase());
                    
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input. No matching service found.");
                    service = null;
                }
            }
            services.add(service);
        }


        System.out.println("Enter Doctor Note: ");
        String note = Helper.readString();

        // Create the AppointmentOutcome
        AppointmentOutcome outcome = new AppointmentOutcome(
            AppointmentOutcomeController.generateApptOutcomeId(),
            prescribedMedicines,
            medicineAmounts,
            diagList,
            services,
            note,
            LocalDateTime.now()
        );

        // Set the outcome and update the medical record
        appointment.setOutcome(outcome);
        Repository.APPOINTMENT_OUTCOME.put(outcome.getOutcomeId(), outcome);
        Repository.persistData(FileType.APPOINTMENT_OUTCOME);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        Repository.persistData(FileType.APPOINTMENT_LIST);
        // Save the updated patient data back to the repository
        Repository.PATIENT.put(patient.getPatientId(), patient);
        Repository.persistData(FileType.PATIENT);
        System.out.println("Outcome recorded successfully.");
    }

    public static String generateApptOutcomeId() {
        String prefix = "OUT";
        int uniqueId = Helper.generateUniqueId(Repository.APPOINTMENT_OUTCOME);
        return prefix + String.format("%03d", uniqueId);
    }

    public static List<Appointment> getAllAppointment() {
        List<Appointment> appointments = new ArrayList<>();

        for (Object apptObj : Repository.APPOINTMENT_LIST.values()) {
            if (apptObj instanceof Appointment) {
                Appointment appointment = (Appointment) apptObj;

                // Add any specific filtering conditions here if needed, for example:
                // - Check if the appointment has an attending doctor
                // - Filter based on certain criteria in appointment ID, attending doctor, etc.
                // Example: add appointment if it has an attending doctor
                if (appointment.getAttendingDoctor() != null) {
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

}
