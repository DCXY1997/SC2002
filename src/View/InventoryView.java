package src.View;

import java.util.Scanner;
import src.Helper.Helper;
import src.Controller.*;

public class InventoryView extends MainView{
	
	protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Medication Inventory View");
        System.out.println("(1) View All Medication Inventory");
        System.out.println("(2) Back");
    }
	
	public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 2);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > View All Medication Inventory");
                    String inventory = InventoryController.checkAllInventory(1);
                    
                    if (inventory.contains("No inventory found")) {
                        System.out.println("No medication inventory available.");
                    } else {
                        System.out.println(inventory);   
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

	public void displayReplenishmentRequest()
	{
		Scanner sc = new Scanner(System.in);
		int opt = -1;
        Helper.clearScreen();
		do
		{
            Helper.clearScreen();
			printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Submit Replenishment Request");
			System.out.println("(1) Submit Replenishment Request");
	        System.out.println("(2) Back");
			opt = Helper.readInt(1, 2);
			switch (opt)
			{
				case 1: 
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Submit Replenishment Request");
					String inventory = InventoryController.checkAllInventory(0);
					if (inventory.contains("No inventory found")) {
                        System.out.println("No low stock medication inventory available.");
                        break;
                    } else {
                        System.out.println(inventory);   
                    }
					System.out.println("Enter the medicine ID to replenish: ");
					String medicineId = Helper.readString();
					//Helper.clearScreen();
			        PharmacistController.submitReplenishmentRequest(medicineId);
					break;
				case 2:
					Helper.pressAnyKeyToContinue();
					break;
				default:
					System.out.println("Invalid input!\n");
					
			}	
		}while(opt != 2);
	}
}
