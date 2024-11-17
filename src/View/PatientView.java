package src.View;

import src.Controller.PatientController;
import src.Helper.Helper;
import src.Model.Patient;

/**
 * The {@code PatientView} class provides an interface for patients to interact
 * with the hospital management system.
 *
 * <p>
 * This includes managing appointments, viewing medical records, updating
 * personal details, handling payments, and accessing past appointment
 * outcomes.</p>
 *
 * @author Jasmine Tye, Bryan, Darren
 * @version 1.0
 * @since 2024-11-17
 */
public class PatientView extends MainView {

    private Patient patient;
    private DisplayPatientAppointment displayPatientAppointmentView;
    private PaymentView displayPaymentView;

    /**
     * Constructs a {@code PatientView} instance for the specified patient.
     *
     * @param patient The patient associated with this view.
     * @throws IllegalArgumentException if the {@code patient} is {@code null}.
     */
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
     * Displays the available actions for the patient.
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

    /**
     * Displays the patient dashboard and handles user interactions.
     */
    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 8);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Patient View > View Medical Record");
                    viewMedicalRecord();
                    break;
                case 2:
                    Helper.clearScreen();
                    displayPatientAppointmentView.viewApp();
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Patient View > View Past Appointment Outcomes");
                    viewPastAppointmentOutcome();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Patient View > Update Personal Information");
                    promptUpdateDetails();
                    break;
                case 5:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Patient View > View Personal Information Details");
                    viewPersonalInformation();
                    break;
                case 6:
                    Helper.clearScreen();
                    displayPaymentView.viewApp();
                    break;
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

    /**
     * Prompts the patient to update their personal details.
     */
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

    /**
     * Prompts the patient to update their contact information.
     */
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

    /**
     * Prompts the patient to update their password.
     */
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

    /**
     * Displays the personal information of the patient.
     */
    private void viewPersonalInformation() {
        String loginId = patient.getPatientId();

        // Retrieve and display personal information using the PatientController
        PatientController.displayPersonalInformation(loginId);
    }

    /**
     * Displays the medical record of the patient.
     */
    private void viewMedicalRecord() {
        String loginId = patient.getPatientId();

        // Retrieve and display personal information using the PatientController
        PatientController.displayPatientRecord(loginId);
    }

    /**
     * Displays the past appointment outcomes for the patient.
     */
    private void viewPastAppointmentOutcome() {
        String loginId = patient.getPatientId();

        // Retrieve and display personal information using the PatientController
        PatientController.viewPastAppointmentOutcome(loginId);
    }
}
