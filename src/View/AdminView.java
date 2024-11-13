package src.View;

import src.Controller.AdminController;
import src.Controller.StaffController;
import src.Helper.Helper;
import src.Model.Staff;
import src.Repository.Repository;

public class AdminView extends MainView {

	/**
	 * initialize objects to call their view app
	 */
	private DisplayStaffView displayStaffView = new DisplayStaffView();
    private ManageStaffAccountView manageStaffAccountView = new ManageStaffAccountView();
	private DisplayAppointmentDetailView displayAppointmentDetailsView = new DisplayAppointmentDetailView();
	private DisplayMedicationInventory displayMedicalInventoryView = new DisplayMedicationInventory();
	private ManageMedicalInventory manageMedicalInventory = new ManageMedicalInventory();
	private ManageReplenishmentRequestView manageReplenishmentRequestView = new ManageReplenishmentRequestView();
	/**
	 * View Actions of the AdminView.
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

    @Override
	public void viewApp() { 
		int opt = -1; 
		do { 
            printActions();
            opt = Helper.readInt(1,8);
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
				case 2:
					Helper.clearScreen();
					manageStaffAccountView.viewApp();
					break;
				// case 3:
				// Helper.clearScreen();
				// printBreadCrumbs("Fast Food App View > Login View > Admin View > Assign
				// Manager View");
				// promptAssignManager();
				// break;
				// case 4:
				// Helper.clearScreen();
				// printBreadCrumbs("Fast Food App View > Login View > Admin View > Promote
				// Staff View");
				// promptPromoteStaff();
				// break;
				// case 5:
				// Helper.clearScreen();
				// printBreadCrumbs("Fast Food App View > Login View > Admin View > Transfer
				// Staff View");
				// promptTransferStaff();
				// break;
				// case 6:
				// Helper.clearScreen();
				// managePaymentView.viewApp();
				// break;
				// case 7:
				// Helper.clearScreen();
				// manageBranchView.viewApp();
				// break;
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