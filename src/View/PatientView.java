package src.View;

import src.Controller.PatientController;
import src.Helper.Helper;
import src.Model.Patient;

public class PatientView extends MainView {

    private Patient patient;
    private DisplayPatientAppointment displayPatientAppointmentView;

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
        System.out.println("(1) View Medical Record (NOT WORKING YET)");
        System.out.println("(2) Handle Appointments");
        System.out.println("(3) View Past Appointment Outcome Records (NOT WORKING)");
        System.out.println("(4) Update Personal Information");
        System.out.println("(5) View Personal Information");
        System.out.println("(6) Logout");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 6);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    // displayStaffView.viewApp();
                    break;
                case 2:
                    Helper.clearScreen();
                    displayPatientAppointmentView.viewApp();
                    break;
                case 3:
                    Helper.clearScreen();
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
        // String loginId = patient.getPatientId();
        System.out.println("Welcome, " + patient.getName());

        // // Retrieve the patient from the repository
        // Patient patient = Repository.PATIENT.get(loginId);
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
        // String loginId = patient.getPatientId();
        System.out.println("Welcome, " + patient.getName());

        System.out.println("Verify your password: ");
        String password = Helper.readString();

        // Retrieve the patient from the repository
        // Patient patient = Repository.PATIENT.get(loginId);
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

    // private void viewMedicalRecord() {
    // System.out.println("Enter your login ID: ");
    // String loginId = Helper.readString();
    // PatientController.displayMedicalRecord(loginId);
    // }
}
