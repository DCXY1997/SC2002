package src.View;
import src.Controller.AdminController;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Repository.Repository;

public class ManageStaffAccountView extends MainView{
	/**
     * View Actions of the ManageStaffAccountView.
     */
    @Override
	public void printActions() {
		Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Staff Account View");
        System.out.println("What would you like to do ?");
        System.out.println("(1) Add new staff");
        System.out.println("(2) Remove staff");
        System.out.println("(3) Update staff");
        System.out.println("(4) Exit");
    }
    /**
     * View Application of the ManageStaffAccountView. <p>
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
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Staff Account View > Add Staff Account View");
                    promptAddStaffAccount();
                    break;
                case 2: 
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Staff Account View > Remove Staff Account View");
                    promptRemoveStaffAccount();
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Staff Account View > Update Staff Account View");
                    promptUpdateStaff();
                    break;
                case 4:
                	break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 4) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 4);
	}
	
	private boolean promptAddStaffAccount() {
		System.out.println("Enter staff hospitalId:");
		String hospitalId = Helper.readString();
		System.out.println("Enter staff name:");
        String name = Helper.readString();
        System.out.println("Enter password:");
        String password = "password";
        int opt = -1;
        opt = Helper.readInt();
        StaffType role = promptRole();
        
        if(role == null) {
        	System.out.println("The position is null! Add staff unsuccessful!");
        	return false;
        }
        
        Gender gender = promptGender();
        if(gender == null) return false;
        
        System.out.println("Enter the staff's age");
        int age = Helper.readInt();
        
        AdminController.addStaffAccount(name, password, gender, age, hospitalId, role);
        return true;
        
	}

	private void printGenderMenu() {
        System.out.println("Please enter the staff's gender (1-2)");
        System.out.println("(1) Male");
        System.out.println("(2) Female");
    }

	private Gender promptGender() {
        printGenderMenu();
        int choice = Helper.readInt(1, 2);
        if (choice != 1 && choice != 2) {
            return null;
        } else {
            switch (choice) {
                case 1:
                    return Gender.MALE;
                case 2:
                    return Gender.FEMALE;
                default:
                    break;
            }
        }
        return null;
    }
 
    private void printRoleMenu() {
        System.out.println("Please enter the staff's role (1-2)");
        System.out.println("(1) Doctor");
        System.out.println("(2) Pharmacist");
    }

    private StaffType promptRole() {
        printRoleMenu();
        int choice = Helper.readInt(1, 3);
        if (choice < 1 || choice > 3) {
            return null;
        } else {
            switch (choice) {
                case 1:
                    return StaffType.DOCTOR;
                case 2:
                    return StaffType.PHARMACIST;
                default:
                    break;
            }
        }
        return null;
    }

    private boolean promptRemoveStaffAccount() {
    Helper.clearScreen();
    printBreadCrumbs("Hotel Management App View > Admin View > Remove a staff");
    System.out.println("Enter the hospital id of the staff that you want to remove: ");
    String hospitalId = Helper.readString();
    
    // First, check if the staff exists
    if (Repository.STAFF.containsKey(hospitalId)) {
        // Call removeStaffAccount and handle the result accordingly
        if (!AdminController.removeStaffAccount(hospitalId)) {
            System.out.println("Staff removal canceled!");
            return false;
        }
    } else {
        System.out.println("Staff not found!");
        return false;
    }
    return true;
}


    private boolean promptUpdateStaff() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management View > Admin View > Update a Staff Detail");
        System.out.println("Enter the staff hospital Id that you want to update: ");
        String hospitalId = Helper.readString();
        if (AdminController.searchStaffById(hospitalId).size() == 0) {
            System.out.println("Staff not found!");
            return false;
        }
        printUpdateStaffMenu();
        int opt = -1;
        opt = Helper.readInt(1, 5);
        switch (opt) {
            case 1:
                System.out.println("Please enter the staff's new name:");
                String name = Helper.readString();
                AdminController.updateStaffAccount(hospitalId, name, 1);
                return true;
            case 2:
            	Gender gender = promptGender();
                if (gender == null) {
                    return false;
                }
                AdminController.updateStaffAccount(hospitalId, 2, gender);
                return true;
            case 3:
                System.out.println("Please enter the staff's new age:");
                int age = Helper.readInt();
                AdminController.updateStaffAccount(hospitalId, 3, age);
                return true;
            default:
                break;
        }
        return false;
    }

    private void printUpdateStaffMenu() {
        System.out.println("Please choose the information that you want to update (1-3)");
        System.out.println("(1) Name");
        System.out.println("(2) Gender");
        System.out.println("(3) Age");
    }
	
}
