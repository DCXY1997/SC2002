package src.Main;

import src.Helper.Helper;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.HospitalManagementAppView;

public class HMSApp {
  public static void main(String[] args) {
    // Repository.clearDatabase();
    Repository.readData(FileType.STAFF);
    Repository.readData(FileType.PATIENT);
    Repository.readData(FileType.MEDICINE);
    Repository.initializeDummyStaff();
    Repository.initializeDummyPatient();
    Helper.clearScreen();
    printHMSTitle();
    HospitalManagementAppView hospitalManagementAppView = new HospitalManagementAppView();
    hospitalManagementAppView.viewApp();
  }

  private static void printHMSTitle() {
    System.out.println();
    System.out
        .println("╔═════════════════════════════════════════════════════════════════════════════════════════════╗");
    System.out
        .println("║                           **  **       **      **     ______                                ║");
    System.out
        .println("║                          /  |/  |   /  \\    /  |   /      \\                               ║");
    System.out
        .println("║                          ▐▐ |▐▐ |   ▐▐  \\  ▐▐ |  /▐▐▐▐▐▐▐ |                                ║");
    System.out
        .println("║                          ▐▐ |▐▐ |   ▐▐▐▐\\▐▐▐▐ |  ▐▐ \\__▐▐/                                ║");
    System.out
        .println("║                          ▐▐▐▐▐▐ |   ▐▐ |▐▐/ ▐▐ |  ▐▐                                        ║");
    System.out
        .println("║                          ▐▐ |▐▐ |   ▐▐ |    ▐▐ |   ▐▐▐▐▐▐▐\\                                ║");
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
