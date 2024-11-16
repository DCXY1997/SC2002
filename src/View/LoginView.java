package src.View;

import src.Controller.PatientController;
import src.Controller.StaffController;
//import src.Controller.UserController;
import src.Enum.StaffType;
import src.Helper.Helper;

public class LoginView extends MainView {

	private AdminView adminView = new AdminView();
	private PatientView patientView = new PatientView();
	private PharmacistView pharmacistView = new PharmacistView();

	@Override
	protected void printActions() {
		printBreadCrumbs("Hospital Management App View > Login View");
		System.out.println("Choose employee type:");
		System.out.println("(1) Admin");
		System.out.println("(2) Doctor");
		System.out.println("(3) Pharmacist");
		System.out.println("(4) Back");
	}

	@Override
	public void viewApp() {
		viewApp(true);
	}

	/**
	 * View Application for LoginView that uses {@link StaffController} to
	 * authenticate login (Staff)
	 */
	public void viewApp(boolean isStaff) {
		if (isStaff) {
			int role = -1;
			StaffType staffType = null;

			do {
				printActions();
				role = Helper.readInt(1, 4);
				switch (role) {
					case 1:
						staffType = StaffType.ADMIN;
						break;
					case 2:
						staffType = StaffType.DOCTOR;
						break;
					case 3:
						staffType = StaffType.PHARMACIST;
						break;
					case 4:
						break;
					default:
						System.out.println("Invalid option. Please try again.");
						break;
				}
			} while (role <= 0 || role > 4);

			// If the user selects 'Back', exit the method
			if (role == 4) {
				return;
			}

			// Handle staff login
			String loginId;
			String password;

			System.out.println("\nLogin ID:");
			loginId = Helper.readString();
			System.out.println("\nPassword:");
			password = Helper.readString();

			boolean loginSuccess = StaffController.authenticate(loginId, password, staffType);
			if (loginSuccess) {
				System.out.println("Login successful, welcome " + loginId);
				Helper.pressAnyKeyToContinue();

				if (staffType == StaffType.ADMIN) {
					adminView.viewApp();
				}
				else if (staffType == StaffType.PHARMACIST)
					pharmacistView.viewApp();
			} else {
				System.out.println("Invalid username/password or employee position");
			}
		} else {
			viewAppPatient();
		}
	}

	// handle patient login
	public void viewAppPatient() {
		String patientId;
		String password;

		System.out.println("\nPatient ID:");
		patientId = Helper.readString();
		System.out.println("\nPassword:");
		password = Helper.readString();

		boolean loginSuccess = PatientController.authenticate(patientId, password);
		if (loginSuccess) {
			System.out.println("Patient login successful, welcome " + patientId);
			patientView.viewApp();

		} else {
			System.out.println("Invalid patient ID/password.");
		}
	}
}
