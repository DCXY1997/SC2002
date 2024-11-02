package src.Main;
import src.View. *;
import src.Helper.Helper;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.HospitalManagementAppView;

public class HMSApp {
  public static void main(String[] args) {
    //Repository.clearDatabase();
    
    // Read existing data first
    Repository.readData(FileType.STAFF);
    Repository.readData(FileType.INVENTORY);
    Repository.readData(FileType.REPLENISHMENT_REQUEST);
    
    // Initialize dummy data if needed
    Repository.initializeDummyStaff();
    Repository.initializeDummyInventory();
    Repository.initializeDummyReplenishmentRequest();
    
    // Save all data after initialization
    Repository.saveAllFiles();  // Add this line
    
    Helper.clearScreen();
    printHMSTitle();
    HospitalManagementAppView hospitalManagementAppView = new HospitalManagementAppView();
    hospitalManagementAppView.viewApp();
  }

    private static void printHMSTitle() {
        System.out.println();
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           **  **       **      **     ______                                ║");
        System.out.println("║                          /  |/  |   /  \\    /  |   /      \\                               ║");
        System.out.println("║                          ▐▐ |▐▐ |   ▐▐  \\  ▐▐ |  /▐▐▐▐▐▐▐ |                                ║");
        System.out.println("║                          ▐▐ |▐▐ |   ▐▐▐▐\\▐▐▐▐ |  ▐▐ \\__▐▐/                                ║");
        System.out.println("║                          ▐▐▐▐▐▐ |   ▐▐ |▐▐/ ▐▐ |  ▐▐                                        ║");
        System.out.println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |   ▐▐▐▐▐▐▐\\                                ║");
        System.out.println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |        ▐▐|                                 ║");
        System.out.println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |  ▐▐    ▐▐ |                                ║");
        System.out.println("║                          ▐▐/ ▐▐/    ▐▐/     ▐▐/    ▐▐▐▐▐▐/                                  ║");
        System.out.println("║                                                                                             ║");
        System.out.println("║                           Welcome to Hospital Management System                             ║");
        System.out.println("║                                                                                             ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

}
