package src.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.Controller.AppointmentController;
import src.Controller.DoctorController;
import src.Controller.PatientController;
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
        System.out.println("(1) View All Current Patients");
        System.out.println("(2) View Patient Medical Records");
        System.out.println("(3) Update Patient Medical Records");
        System.out.println("(4) View Personal Schedule");
        System.out.println("(5) Set Availability for Appointments");
        System.out.println("(6) Handle Appointments");
        System.out.println("(7) Record Appointment Outcome");
        System.out.println("(8) View Personal Information");
        System.out.println("(9) Add Specialization");
        System.out.println("(10) Logout");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 11);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    displayAllPatients(doctor);
                    break;
                case 2:
                    Helper.clearScreen();
                    promptGetPatientRecords();
                    break;
                case 3:
                    Helper.clearScreen();
                    promptUpdatePatientRecords();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Personal Schedule");
                    promptDisplaySchedule(doctor); // Pass hospitalId
                    break;
                case 5:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Set Availability");
                    promptAddAvailability(doctor);
                    break;
                case 6:
                    Helper.clearScreen();
                    displayDoctorAppointmentView.viewApp();
                    break;
                case 7:
                	Helper.clearScreen();
                	recordAppointmentOutcome(doctor);
                	break;
                case 8:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Personal Information");
                    promptPersonalInformation();
                    break;
                case 9:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Add Specialization");
                    promptSpecialization(doctor);
                    break;
                case 10:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 10) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 10);
    }

    public void displayAllPatients(Doctor doctor) {
        List<Patient> patientList = DoctorController.getAllPatients(doctor); // Update to get patients by hospitalId
        for (Patient patient : patientList) {
            System.out.println("Patient ID: " + patient.getPatientId());
            System.out.println("Patient Name: " + patient.getName());
        }
    }

    private void promptDisplaySchedule(Doctor doctor) {
        // Retrieve the schedule using the DoctorController method
        List<Schedule> docSchedule = DoctorController.getSchedule(doctor);

        // Print each schedule's start and end times
        for (Schedule schedule : docSchedule) {
            System.out.println("From " + schedule.getStartTime() + " to " + schedule.getEndTime());
        }
    }

    // FIX: IMPROVE THIS SO ITS MORE INTUITIVE + SEPARATE INTO DIFFERENT VIEW FILE
    private void promptAddAvailability(Doctor doctor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Prompt for the "from" date
        System.out.println("Key in Availability start date (format: yyyy-MM-dd):");
        String startDate = Helper.readString();
        System.out.println("Key in Availability start time (format: HH:mm):");
        String startTime = Helper.readString();
        LocalDateTime from = LocalDateTime.parse(startDate + " " + startTime, formatter);

        // Prompt for the "to" date
        System.out.println("Key in Availability end date (format: yyyy-MM-dd):");
        String endDate = Helper.readString();
        System.out.println("Key in Availability end time (format: HH:mm):");
        String endTime = Helper.readString();
        LocalDateTime to = LocalDateTime.parse(endDate + " " + endTime, formatter);

        // Set the availability
        DoctorController.addAvailibility(doctor, from, to);
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

            // Now add the specialization to the Doctor instance
            if (specializationObj != null) {
                DoctorController.addSpecialization(doctor, specializationObj);
                System.out.println("Specialization " + specializationObj.getSpecializationName() + " has been added to Doctor " + doctor.getName());
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
    
    private void promptUpdatePatientRecords(){
        promptGetPatientRecords();
        System.out.println("Enter new Diagnosis and Treatment plan:");
        System.out.println("Enter new Diagnosis name:");

    }

    public void recordAppointmentOutcome(Doctor doctor) {
        List<Appointment> docAppointments = new ArrayList<>();

        // Filter appointments for the specified doctor
        docAppointments = AppointmentController.viewDoctorAppointments(doctor);

        // Check if the doctor has any appointments
        if (docAppointments.isEmpty()) {
            System.out.println("No appointments found for Dr. " + doctor.getName());
            return;
        }

        // Display appointments and prompt the user to select one
        System.out.println("Select an appointment to record outcome:");
        for (int i = 0; i < docAppointments.size(); i++) {
            Appointment appoint = docAppointments.get(i);
            System.out.println((i + 1) + ". Appointment ID: " + appoint.getAppointmentId() + 
                               ", Patient: " + appoint.getPatient().getName() + 
                               ", Date: " + appoint.getAppointmentStartDate());
        }

        int selectedIndex = Helper.readInt(1, docAppointments.size()) - 1;
        Appointment appointment = docAppointments.get(selectedIndex);
        Patient patient = Repository.PATIENT.get(appointment.getPatient().getPatientId());

        // Checking for existing medical record
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        int diagId = 0;
        if (medicalRecord == null) {
            medicalRecord = new MedicalRecord("MR" + patient.getPatientId());
            patient.setMedicalRecord(medicalRecord);
        }
        if (!medicalRecord.getDiagnoses().isEmpty()){
            int lastPatientDiag = patient.getMedicalRecord().getDiagnoses().size()-1;
            diagId = patient.getMedicalRecord().getDiagnoses().get(lastPatientDiag).getDiagnosisId();
        }

        // Prompt for diagnosis details
        
        List<Diagnosis> diagList = new ArrayList<>();
        System.out.println("Enter number of Diagnoses: ");
        int diagnoses = Helper.readInt();
        List<Medicine> prescribedMedicines = new ArrayList<>();
        for(int a = 0; a < diagnoses; a++) {
            System.out.println("Enter Diagnosis:");
            String diagnosisName = Helper.readString();
            System.out.println("Enter Description:");
            String description = Helper.readString();

            Diagnosis diagnosis = new Diagnosis(++diagId, diagnosisName, description);

            // Prompt for medications and create a unique treatment
            List<Medicine> treatmentMedicines = new ArrayList<>();
            List<String> medicineAmount = new ArrayList<>();

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
                    String amount = Helper.readString();
                    medicineAmount.add(amount);
                    // Create a unique Treatment for each Diagnosis
                    Treatment treatment = new Treatment(diagId, treatmentMedicines, medicineAmount);
                    // Add the treatment to the diagnosis
                    diagnosis.addTreatment(treatment);
                } else {
                    System.out.println("Warning: Medicine with ID '" + medKey + "' not found.");
                }
            }
            medicalRecord.addDiagnosis(diagnosis);
            diagList.add(diagnosis);
        }
        System.out.println("Enter Doctor Note: ");
        String note = Helper.readString();

        

        

        // Create the AppointmentOutcome
        AppointmentOutcome outcome = new AppointmentOutcome(
            "OUT" + System.currentTimeMillis(),
            prescribedMedicines,
            diagList,
            note,
            LocalDateTime.now()
        );

        // Set the outcome and update the medical record
        appointment.setOutcome(outcome);
        Repository.APPOINTMENT_LIST.put(appointment.getAppointmentId(), appointment);
        // Save the updated patient data back to the repository
        Repository.PATIENT.put(patient.getPatientId(), patient);

        System.out.println("Outcome recorded successfully.");
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
