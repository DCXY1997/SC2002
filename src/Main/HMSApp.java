package src.Main;

import src.Helper.Helper;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.HospitalManagementAppView;

/**
 * The starting point of the application.
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class HMSApp {
<<<<<<< HEAD
        /**
     * Main function that is the starting point of the application.
     * @param args Arguments passed to the app
     */
=======

>>>>>>> 9f13667fef1bff181ceb9bdfee5c7161c5340f34
    public static void main(String[] args) {
        //Repository.clearDatabase();

        // Read existing data first
        Repository.readData(FileType.STAFF);
        Repository.readData(FileType.PATIENT);
        Repository.readData(FileType.INVENTORY);
        Repository.readData(FileType.REPLENISHMENT_REQUEST);
        Repository.readData(FileType.APPOINTMENT_OUTCOME);
        Repository.readData(FileType.APPOINTMENT_LIST);
        Repository.readData(FileType.MEDICINE);
        Repository.readData(FileType.MEDICAL_RECORD);
        Repository.readData(FileType.DIAGNOSIS);
        Repository.readData(FileType.TREATMENT);

        // Initialize dummy data if needed
        Repository.initializeDummyPatient();
        Repository.initializeDummyStaff();
        Repository.initializeDummyInventory();
        Repository.initializeDummyReplenishmentRequest();
        //Repository.initializeDummyAppointmentOutcome();

        // Save all data after initialization
        Repository.saveAllFiles(); // Add this line

        Helper.clearScreen();
        printHMSTitle();
        HospitalManagementAppView hospitalManagementAppView = new HospitalManagementAppView();
        hospitalManagementAppView.viewApp();
        Repository.saveAllFiles(); // Add this line
    }

    /**
     * Prints the HMS title.
     */

    private static void printHMSTitle() {
        System.out.println();
        System.out
                .println("╔═════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out
                .println("║                           **  **       **      **     ______                                ║");
        System.out
                .println("║                          /  |/  |   /  \\    /  |   /      \\                                 ║");
        System.out
                .println("║                          ▐▐ |▐▐ |   ▐▐  \\  ▐▐ |  /▐▐▐▐▐▐▐ |                                 ║");
        System.out
                .println("║                          ▐▐ |▐▐ |   ▐▐▐▐\\▐▐▐▐ |  ▐▐ \\__▐▐/                                  ║");
        System.out
                .println("║                          ▐▐▐▐▐▐ |   ▐▐ |▐▐/ ▐▐ |  ▐▐                                        ║");
        System.out
                .println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |   ▐▐▐▐▐▐▐\\                                 ║");
        System.out
                .println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |        ▐▐|                                 ║");
        System.out
                .println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |  ▐▐    ▐▐ |                                ║");
        System.out
                .println("║                          ▐▐/ ▐▐/    ▐▐/     ▐▐/    ▐▐▐▐▐▐/                                  ║");
        System.out
                .println("║                                                                                             ║");
        System.out
                .println("║                           Welcome to Hospital Management System                             ║");
        System.out
                .println("║                                                                                             ║");
        System.out
                .println("╚═════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

}
