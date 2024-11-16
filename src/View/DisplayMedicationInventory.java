package src.View;

import src.Controller.AdminController;
import src.Helper.Helper;

public class DisplayMedicationInventory extends MainView{

    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Medical Inventory View");
        System.out.println("(1) View Medical Inventory");
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
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Medical Inventory View");
                    String medicalInventory = AdminController.getInventoryRecord();  // Get details from controller
                    System.out.println(medicalInventory);
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
}

