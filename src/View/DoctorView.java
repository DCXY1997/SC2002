package src.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.Controller.DoctorController;
import src.Helper.Helper;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;
import src.Model.Specialization;

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
        // System.out.println("(1) View All Current Patients");
        // System.out.println("(2) View Patient Medical Records");
        // System.out.println("(3) Update Patient Medical Records");
        System.out.println("(1) View Personal Schedule");
        System.out.println("(2) Set Availability for Appointments");
        System.out.println("(3) Handle Appointments");
        // System.out.println("(4) Record Appointment Outcome");
        System.out.println("(4) View Personal Information");
        System.out.println("(5) Add Specialization");
        System.out.println("(6) Logout");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 11);
            switch (opt) {
                // case 1:
                // Helper.clearScreen();
                // displayAllPatients(doctor.getHospitalId()); // Pass hospitalId
                // break;
                // case 2:
                // Helper.clearScreen();
                // promptGetPatientRecords();
                // break;
                // case 3:
                // Helper.clearScreen();
                // // promptUpdatePatientRecords();
                // break;
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Personal Schedule");
                    promptDisplaySchedule(doctor.getHospitalId()); // Pass hospitalId
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Set Availability");
                    promptAddAvailability(doctor);
                    break;
                case 3:
                    Helper.clearScreen();
                    displayDoctorAppointmentView.viewApp();
                    break;

                // case 8:
                //     Helper.clearScreen();
                //     printBreadCrumbs(
                //             "Hospital Management App View > Doctor View > Record Appointment Outcome");
                //     promptRecordOutcome();
                //     break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > View Personal Information");
                    promptPersonalInformation();
                    break;
                case 5:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Doctor View > Add Specialization");
                    promptSpecialization(doctor);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 6) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 6);
    }

    public void displayAllPatients(String hospitalId) {
        List<Patient> patientList = DoctorController.getAllPatients(hospitalId);
        for (Patient patient : patientList) {
            System.out.println("Patient ID: " + patient.getPatientId());
            System.out.println("Patient Name: " + patient.getName());
        }
    }

    private void promptDisplaySchedule(String hospitalId) {
        List<Schedule> docSchedule = DoctorController.getSchedule(doctor, hospitalId);

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

            // Now add the specialization to the Doctor instance
            if (specializationObj != null) {
                DoctorController.addSpecialization(doctor, specializationObj);
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    }

}
