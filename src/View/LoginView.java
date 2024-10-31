package src.View;

import src.Controller.UserController;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Repository.Repository;

public class LoginView extends MainView {

    private AdminView adminView = new AdminView();

    @Override
	protected void printActions() {
		printBreadCrumbs("Hospital Management App View > Login View");
		System.out.println("Choose employee type:");
		System.out.println("(1) Admin");
		System.out.println("(2) Doctor");
		System.out.println("(3) Pharmacist");
		System.out.println("(4) Back");
	}
	/**
	 * View Application for LoginView that uses {@link UserController} to authenticate login 
	 */

	@Override
	public void viewApp() {
		int role = -1;
		StaffType staffType = null;

		do {
			printActions();
			role = Helper.readInt(1,4);
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
		} while(role <= 0 || role> 4);
		
		if(role == 4) {
			return;
		}

		String loginId;
		String password;
		
		System.out.println("\nLogin ID:");
		loginId = Helper.readString();
		System.out.println("\nPassword:");
		password = Helper.readString();
		
		
		boolean loginSuccess = UserController.authenticate(loginId, password, staffType);
		if (loginSuccess) {
			System.out.println("Login successful, welcome " +loginId);
			
			if(staffType == StaffType.ADMIN) {
                adminView.viewApp();
			}
		} 
		
		else {
			System.out.println("Invalid username/password or employee position");
		}
	}
}
