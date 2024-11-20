package src.View;

import src.Controller.AdminController;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Repository.Repository;

/**
 * The ManageStaffAccountView class provides an interface for administrators to
 * manage hospital staff accounts in the hospital management system.
 * <p>
 * This class allows administrators to:
 * </p>
 * <ul>
 * <li>Add new staff accounts with details such as name, role, gender, and
 * age.</li>
 * <li>Remove existing staff accounts by their hospital ID.</li>
 * <li>Update staff account details, including name, gender, and age.</li>
 * <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Integrates with {@link AdminController} to handle staff management
 * operations.</li>
 * <li>Provides prompts and validation to ensure accurate data input.</li>
 * <li>Generates unique hospital IDs for staff accounts based on their
 * roles.</li>
 * </ul>
 *
 * @see AdminController
 * @see Helper
 * @see Repository
 * @see StaffType
 * @see Gender
 * @see MainView
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class ManageStaffAccountView extends MainView {

    /**
     * Displays the actions available in the Manage Staff Account View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     * <li>(1) Add new staff</li>
     * <li>(2) Remove staff</li>
     * <li>(3) Update staff details</li>
     * <li>(4) Exit the menu</li>
     * </ul>
     */
    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Staff Account View");
        System.out.println("What would you like to do?");
        System.out.println("(1) Add new staff");
        System.out.println("(2) Remove staff");
        System.out.println("(3) Update staff");
        System.out.println("(4) Exit");
    }

    /**
     * Controls the workflow of the Manage Staff Account View.
     * <p>
     * Allows the administrator to select and execute staff management
     * operations such as adding, removing, or updating staff accounts.
     * </p>
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

    /**
     * Prompts the administrator to add a new staff account.
     * <p>
     * Collects details such as name, role, gender, and age, generates a unique
     * hospital ID, and creates the staff account.
     * </p>
     *
     * @return {@code true} if the staff account is successfully added,
     * {@code false} otherwise.
     */
    private boolean promptAddStaffAccount() {
        System.out.println("Enter staff name:");
        String name = Helper.readString();

        String password = "password"; // Default password

        StaffType role = promptRole();
        if (role == null) {
            System.out.println("Invalid role! Add staff unsuccessful!");
            return false;
        }

        Gender gender = promptGender();
        if (gender == null) {
            System.out.println("Invalid gender! Add staff unsuccessful!");
            return false;
        }

        System.out.println("Enter the staff's age:");
        int age = Helper.readInt();

        // Generate the hospital ID based on the role prefix
        String hospitalId = generateHospitalId(role);
        AdminController.addStaffAccount(name, password, gender, age, hospitalId, role);
        return true;
    }

    /**
     * Generates a unique hospital ID for a new staff account based on their
     * role.
     *
     * @param role The role of the staff (e.g., Doctor, Pharmacist, Admin).
     * @return A unique hospital ID string.
     */
    private String generateHospitalId(StaffType role) {
        String prefix = "";
        switch (role) {
            case DOCTOR:
                prefix = "D";
                break;
            case PHARMACIST:
                prefix = "P";
                break;
            case ADMIN:
                prefix = "A";
                break;
        }
        int uniqueId = Helper.generateUniqueStaffId(Repository.STAFF);
        return prefix + String.format("%03d", uniqueId); // e.g., D001
    }

    /**
     * Prompts the administrator to remove a staff account.
     * <p>
     * Validates the hospital ID before proceeding with the removal.
     * </p>
     *
     * @return {@code true} if the staff account is successfully removed,
     * {@code false} otherwise.
     */
    private boolean promptRemoveStaffAccount() {
        Helper.clearScreen();
        printBreadCrumbs("Hotel Management App View > Admin View > Remove a staff");
        System.out.println("Enter the hospital ID of the staff that you want to remove: ");
        String hospitalId = Helper.readString();

        // First, check if the staff exists
        if (Repository.STAFF.containsKey(hospitalId)) {
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

    /**
     * Prompts the administrator to update a staff account.
     * <p>
     * Allows updating details such as name, gender, or age based on the user's
     * choice.
     * </p>
     *
     * @return {@code true} if the staff account is successfully updated,
     * {@code false} otherwise.
     */
    private boolean promptUpdateStaff() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management View > Admin View > Update a Staff Detail");
        System.out.println("Enter the staff hospital ID that you want to update: ");
        String hospitalId = Helper.readString();
        if (AdminController.searchStaffById(hospitalId).size() == 0) {
            System.out.println("Staff not found!");
            return false;
        }
        printUpdateStaffMenu();
        int opt = -1;
        opt = Helper.readInt(1, 3);
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

    /**
     * Prints a menu for updating staff account details.
     * <p>
     * The options include updating:
     * </p>
     * <ul>
     * <li>(1) Name</li>
     * <li>(2) Gender</li>
     * <li>(3) Age</li>
     * </ul>
     */
    private void printUpdateStaffMenu() {
        System.out.println("Please choose the information that you want to update (1-3)");
        System.out.println("(1) Name");
        System.out.println("(2) Gender");
        System.out.println("(3) Age");
    }

    /**
     * Prompts the administrator to select a gender for the staff account.
     * <p>
     * Displays a menu and validates the selection.
     * </p>
     *
     * @return The selected {@link Gender}, or {@code null} if the input is
     * invalid.
     */
    private Gender promptGender() {
        printGenderMenu();
        int choice = Helper.readInt(1, 2);
        switch (choice) {
            case 1:
                return Gender.MALE;
            case 2:
                return Gender.FEMALE;
            default:
                return null;
        }
    }

    /**
     * Displays a menu for selecting a staff role.
     * <p>
     * The options include:
     * </p>
     * <ul>
     * <li>(1) Doctor</li>
     * <li>(2) Pharmacist</li>
     * <li>(3) Admin</li>
     * </ul>
     *
     * @return The selected {@link StaffType}, or {@code null} if the input is
     * invalid.
     */
    private StaffType promptRole() {
        printRoleMenu();
        int choice = Helper.readInt(1, 3);
        switch (choice) {
            case 1:
                return StaffType.DOCTOR;
            case 2:
                return StaffType.PHARMACIST;
            case 3:
                return StaffType.ADMIN;
            default:
                return null;
        }
    }

    /**
     * Prints the menu for selecting a gender.
     */
    private void printGenderMenu() {
        System.out.println("Please enter the staff's gender (1-2)");
        System.out.println("(1) Male");
        System.out.println("(2) Female");
    }

    /**
     * Prints the menu for selecting a staff role.
     */
    private void printRoleMenu() {
        System.out.println("Please enter the staff's role (1-3)");
        System.out.println("(1) Doctor");
        System.out.println("(2) Pharmacist");
        System.out.println("(3) Admin");
    }
}
