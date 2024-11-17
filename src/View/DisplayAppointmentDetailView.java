package src.View;

import java.util.List;
import src.Controller.AdminController;
import src.Controller.AppointmentController;
import src.Helper.Helper;

/**
 * The DisplayAppointmentDetailView class provides an interface for administrators 
 * to view details of specific appointments in the hospital management system.
 * <p>
 * This class allows administrators to:
 * </p>
 * <ul>
 *   <li>View a list of available appointment IDs.</li>
 *   <li>Enter an appointment ID to view the corresponding details.</li>
 *   <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Integrates with {@link AppointmentController} to fetch appointment details.</li>
 *   <li>Provides a simple interface to display available appointment IDs.</li>
 *   <li>Displays detailed information about a specific appointment.</li>
 * </ul>
 *
 * @see AppointmentController
 * @see AdminController
 * @see Helper
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class DisplayAppointmentDetailView extends MainView {

    /**
     * Prints the actions available in the Appointment Details View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     *   <li>(1) View appointment details by appointment ID.</li>
     *   <li>(2) Navigate back to the previous menu.</li>
     * </ul>
     */
    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Appointment Details View");
        System.out.println("(1) View Appointment Details by Appointment Id");
        System.out.println("(2) Back");
    }

    /**
     * Controls the workflow of the Appointment Details View.
     * <p>
     * Allows the administrator to choose from the available options and interact with the appointment details interface.
     * </p>
     */
    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 2);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Appointment Details View");
                    promptAppointmentId();
                    Helper.pressAnyKeyToContinue();
                    break;
                case 2:
                    Helper.pressAnyKeyToContinue();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (opt != 2);
    }

    /**
     * Prompts the administrator to enter an appointment ID and displays the corresponding appointment details.
     * <p>
     * This method first fetches and displays a list of available appointment IDs using {@link AppointmentController#getAllAppointmentIds()}.
     * It then prompts the user to enter an appointment ID and displays the details of the selected appointment.
     * If no appointments are available, it displays a message and exits the method.
     * </p>
     */
    private void promptAppointmentId() {
        // Load and display available appointment IDs
        List<String> appointmentIds = AppointmentController.getAllAppointmentIds();
        if (appointmentIds.isEmpty()) {
            System.out.println("No appointments available.");
            return;  // Exit the method if no appointments are available
        }

        System.out.println("Available Appointment IDs:");
        for (String id : appointmentIds) {
            System.out.println("  - " + id);
        }

        // Prompt the user to enter an appointment ID
        System.out.println("Enter the Appointment Id: ");
        String appointmentId = Helper.readString();

        // Fetch and display the appointment details
        String appointmentDetails = AppointmentController.getAppointmentDetails(appointmentId);  // Get details from controller
        System.out.println(appointmentDetails);  // Display the details
    }
}
