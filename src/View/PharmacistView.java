package src.View;

import src.Controller.*;
import src.Helper.Helper;
import src.Model.Staff;
import src.Repository.Repository;

public class PharmacistView extends MainView{
	
	private AppointmentOutcomeView appointmentOutcomeView = new AppointmentOutcomeView();
	//private PharmacistController pharmacistController = new PharmacistController();
	private InventoryView inventoryView = new InventoryView();
	
	public void printActions()
	{
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
	
	public void viewApp()
	{
		int opt = -1;
		do
		{
			printActions();
			opt = Helper.readInt(1, 6);
			Helper.clearScreen();
			switch (opt) 
			{
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
			if (opt!=6)
				Helper.pressAnyKeyToContinue();
		}while(opt !=6);
	}
	
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
}
