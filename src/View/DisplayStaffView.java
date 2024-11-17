package src.View;

import src.Controller.AdminController;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;

/**
 * The DisplayStaffView class provides an interface for administrators to view
 * hospital staff based on specific filters in the hospital management system.
 * <p>
 * This class allows administrators to:
 * </p>
 * <ul>
 * <li>Filter staff by role (e.g., Doctor, Pharmacist, Admin).</li>
 * <li>Filter staff by gender (Male, Female).</li>
 * <li>Filter staff by age.</li>
 * <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Integrates with {@link AdminController} to fetch filtered staff
 * records.</li>
 * <li>Provides a user-friendly menu for selecting filters.</li>
 * </ul>
 *
 * @see AdminController
 * @see Helper
 * @see MainView
 * @see StaffType
 * @see Gender
 * @author Keng Jia Chi
 * @version 1.0
 * @sin ce 2024-11-17
 */
public class DisplayStaffView extends MainView {

    /**
     * Displays the actions available in the Display Staff View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     * <li>(1) Filter staff by role.</li>
     * <li>(2) Filter staff by gender.</li>
     * <li>(3) Filter staff by age.</li>
     * <li>(4) Navigate back to the previous menu.</li>
     * </ul>
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
     * Controls the workflow of the Display Staff View.
     * <p>
     * Allows the administrator to choose from the available filters and
     * interact with the staff display interface.
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
     * Prints the role menu to the console.
     * <p>
     * The roles include:
     * </p>
     * <ul>
     * <li>(1) Doctor</li>
     * <li>(2) Pharmacist</li>
     * <li>(3) Admin</li>
     * </ul>
     */
    private void printRoleMenu() {
        System.out.println("(1) DOCTOR ");
        System.out.println("(2) PHARMACIST ");
        System.out.println("(3) ADMIN ");
    }

    /**
     * Prompts the administrator to select a role filter and displays the staff
     * based on the selected role.
     *
     * @return {@code true} if the staff list is successfully displayed,
     * {@code false} otherwise.
     */
    private boolean promptDisplayStaffByRole() {
        System.out.println("Enter the role name: ");
        printRoleMenu();
        int opt = -1;
        opt = Helper.readInt();
        switch (opt) {
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
     * Prints the gender menu to the console.
     * <p>
     * The genders include:
     * </p>
     * <ul>
     * <li>(1) Male</li>
     * <li>(2) Female</li>
     * </ul>
     */
    private void printGenderMenu() {
        int i = 1;
        for (Gender gender : Gender.values()) {
            System.out.println("(" + i + ") " + gender);
            i++;
        }
    }

    /**
     * Prompts the administrator to select a gender filter and displays the
     * staff based on the selected gender.
     *
     * @return {@code true} if the staff list is successfully displayed,
     * {@code false} otherwise.
     */
    private boolean promptDisplayStaffByGender() {
        System.out.println("Enter the gender: ");
        printGenderMenu();
        int opt = -1;
        opt = Helper.readInt();
        switch (opt) {
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
     * Prompts the administrator to enter an age filter and displays the staff
     * based on the entered age.
     *
     * @return {@code true} if the staff list is successfully displayed,
     * {@code false} otherwise.
     */
    private boolean promptDisplayStaffByAge() {
        System.out.println("Enter the age: ");
        int age = Helper.readInt();
        return AdminController.displayStaffListByAge(age);
    }
}
