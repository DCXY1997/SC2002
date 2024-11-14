package src.View;

import src.Controller.InventoryController;
import src.Helper.Helper;
import src.Model.InventoryList;
import src.Model.ReplenishmentRequest;

public class ManageReplenishmentRequestView extends MainView {

    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Replenishment Request View");
        System.out.println("(1) View Replenishment Requests");
        System.out.println("(2) Back");
    }

    @Override
public void viewApp() {
    int opt = -1;
    do {
        printActions();
        opt = Helper.readInt(1, 2);
        switch (opt) {
            case 1:
                Helper.clearScreen();
                printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Replenishment Request View");

                // Display all requests as returned by findAllReplenishmentRequests()
                String allRequests = InventoryController.findAllReplenishmentRequests();
                System.out.println(allRequests);

                System.out.println("\n(1) Manage a pending request");
                System.out.println("(2) Back");

                int choice = Helper.readInt(1, 2);
                if (choice == 1) {
                    System.out.println("Enter the Request ID to manage:");
                    String requestId = Helper.readString();
                    manageReplenishmentRequest(requestId);
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

    public void manageReplenishmentRequest(String requestId) {
        ReplenishmentRequest request = InventoryController.getReplenishmentRequestById(requestId);
        if (request == null) {
            System.out.println("Request ID not found.");
            return;
        }

        String medicineId = request.getMedicineId();
        InventoryList inventoryItem = InventoryController.searchMedicalInventoryById(medicineId);
        
        if (inventoryItem == null) {
            System.out.println("Medicine ID not found in inventory.");
            return;
        }

        // Display current stock and low stock level alert for the medicine
        System.out.println("Current Medicine Details:");
        System.out.println("Medicine ID: " + medicineId);
        System.out.println("Current Stock Level: " + inventoryItem.getInitialStock());
        System.out.println("Low Stock Level Alert: " + inventoryItem.getLowStocklevelAlert());
        System.out.println("\nChoose an action for the replenishment request:");
        System.out.println("(1) Approve");
        System.out.println("(2) Reject");

        int choice = Helper.readInt(1, 2);

        if (choice == 1) {
            boolean success = InventoryController.approveReplenishmentRequest(requestId);
            if (success) {
                int newStockLevel = inventoryItem.getInitialStock(); // Get updated stock level after approval
                System.out.println("Request approved successfully.");
                System.out.println("New Stock Level: " + newStockLevel);
            } else {
                System.out.println("Approval not needed: stock is above alert threshold.");
            }
        } else if (choice == 2) {
            InventoryController.rejectReplenishmentRequest(requestId);
            System.out.println("Request rejected successfully.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
}


