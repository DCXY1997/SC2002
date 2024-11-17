package src.View;

import src.Helper.Helper;

/**
 * The HospitalManagementAppView class represents the main interface of the hospital management system.
 * <p>
 * This class serves as the entry point for the application, allowing users to:
 * </p>
 * <ul>
 *   <li>Log in as staff members.</li>
 *   <li>Log in as patients.</li>
 *   <li>Quit the application.</li>
 * </ul>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Integrates with {@link LoginView} for authentication.</li>
 *   <li>Provides navigation options for staff and patients.</li>
 *   <li>Offers a clean and user-friendly interface for starting the application.</li>
 * </ul>
 *
 * @see LoginView
 * @see AdminView
 * @see Helper
 * @see MainView
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class HospitalManagementAppView extends MainView {

    /**
     * Reference to the AdminView for administrative functionalities.
     */
    private AdminView adminView = new AdminView();

    /**
     * Reference to the LoginView for handling user authentication.
     */
    private LoginView loginView = new LoginView();

    /**
     * Displays the main menu actions available in the Hospital Management App View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     *   <li>(1) Continue as Staff.</li>
     *   <li>(2) Continue as Patient.</li>
     *   <li>(3) Quit the application.</li>
     * </ul>
     */
    @Override
    protected void printActions() {
        printBreadCrumbs("Hospital Management App View");
        System.out.println("Who would you like to continue as?");
        System.out.println("(1) Staff");
        System.out.println("(2) Patient");
        System.out.println("(3) Quit Hospital Management App");
    }

    /**
     * Controls the main workflow of the Hospital Management App.
     * <p>
     * Allows the user to choose their role (Staff or Patient) or quit the application.
     * Redirects to the appropriate view based on the user's selection.
     * </p>
     */
    @Override
    public void viewApp() {
        int choice;
        do {
            printActions();
            choice = Helper.readInt();
            switch (choice) {
                case 1:
                    loginView.viewApp(); // Log in as staff
                    break;
                case 2:
                    loginView.viewApp(false); // Log in as patient
                    break;
                case 3:
                    System.out.println("Thank you for using Hospital Management App!");
                    return; // Exit the method and terminate the application
                default:
                    System.out.println("Invalid input! Please try again.");
                    break;
            }
        } while (choice != 3);
    }
}
