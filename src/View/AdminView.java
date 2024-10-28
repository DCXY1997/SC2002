package src.View;

import src.Controller.AdminController;
import src.Helper.Helper;

public class AdminView extends MainView{
	
	/**
	 * initialize objects to call their view app
	 */
	private DisplayStaffView displayStaffView = new DisplayStaffView();
    private ManageStaffAccountView manageStaffAccountView = new ManageStaffAccountView();
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
        System.out.println("(7) Logout");
	}

    @Override
	public void viewApp() { 
		int opt = -1; 
		do { 
            printActions();
            opt = Helper.readInt(1,7);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    displayStaffView.viewApp();
                    break;
                case 2:
                    Helper.clearScreen();
                    manageStaffAccountView.viewApp();
                    break;
                // case 3:
                    // Helper.clearScreen();
                    // printBreadCrumbs("Fast Food App View > Login View > Admin View > Assign Manager View");
                    // promptAssignManager();
                    // break;
                // case 4:
                	// Helper.clearScreen();
                	// printBreadCrumbs("Fast Food App View > Login View > Admin View > Promote Staff View");
                	// promptPromoteStaff();
                    // break;
                // case 5:
                	// Helper.clearScreen();
                	// printBreadCrumbs("Fast Food App View > Login View > Admin View > Transfer Staff View");
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
                // case 8:
                	// printBreadCrumbs("Fast Food App View > Login View > Admin View > Password Change View");
                	// promptChangePassword();
                	// break;
                case 9:
                	break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 9) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 9);
	}
    
}