package src.View;

import src.Controller.InventoryController;
import src.Enum.MedicineStatus;
import src.Helper.Helper;
import src.Repository.Repository;

public class ManageMedicalInventory extends MainView {
    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Staff Account View");
        System.out.println("What would you like to do ?");
        System.out.println("(1) Add Medical Inventory Item");
        System.out.println("(2) Remove Medical Inventory Item");
        System.out.println("(3) Update Medical Inventory Item");
        System.out.println("(4) Exit");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 4);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Login View > Admin View > Manage Medical Inventory View > Add Medical InventoryView");
                    promptAddMedicalInventory();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Login View > Admin View > Manage Medical Inventory View > Remove Medical Inventory View");
                    promptRemoveMedicalInventory();
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs(
                            "Hospital Management App View > Login View > Admin View > Manage Medical Inventory View > Update Medical Inventory View");
                    promptUpdateMedicalInventory();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 4) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 4);
    }

    private boolean promptAddMedicalInventory() {
        System.out.println("Enter Medicine Name:");
        String name = Helper.readString();

        System.out.println("Enter Medicine Price:");
        float price = (float) Helper.readFloat();

        System.out.println("Enter Medicine Description:");
        String description = Helper.readString();

        // --------------------------------------------------
        // Medicine Status CHG HERE
        // Prompt the user to enter a valid medicine status
        System.out.println("Enter Medicine Status (PENDING/DISPENSED):");

        // Read the user input
        String statusInput = Helper.readString().toUpperCase().trim(); // Convert to uppercase and trim whitespace

        // Use a switch statement to handle the two valid status options
        MedicineStatus status;
        switch (statusInput) {
            case "PENDING":
                status = MedicineStatus.PENDING;
                // Perform actions for PENDING status if needed
                break;
            case "DISPENSED":
                status = MedicineStatus.DISPENSED;
                // Perform actions for DISPENSED status if needed
                break;
            default:
                // Handle invalid input
                System.out.println("Invalid medicine status entered. Please try again.");
                // Optionally, you could loop or prompt for the status again
                status = null; // Set to null or handle appropriately
                break;
        }

        // Use the status variable if it is not null
        if (status != null) {
            // Continue with your code using the valid status
        }
        // --------------------------------------------------

        System.out.println("Enter Medicine Initial Stock Count:");
        int stock = Helper.readInt();

        System.out.println("Enter Medicine Low Stock Level Alert Count:");
        int stockLow = Helper.readInt();

        String medicineId = generateMedicalInventoryId(name);

        // ADD STATUS
        InventoryController.addMedicalInventory(medicineId, name, status, price, description, stock, stockLow);
        return true;
    }

    private String generateMedicalInventoryId(String name) {
        String prefix = "";
        int uniqueId = Helper.generateUniqueId(Repository.INVENTORY);
        return prefix + String.format("%03d", uniqueId);
    }

    private boolean promptRemoveMedicalInventory() {
        System.out.println("Enter the medical inventory item id you want to remove: ");
        String medicineId = Helper.readString();

        if (Repository.INVENTORY.containsKey(medicineId)) {
            if (!InventoryController.removeMedicalInventoryItem(medicineId)) {
                System.out.println("Medical Inventory Removal canceled!");
                return false;
            }
        } else {
            System.out.println("Medical Inventory Item Not Found!");
            return false;
        }
        return true;
    }

    private boolean promptUpdateMedicalInventory() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management View > Admin View > Update a Medical Inventory Item");

        System.out.println("Enter the medical inventory ID that you want to update: ");
        String medicineId = Helper.readString();

        // Check if the medicineId exists in INVENTORY via the controller
        if (InventoryController.searchMedicalInventoryById(medicineId) == null) {
            System.out.println("Medical inventory item not found!");
            return false;
        }

        System.out.println("What would you like to update?");
        System.out.println("(1) Update Stock Count");
        System.out.println("(2) Update Low Stock Level Alert");
        int choice = Helper.readInt(1, 2);

        boolean success = false;

        if (choice == 1) {
            System.out.println("Please enter the new stock level: ");
            int newStockLevel = Helper.readInt();
            success = InventoryController.updateMedicalInventoryStockLevel(medicineId, newStockLevel);

            // Print success or failure message
            if (success) {
                System.out.println("Stock level updated successfully for Medicine ID: " + medicineId);
            } else {
                System.out.println("Failed to update stock level for Medicine ID: " + medicineId);
            }

        } else if (choice == 2) {
            System.out.println("Please enter the new low stock level alert: ");
            int newLowStockLevelAlert = Helper.readInt();
            success = InventoryController.updateMedicalInventoryLowStockAlert(medicineId, newLowStockLevelAlert);

            // Print success or failure message
            if (success) {
                System.out.println("Low stock level alert updated successfully for Medicine ID: " + medicineId);
            } else {
                System.out.println("Failed to update low stock level alert for Medicine ID: " + medicineId);
            }
        }

        return success;
    }

}
