package src.View;

import src.Controller.AdminController;
import src.Controller.StaffController;
import src.Helper.Helper;
import src.Model.Admin;
import src.Model.Staff;
import src.Repository.Repository;

/**
 * The AdminView class represents the main interface for administrators in the
 * hospital management system.
 * <p>
 * This class provides administrators with access to various functionalities,
 * such as managing staff, appointments, medication inventory, and replenishment
 * requests. It also supports password changes and logout functionality.
 * </p>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>View and manage hospital staff accounts.</li>
 * <li>View and manage appointments.</li>
 * <li>View and manage medication inventory.</li>
 * <li>Approve replenishment requests.</li>
 * <li>Change administrator password.</li>
 * <li>Logout functionality.</li>
 * </ul>
 *
 * @see DisplayStaffView
 * @see ManageStaffAccountView
 * @see DisplayAppointmentDetailView
 * @see DisplayMedicationInventory
 * @see ManageMedicalInventory
 * @see ManageReplenishmentRequestView
 * @see StaffController
 * @see AdminController
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class AdminView extends MainView {

    /**
     * View for displaying hospital staff.
     */
    private DisplayStaffView displayStaffView = new DisplayStaffView();

    /**
     * View for managing staff accounts.
     */
    private ManageStaffAccountView manageStaffAccountView = new ManageStaffAccountView();

    /**
     * View for displaying appointment details.
     */
    private DisplayAppointmentDetailView displayAppointmentDetailsView = new DisplayAppointmentDetailView();

    /**
     * View for displaying medication inventory.
     */
    private DisplayMedicationInventory displayMedicalInventoryView = new DisplayMedicationInventory();

    /**
     * View for managing medication inventory.
     */
    private ManageMedicalInventory manageMedicalInventory = new ManageMedicalInventory();

    /**
     * View for managing replenishment requests.
     */
    private ManageReplenishmentRequestView manageReplenishmentRequestView = new ManageReplenishmentRequestView();

    /**
     * Displays the actions available to the administrator.
     */
    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Hospital Staff");
        System.out.println("(2) Add/Remove/Update Hospital Staff");
        System.out.println("(3) View Appointments Details");
        System.out.println("(4) View Medication Inventory");
        System.out.println("(5) Add/Remove/Update Medication Inventory");
        System.out.println("(6) Approve Replenishment Requests");
        System.out.println("(7) Change Password");
        System.out.println("(8) Logout");
    }

    /**
     * Controls the main workflow of the administrator's view. Allows the
     * administrator to select actions from a menu of options.
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
                    displayStaffView.viewApp();
                    break;
                case 2:
                    Helper.clearScreen();
                    manageStaffAccountView.viewApp();
                    break;
                case 3:
                    Helper.clearScreen();
                    displayAppointmentDetailsView.viewApp();
                    break;
                case 4:
                    Helper.clearScreen();
                    displayMedicalInventoryView.viewApp();
                    break;
                case 5:
                    Helper.clearScreen();
                    manageMedicalInventory.viewApp();
                    break;
                case 6:
                    Helper.clearScreen();
                    manageReplenishmentRequestView.viewApp();
                    break;
                case 7:
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Password Change View");
                    promptChangePassword();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 8) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 8);
    }

    /**
     * Prompts the administrator to change their password after verifying their
     * credentials.
     */
    private void promptChangePassword() {
        System.out.println("Verify your loginID: ");
        String loginId = Helper.readString();
        System.out.println("Verify your password: ");
        String password = Helper.readString();

        Staff staff = Repository.STAFF.get(loginId);

        // Check if the staff exists and verify the password
        if (staff != null && staff.getPassword().equals(password)) {
            System.out.println("Verification successful");

            System.out.println("Enter new password: ");
            String newPassword = Helper.readString();
            System.out.println("Re-enter new password: ");
            String confirmPassword = Helper.readString();

            if (StaffController.changePassword(staff, newPassword, confirmPassword)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Passwords do not match.");
            }
        } else {
            System.out.println("Verification failed. Either the user ID or password is incorrect.");
        }
    }

}
