package src.View;

import java.util.Map;

import src.Enum.InventoryRequestStatus;
import src.Helper.Helper;
import src.Model.*;
import src.Repository.Repository;
import src.Controller.*;

public class InventoryView extends MainView{
	
	protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Inventory View");
        System.out.println("(1) View All Inventory");
        System.out.println("(2) Back");
    }
	
	public void viewApp() {
        int opt = -1;
        int valid = 0;
        do {
            printActions();
            opt = Helper.readInt(1, 2);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Display All Inventory");
                    String inventory = InventoryController.displayAllInventory();
                    
                    if (inventory.contains("No inventory found")) {
                        System.out.println("No inventory available.");
                    } else {
                        System.out.println(inventory);
                        do
                        {
                        	System.out.println("\n(1) Submit Replenishment Request");
                            System.out.println("(2) Back");

                            int choice = Helper.readInt(1, 2);
                            if (choice == 1) {
                            	PharmacistController pharmacistController = new PharmacistController();
                            	System.out.println("Enter the Medicine ID to replenish:");
                                String medicineId = Helper.readString();
                                valid = pharmacistController.submitReplenishmentRequest(medicineId);
                            }
                        }while (valid==0);
                    }
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

	
}
