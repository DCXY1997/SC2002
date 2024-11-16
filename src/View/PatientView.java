package src.View;

import src.Controller.PatientController;
import src.Helper.Helper;
import src.Model.Patient;
import src.Repository.Repository;

public class PatientView extends MainView {

    private Patient patient;
    private DisplayPatientAppointment displayPatientAppointmentView;
    private PaymentView displayPaymentView;

    public PatientView(Patient patient) {
        this.patient = patient;
        // System.out.println("PatientView initialized with patient: " + (patient != null ? patient.getPatientId() : "null"));

        // Create DisplayPatientAppointment instance only if patient is not null
        if (patient != null) {
            this.displayPatientAppointmentView = new DisplayPatientAppointment(patient);
        } else {
            // Handle the case where patient is null
            System.out.println("Error: Patient is null in PatientView.");
        }
        if (patient != null) {
            this.displayPaymentView = new PaymentView(patient);
        } else {
            // Handle the case where patient is null
            System.out.println("Error: Patient is null in PaymentView.");
        }
    }

    /**
     * initialize objects to call their view app
     */
    // private DisplayStaffView displayStaffView = new DisplayStaffView();
    // private DisplayPatientAppointment displayPatientAppointmentView = new DisplayPatientAppointment(patient);
    /**
     * View Actions of the PatientView.
     */
    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Patient Dashboard");
        System.out.println("What would you like to do?");
        System.out.println("(1) View Medical Record");
        System.out.println("(2) Handle Appointments");
        System.out.println("(3) View Past Appointment Outcome Records");
        System.out.println("(4) Update Personal Information");
        System.out.println("(5) View Personal Information");
        System.out.println("(6) Handle Payment");
        System.out.println("(7) Logout");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 8);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    viewMedicalRecord();
                    break;
                case 2:
                    Helper.clearScreen();
                    displayPatientAppointmentView.viewApp();
                    break;
                case 3:
                    printBreadCrumbs(
                    "Hospital Management App View > Patient View > View Past Appointment Outcomes");
                    viewPastAppointmentOutcome();
                    break;
                case 4:
                    printBreadCrumbs(
                            "Hospital Management App View > Patient View > Update Personal Information");
                    promptUpdateDetails();
                    break;
                case 5:
                    printBreadCrumbs(
                            "Hospital Management App View > Patient View > View Personal Information Details");
                    viewPersonalInformation();
                    break;
                case 6:
                    Helper.clearScreen();
                    displayPaymentView.viewApp();
                case 7:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 7) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 7);
    }

    private void promptUpdateDetails() {
        System.out.println("Select the information you want to update:");
        System.out.println("(1) Contact Information");
        System.out.println("(2) Password");
        System.out.println("(3) Back");
        int option = Helper.readInt(1, 3);

        switch (option) {
            case 1:
                promptUpdateContactInformation();
                break;
            case 2:
                promptUpdatePassword();
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void promptUpdateContactInformation() {
        System.out.println("Enter your login ID: ");
        String loginId = Helper.readString();

        // Retrieve the patient from the repository
        Patient patient = Repository.PATIENT.get(loginId);

        if (patient != null) {
            System.out.println("Enter new contact information:");
            String newContactInformation = Helper.readString();

            // Pass the update to the controller
            PatientController.updateContactInformation(patient, newContactInformation);
            System.out.println("Contact information updated successfully!");
        } else {
            System.out.println("Patient not found. Please check your login ID.");
        }
    }

    private void promptUpdatePassword() {
        System.out.println("Verify your login ID: ");
        String loginId = Helper.readString();
        System.out.println("Verify your password: ");
        String password = Helper.readString();

        // Retrieve the patient from the repository
        Patient patient = Repository.PATIENT.get(loginId);

        if (patient != null && patient.getPassword().equals(password)) {
            System.out.println("Verification successful.");
            System.out.println("Enter new password: ");
            String newPassword = Helper.readString();
            System.out.println("Re-enter new password: ");
            String confirmPassword = Helper.readString();

            // Use the controller to change the password
            if (PatientController.changePassword(patient, newPassword, confirmPassword)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Passwords do not match.");
            }
        } else {
            System.out.println("Verification failed. Either the user ID or password is incorrect.");
        }
    }

    private void viewPersonalInformation() {
        String loginId = patient.getPatientId();

        // Retrieve and display personal information using the PatientController
        PatientController.displayPersonalInformation(loginId);
    }

    private void viewMedicalRecord() {
        String loginId = patient.getPatientId();

        // Retrieve and display personal information using the PatientController
        PatientController.displayPatientRecord(loginId);
    }
    
    private void viewPastAppointmentOutcome() {
        String loginId = patient.getPatientId();

        // Retrieve and display personal information using the PatientController
        PatientController.viewPastAppointmentOutcome(loginId);
    }
}
