package src.View;

import src.Controller.InventoryController;
import src.Helper.Helper;
import src.Model.InventoryList;
import src.Model.ReplenishmentRequest;

/**
 * The ManageReplenishmentRequestView class provides an interface for administrators 
 * to manage replenishment requests in the hospital management system.
 * <p>
 * This class allows administrators to:
 * </p>
 * <ul>
 *   <li>View all replenishment requests (pending, approved, rejected).</li>
 *   <li>Approve or reject pending replenishment requests.</li>
 *   <li>View the details of inventory items related to requests.</li>
 *   <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Integrates with {@link InventoryController} to handle replenishment request operations.</li>
 *   <li>Provides detailed prompts to guide users through managing requests.</li>
 * </ul>
 *
 * @see InventoryController
 * @see Helper
 * @see InventoryList
 * @see ReplenishmentRequest
 * @see MainView
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class ManageReplenishmentRequestView extends MainView {

    /**
     * Displays the actions available in the Replenishment Request View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     *   <li>(1) View replenishment requests.</li>
     *   <li>(2) Back to the previous menu.</li>
     * </ul>
     */
    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Replenishment Request View");
        System.out.println("(1) View Replenishment Requests");
        System.out.println("(2) Back");
    }

    /**
     * Controls the workflow of the Replenishment Request View.
     * <p>
     * Allows the administrator to view and manage replenishment requests 
     * or navigate back to the previous menu.
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
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Replenishment Request View");

                    // Display all requests
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

    /**
     * Manages a specific replenishment request based on the provided Request ID.
     * <p>
     * The administrator can:
     * </p>
     * <ul>
     *   <li>Approve the request, which updates the stock level of the related inventory item.</li>
     *   <li>Reject the request.</li>
     * </ul>
     * If the request or the related inventory item is not found, an appropriate message is displayed.
     *
     * @param requestId The unique ID of the replenishment request to manage.
     */
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
            }
        } else if (choice == 2) {
            InventoryController.rejectReplenishmentRequest(requestId);
            System.out.println("Request rejected successfully.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
