package src.View;

import src.Controller.*;
import src.Helper.Helper;
import src.Model.Patient;
import src.Model.Staff;
import src.Repository.Repository;

/**
 * The {@code PharmacistView} class represents the user interface for pharmacists in the
 * hospital management system. It provides options for pharmacists to manage prescriptions,
 * view appointment outcomes, handle inventory, and submit replenishment requests.
 * 
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>View appointment outcome records.</li>
 *   <li>Update prescription statuses.</li>
 *   <li>Manage medication inventory.</li>
 *   <li>Submit replenishment requests for medications.</li>
 *   <li>Change account password.</li>
 * </ul>
 * 
 * <p><b>Associated Views and Controllers:</b></p>
 * <ul>
 *   <li>{@link AppointmentOutcomeView}</li>
 *   <li>{@link InventoryView}</li>
 *   <li>{@link PaymentView}</li>
 *   <li>{@link PharmacistController}</li>
 * </ul>
 *
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class PharmacistView extends MainView {

    /**
     * Manages the appointment outcomes interface.
     */
    private AppointmentOutcomeView appointmentOutcomeView = new AppointmentOutcomeView();

    /**
     * Manages the inventory interface.
     */
    private InventoryView inventoryView = new InventoryView();

    /**
     * Handles patient payments. Initialized when interacting with a patient.
     */
    private PaymentView paymentView;

    /**
     * Displays the available actions for the pharmacist interface.
     */
    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View");
        System.out.println("What would you like to do? ");
        System.out.println("(1) View Appointment Outcome Record");
        System.out.println("(2) Update Prescription Status");
        System.out.println("(3) View Medication Inventory");
        System.out.println("(4) Submit Replenishment Request");
        System.out.println("(5) Change Password");
        System.out.println("(6) Logout");
    }

    /**
     * Handles the main navigation for the pharmacist interface.
     * Provides access to various functionalities like viewing outcomes, managing prescriptions, 
     * handling inventory, and changing passwords.
     */
    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 6);
            Helper.clearScreen();
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    appointmentOutcomeView.viewApp();
                    break;
                case 2:
                    Helper.clearScreen();
                    appointmentOutcomeView.displayPrescriptionStatus();
                    break;
                case 3:
                    Helper.clearScreen();
                    inventoryView.viewApp();
                    break;
                case 4:
                    Helper.clearScreen();
                    inventoryView.displayReplenishmentRequest();
                    break;
                case 5:
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Password Change View");
                    promptChangePassword();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid Option.");
            }
            if (opt != 6) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 6);
    }

    /**
     * Prompts the pharmacist to change their password.
     * Validates the login ID and current password before allowing the password change.
     */
    private void promptChangePassword() {
        System.out.println("Verify your loginID: ");
        String loginId = Helper.readString();
        System.out.println("Verify your password: ");
        String password = Helper.readString();

        Staff pharmacist = Repository.STAFF.get(loginId);

        // Check if the staff exists and verify the password
        if (pharmacist != null && pharmacist.getPassword().equals(password)) {
            System.out.println("Verification successful");

            System.out.println("Enter new password: ");
            String newPassword = Helper.readString();
            System.out.println("Re-enter new password: ");
            String confirmPassword = Helper.readString();

            if (PharmacistController.changePassword(pharmacist, newPassword, confirmPassword)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Passwords do not match.");
            }
        } else {
            System.out.println("Verification failed. Either the user ID or password is incorrect.");
        }
    }

    /**
     * Prompts the pharmacist to enter a patient ID and initializes the {@link PaymentView}
     * for the corresponding patient.
     */
    private void promptPatientId() {
        System.out.println("Enter Patient ID: ");
        String pid = Helper.readString();
        Patient patient = Repository.PATIENT.get(pid);
        paymentView = new PaymentView(patient);
    }
}
