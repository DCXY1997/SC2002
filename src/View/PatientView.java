package src.View;

import src.Controller.PatientController;
import src.Helper.Helper;
import src.Model.Patient;
import src.Repository.Repository;

public class PatientView extends MainView {

	/**
	 * initialize objects to call their view app
	 */
	// private DisplayStaffView displayStaffView = new DisplayStaffView();
	// private ManageStaffAccountView manageStaffAccountView = new
	// ManageStaffAccountView();
	/**
	 * View Actions of the PatientView.
	 */
	@Override
	public void printActions() {
		Helper.clearScreen();
		printBreadCrumbs("Hospital Management App View > Login View > Patient View");
		System.out.println("What would you like to do?");
		// System.out.println("(1) View Medical Record");
		// System.out.println("(2) View Available Appointment Slots");
		// System.out.println("(3) Schedule an Appointment");
		// System.out.println("(4) Reschedule an Appointment");
		// System.out.println("(5) Cancel an Appointment");
		// System.out.println("(6) View Scheduled Appointments");
		// System.out.println("(7) View Past Appointment Outcome Records");
		System.out.println("(8) Update Personal Information");
		System.out.println("(9) View Personal Information");
		System.out.println("(10) Logout");
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
					// displayStaffView.viewApp();
					break;
				// case 2:
				// Helper.clearScreen();
				// manageStaffAccountView.viewApp();
				// break;
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
				case 8:
					printBreadCrumbs(
							"Hospital Management App View > Patient View > Update Personal Information");
					promptUpdateDetails();
					break;
				case 9:
					printBreadCrumbs(
							"Hospital Management App View > Patient View > View Personal Information Details");
					viewPersonalInformation();
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
		System.out.println("Enter your login ID: ");
		String loginId = Helper.readString();

		// Retrieve and display personal information using the PatientController
		PatientController.displayPersonalInformation(loginId);
	}
	
	private void viewMedicalRecord() {
		System.out.println("Enter your login ID: ");
		String loginId = Helper.readString();
		
		PatientController.displayMedicalRecord(loginId);
	}

}