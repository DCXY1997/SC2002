
package src.Controller;

import java.util.Map;
import src.Enum.InventoryRequestStatus;
import src.Helper.Helper;
import src.Model.Admin;
import src.Model.InventoryList;
import src.Model.Medicine;
import src.Model.ReplenishmentRequest;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.AdminView;
import src.View.InventoryView;

/** InventoryController is a controller class that acts as a "middleman"
 * between the view classes - {@link AdminView},  {@link InventoryView}, {@link DisplayMedicalInventoryView}
 * {@link ManageMedicalInventoryView} and {@link ManagReplenishmentRequestView} and the model class - {@link Admin}, {@link InventoryList} and {@link Medicine}. <p>
 * 
 * It can view/add/update/remove medical inventory items and get/approve/reject replenishment requsts
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class InventoryController {

    /**
    * Adds a new medicine to the medical inventory and persists the data to the repository.
    * Creates a new InventoryList item for the provided medicine details and updates the inventory.
    *
    * @param medicineId          The unique identifier for the medicine.
    * @param medicineName        The name of the medicine.
    * @param medicinePrice       The price of the medicine.
    * @param medicineDescription A brief description of the medicine.
    * @param medicineStock       The current stock level of the medicine.
    * @param medicineLowStock    The low stock level alert threshold for the medi
    * cine.
    */
    public static void addMedicalInventory(String medicineId, String medicineName, float medicinePrice, String medicineDescription, int medicineStock, int medicineLowStock) {
        Medicine medicine = new Medicine(medicineId, medicineName, medicinePrice, medicineDescription);
		InventoryList inventoryList= new InventoryList(medicine, medicineStock, medicineLowStock);
        
        //Update Hash Map
        Repository.INVENTORY.put(medicine.getMedicineId(), inventoryList);
    
        // Persist data to file
        Repository.persistData(FileType.INVENTORY);

        System.out.println("Medical Inventory Item added successfully! ID: " + medicineId);
	}

    /**
    * Removes a medical inventory item by its medicine ID.
     * Prompts the user for confirmation before removal and persists the changes to the repository.
    *
    * @param medicineId The unique identifier of the medicine to be removed.
    * @return {@code true} if the item was successfully removed, {@code false} otherwise.
    */

    public static boolean removeMedicalInventoryItem(String medicineId) { 
        if (Repository.INVENTORY.containsKey(medicineId)) {
            // Prompt confirmation before removal
            if (Helper.promptConfirmation("remove this medical inventory item?")) {
                Repository.INVENTORY.remove(medicineId);
                Repository.persistData(FileType.INVENTORY); // Persist changes
                System.out.println("Medical Inventory Item removed successfully.");
                return true;
            } else {
                return false; // Don't print the cancellation message here
            }
        } else {
            System.out.println("Medical Inventory Item not found!");
            return false;
        }
    }
    
    /**
     * Searches for a medical inventory item by its medicine ID.
    *
    * @param medicineId The unique identifier of the medicine to search for.
    * @return The {@link InventoryList} item associated with the provided medicine ID, or {@code null} if not found.
    */

    public static InventoryList searchMedicalInventoryById(String medicineId) {
        // Check if the INVENTORY map contains the specified medicineId
        if (Repository.INVENTORY.containsKey(medicineId)) {
            // Return the InventoryList item if it exists
            return Repository.INVENTORY.get(medicineId);
        } else {
            // Return null or handle accordingly if the item is not found
            System.out.println("No inventory record found for Medicine ID: " + medicineId);
            return null;
        }
    }
    
    /**
    * Updates the stock level of a specific medical inventory item.
    * Persists the updated inventory to the repository.
    *
    * @param medicineId The unique identifier of the medicine to update.
    * @param stockLevel The new stock level for the medicine.
    * @return {@code true} if the stock level was successfully updated, {@code false} if the medicine ID was not found.
    */
   
    public static boolean updateMedicalInventoryStockLevel(String medicineId, int stockLevel) {
        // Check if the INVENTORY map contains the specified medicineId
        if (Repository.INVENTORY.containsKey(medicineId)) {
            // Get the InventoryList item associated with the medicineId
            InventoryList inventoryItem = Repository.INVENTORY.get(medicineId);
            
            // Update the initial stock level
            inventoryItem.setInitialStock(stockLevel);
            
            // Persist the updated inventory if necessary
            Repository.INVENTORY.put(medicineId, inventoryItem);  // Explicitly put the updated item back into INVENTORY
    
            // Save the changes to the data file if required
            Repository.persistData(FileType.INVENTORY);  // Assuming persistData handles saving INVENTORY data
            
            return true;  // Indicate success
        } else {
            return false;  // Indicate failure if medicineId not found
        }
    }

    /**
    * Updates the low stock level alert for a specific medical inventory item.
    * Persists the changes to the repository.
    *
    * @param medicineId         The unique identifier of the medicine to update.
    * @param lowStockLevelAlert The new low stock alert level for the medicine.
    * @return {@code true} if the alert level was successfully updated, {@code false} if the medicine ID was not found.
    */

    public static boolean updateMedicalInventoryLowStockAlert(String medicineId, int lowStockLevelAlert) {
        // Check if the inventory contains the specified medicine ID
        if (Repository.INVENTORY.containsKey(medicineId)) {
            // Retrieve the inventory item associated with the medicine ID
            InventoryList inventoryItem = Repository.INVENTORY.get(medicineId);
            
            // Update the low stock level alert value
            inventoryItem.setLowStocklevelAlert(lowStockLevelAlert);
            
            // Persist the changes to the data file
            Repository.persistData(FileType.INVENTORY);
            
            return true;  // Indicate success
        } else {
            return false;  // Medicine ID not found
        }
    }
    
    /**
     * Retrieves all replenishment requests grouped by their status (Pending, Approved, Rejected).
    *
    * @return A formatted string containing all replenishment requests.
    */

    public static String findAllReplenishmentRequests() {
        StringBuilder requests = new StringBuilder();
    
        requests.append("Replenishment Requests:\n");
        requests.append("------------------------------------------------------------\n");
    
        boolean hasPendingRequests = false;
        boolean hasApprovedRequests = false;
        boolean hasRejectedRequests = false;
    
        // Pending Requests Section
        requests.append("Pending Replenishment Requests:\n");
        requests.append("------------------------------------------------------------\n");
        for (Map.Entry<String, ReplenishmentRequest> entry : Repository.REPLENISHMENT_REQUEST.entrySet()) {
            ReplenishmentRequest request = entry.getValue();
    
            if (request.getStatus() == InventoryRequestStatus.PENDING) {
                hasPendingRequests = true;
                requests.append("Request ID: ").append(request.getRequestId()).append("\n");
                requests.append("Medicine ID: ").append(request.getMedicineId()).append("\n");
                requests.append("Requested Stock Level: ").append(request.getStockLevel()).append("\n");
                requests.append("Status: ").append(request.getStatus()).append("\n");
                requests.append("------------------------------------------------------------\n");
            }
        }
        if (!hasPendingRequests) {
            requests.append("No pending replenishment requests found.\n");
            requests.append("------------------------------------------------------------\n");
        }
    
        // Approved Requests Section
        requests.append("\nApproved Replenishment Requests:\n");
        requests.append("------------------------------------------------------------\n");
        for (Map.Entry<String, ReplenishmentRequest> entry : Repository.REPLENISHMENT_REQUEST.entrySet()) {
            ReplenishmentRequest request = entry.getValue();
    
            if (request.getStatus() == InventoryRequestStatus.APPROVED) {
                hasApprovedRequests = true;
                requests.append("Request ID: ").append(request.getRequestId()).append("\n");
                requests.append("Medicine ID: ").append(request.getMedicineId()).append("\n");
                requests.append("Requested Stock Level: ").append(request.getStockLevel()).append("\n");
                requests.append("Status: ").append(request.getStatus()).append("\n");
                requests.append("------------------------------------------------------------\n");
            }
        }
        if (!hasApprovedRequests) {
            requests.append("No approved replenishment requests found.\n");
            requests.append("------------------------------------------------------------\n");
        }
    
        // Rejected Requests Section
        requests.append("\nRejected Replenishment Requests:\n");
        requests.append("------------------------------------------------------------\n");
        for (Map.Entry<String, ReplenishmentRequest> entry : Repository.REPLENISHMENT_REQUEST.entrySet()) {
            ReplenishmentRequest request = entry.getValue();
    
            if (request.getStatus() == InventoryRequestStatus.REJECTED) {
                hasRejectedRequests = true;
                requests.append("Request ID: ").append(request.getRequestId()).append("\n");
                requests.append("Medicine ID: ").append(request.getMedicineId()).append("\n");
                requests.append("Requested Stock Level: ").append(request.getStockLevel()).append("\n");
                requests.append("Status: ").append(request.getStatus()).append("\n");
                requests.append("------------------------------------------------------------\n");
            }
        }
        if (!hasRejectedRequests) {
            requests.append("No rejected replenishment requests found.\n");
            requests.append("------------------------------------------------------------\n");
        }
    
        return requests.toString();
    }
    
    /**
     * Approves a replenishment request and updates the stock level of the associated inventory item.
     * This method will deny approval if the request has already been approved or rejected.
     *
     * @param requestId The unique identifier of the replenishment request to approve.
     * @return {@code true} if the request was successfully approved, {@code false} otherwise.
     */
    public static boolean approveReplenishmentRequest(String requestId) {
        ReplenishmentRequest request = Repository.REPLENISHMENT_REQUEST.get(requestId);
        if (request == null) {
            System.out.println("Request ID not found.");
            return false;
        }
    
        // Deny management if the request is already approved or rejected
        if (request.getStatus() != InventoryRequestStatus.PENDING) {
            System.out.println("This request has already been " + request.getStatus().toString().toLowerCase() + " and cannot be managed.");
            return false;
        }
    
        String medicineId = request.getMedicineId();
        InventoryList inventoryItem = Repository.INVENTORY.get(medicineId);
    
        if (inventoryItem != null) {
            int updatedStock = inventoryItem.getInitialStock() + request.getStockLevel();
            inventoryItem.setInitialStock(updatedStock);
    
            Repository.INVENTORY.put(medicineId, inventoryItem);
            request.setStatus(InventoryRequestStatus.APPROVED);
            Repository.REPLENISHMENT_REQUEST.put(requestId, request);
    
            Repository.persistData(FileType.INVENTORY);
            Repository.persistData(FileType.REPLENISHMENT_REQUEST);
    
            return true;
        } else {
            System.out.println("Medicine ID not found in inventory.");
            return false;
        }
    }
    
    /**
     * Rejects a replenishment request by updating its status to REJECTED.
     * This method will deny rejection if the request has already been approved or rejected.
     *
     * @param requestId The unique identifier of the replenishment request to reject.
     */
    public static void rejectReplenishmentRequest(String requestId) {
        ReplenishmentRequest request = Repository.REPLENISHMENT_REQUEST.get(requestId);
        if (request != null) {
            // Deny management if the request is already approved or rejected
            if (request.getStatus() != InventoryRequestStatus.PENDING) {
                System.out.println("This request has already been " + request.getStatus().toString().toLowerCase() + " and cannot be managed.");
                return;
            }
    
            request.setStatus(InventoryRequestStatus.REJECTED);
            Repository.REPLENISHMENT_REQUEST.put(requestId, request);
            Repository.persistData(FileType.REPLENISHMENT_REQUEST);
        } else {
            System.out.println("Request ID not found.");
        }
    }

    /**
    * Retrieves a specific replenishment request by its request ID.
    *
     * @param requestId The unique identifier of the replenishment request to retrieve.
    * @return The {@link ReplenishmentRequest} associated with the provided request ID, or {@code null} if not found.
    */

    public static ReplenishmentRequest getReplenishmentRequestById(String requestId) {
        return Repository.REPLENISHMENT_REQUEST.get(requestId);
    }

    /**
    * Checks all inventory items and retrieves details of items.
    * Includes an option to filter items based on their stock status.
    *
    * @param all {@code 1} to list all items, {@code 0} to list only items with low stock levels.
    * @return A formatted string containing the inventory details.
    */

    public static String checkAllInventory(int all) {
        StringBuilder allInventory = new StringBuilder();

        // Check if the inventory is empty
        if (Repository.INVENTORY.isEmpty()) {
            return "No inventory found.";
        }

        allInventory.append("\nMedicine Inventory:\n");
        allInventory.append("------------------------------------------------------------\n");

        // Iterate through all inventory items in the repository
        for (Map.Entry<String, InventoryList> entry : Repository.INVENTORY.entrySet()) {
            InventoryList inventory = entry.getValue();
            if (all==1 || (all==0)&&(inventory.getInitialStock() < inventory.getLowStocklevelAlert()))
            {
            	allInventory.append("Medicine ID: ").append(inventory.getMedicine().getMedicineId()).append("\n");
                allInventory.append("Medicine Name: ").append(inventory.getMedicine().getMedicineName()).append("\n");
                allInventory.append("Stock Level: ").append(inventory.getInitialStock()).append("\n");
                allInventory.append("Alert Threshold: ").append(inventory.getLowStocklevelAlert()).append("\n");
                if (inventory.getInitialStock() < inventory.getLowStocklevelAlert())
                	allInventory.append("[STOCK STATUS: ").append("LOW]").append("\n");
                allInventory.append("------------------------------------------------------------\n");
            }  
        }

        return allInventory.toString();
    }

    /**
    * Retrieves a detailed record of all medical inventory items.
    *
    * @return A formatted string containing the details of all inventory items, or "No inventory records found" if empty.
    */

    public static String getInventoryRecord() {
        StringBuilder inventoryDetails = new StringBuilder();
        
        if (Repository.INVENTORY.isEmpty()) {
            return "No inventory records found.";
        }
        
        inventoryDetails.append("Medical Inventory:\n");
        inventoryDetails.append("------------------------------------------------------------\n");
        
        for (Map.Entry<String, InventoryList> entry : Repository.INVENTORY.entrySet()) {
            InventoryList inventoryItem = entry.getValue();
            Medicine medicine = inventoryItem.getMedicine();
               
            inventoryDetails.append("Medicine ID: ").append(medicine.getMedicineId()).append("\n");
            inventoryDetails.append("Medicine Name: ").append(medicine.getMedicineName()).append("\n");
            inventoryDetails.append("Medicine Price: $").append(medicine.getMedicinePrice()).append("\n");
            inventoryDetails.append("Medicine Description: ").append(medicine.getMedicineDescription()).append("\n");
            inventoryDetails.append("Initial Stock: ").append(inventoryItem.getInitialStock()).append("\n");
            inventoryDetails.append("Low Stock Level Alert: ").append(inventoryItem.getLowStocklevelAlert()).append("\n");
            inventoryDetails.append("------------------------------------------------------------\n");
        }
        
        return inventoryDetails.toString();
    }

}
