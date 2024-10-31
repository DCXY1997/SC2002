package src.View;

import src.Helper.Helper;

public class HospitalManagementAppView extends MainView {

	private AdminView adminView = new AdminView();
	private LoginView loginView = new LoginView();

	@Override
	protected void printActions() {
		printBreadCrumbs("Hospital Management App View");
        System.out.println("Who would you like to continue as?");
        System.out.println("(1) Admin");
        System.out.println("(2) Quit Hospital Management App");
	}

	@Override
	public void viewApp() {
    	int choice;
    	do {
        	printActions();
        	choice = Helper.readInt();
        	switch(choice) {
            	case 1:
                	loginView.viewApp();
                	break;
           		case 2:
                	System.out.println("Thank you for using Hospital Management App!");
                	return;  // Exit the method immediately to avoid further checks
            	default:
                	System.out.println("Invalid input! Please try again.");
                	break;
        	}
    	} while (choice != 2);
	}	
}
