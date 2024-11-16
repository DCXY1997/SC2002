package src.View;

import java.util.List;
import src.Controller.AdminController;
import src.Helper.Helper;

public class DisplayAppointmentDetailView extends MainView {

    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Appointment Details View");
        System.out.println("(1) View Appointment Details by Appointment Id");
        System.out.println("(2) Back");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 2);
            switch(opt){
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
        }while(opt != 2);
}

private void promptAppointmentId() {
    // Load and display available appointment IDs
    List<String> appointmentIds = AdminController.getAllAppointmentIds();
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
    String appointmentDetails = AdminController.getAppointmentDetails(appointmentId);  // Get details from controller
    System.out.println(appointmentDetails);  // Display the details
}


}