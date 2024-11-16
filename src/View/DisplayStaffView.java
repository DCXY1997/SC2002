package src.View;
import src.Controller.AdminController;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;

public class DisplayStaffView extends MainView{
	/**
     * View Actions of the DisplayStaffView.
     */
    @Override
	public void printActions() {
		Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Staff View");
        System.out.println("Select a filter: ");
        System.out.println("(1) Role");
        System.out.println("(2) Gender");
        System.out.println("(3) Age");
        System.out.println("(4) Back");
	}
    /**
     * View Application of the DisplayStaffView. <p>
     */
    @Override
	public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 4);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Staff View > Display Staff by Role View");
                    promptDisplayStaffByRole();
                    Helper.pressAnyKeyToContinue();
                    break;
                case 2: 
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Staff View > Display Staff by Gender View");
                    promptDisplayStaffByGender();
                    Helper.pressAnyKeyToContinue();
                    break;
                case 3: 
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management View > Login View > Admin View > Display Staff View > Display Staff by Age View");
                    promptDisplayStaffByAge();
                    Helper.pressAnyKeyToContinue();
                    break;
                case 4: 
                    Helper.pressAnyKeyToContinue();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (opt != 4);
	}
    /**
     * function to print role menu
     */
    private void printRoleMenu() {
        System.out.println("(1) DOCTOR ");
        System.out.println("(2) PHARMACIST ");
        System.out.println("(3) ADMIN ");
    }
    /**
	 * function to prompt to display staff by role
	 * @return {@code true} if successfully display the staff list. Otherwise, {@code false}.
	 */
    private boolean promptDisplayStaffByRole() {
		System.out.println("Enter the role name: ");
		printRoleMenu();
		int opt = -1;
		opt = Helper.readInt();
		switch(opt) {
			case 1:
				AdminController.displayStaffListByRole(StaffType.DOCTOR);
				return true;
			case 2:
				AdminController.displayStaffListByRole(StaffType.PHARMACIST);
				return true;
            case 3:
				AdminController.displayStaffListByRole(StaffType.ADMIN);
                return true;
			default:
                System.out.println("Invalid option");
                return false;
		}
    }
    /**
     * function to print gender menu
     */
    private void printGenderMenu() {
		int i = 1;
        for(Gender gender : Gender.values()) {
        	System.out.println("(" + i + ") " + gender);
			i++;
        }
    }
    /**
	 * function to prompt to display staff by gender
	 * @return {@code true} if successfully display the staff list. Otherwise, {@code false}.
	 */
    private boolean promptDisplayStaffByGender() {
		System.out.println("Enter the gender: ");
		printGenderMenu();
		int opt = -1;
		opt = Helper.readInt();
		switch(opt) {
			case 1:
				AdminController.displayStaffListByGender(Gender.MALE);
				return true;
			case 2:
				AdminController.displayStaffListByGender(Gender.FEMALE);
				return true;
			default:
                System.out.println("Invalid option");
                return false;
		}
    }
    /**
	 * function to prompt to display staff by age
	 * @return {@code true} if successfully display the staff list. Otherwise, {@code false}.
	 */
    private boolean promptDisplayStaffByAge() {
		System.out.println("Enter the age: ");
		int age = Helper.readInt();
		return AdminController.displayStaffListByAge(age);
    }
}