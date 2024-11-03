package src.Controller;

import java.util.ArrayList;
import java.util.Map;

import src.Enum.Gender;
import src.Enum.InventoryRequestStatus;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Model.InventoryList;
import src.Model.Medicine;
import src.Model.ReplenishmentRequest;
import src.Model.Staff;
import src.Repository.FileType;
import src.Repository.Repository;

public class InventoryController {

    public static void addMedicalInventory(String medicineId, String medicineName, float medicinePrice, String medicineDescription, int medicineStock, int medicineLowStock) {
        Medicine medicine = new Medicine(medicineId, medicineName, medicinePrice, medicineDescription);
		InventoryList inventoryList= new InventoryList(medicine, medicineStock, medicineLowStock);
        
        //Update Hash Map
        Repository.INVENTORY.put(medicine.getMedicineId(), inventoryList);
    
        // Persist data to file
        Repository.persistData(FileType.INVENTORY);

        System.out.println("Medical Inventory Item added successfully! ID: " + medicineId);
	}

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
    
    public static String findPendingReplenishmentRequests() {
        StringBuilder pendingRequests = new StringBuilder();
    
        if (Repository.REPLENISHMENT_REQUEST.isEmpty()) {
            return "No replenishment requests found.";
        }
    
        pendingRequests.append("Pending Replenishment Requests:\n");
        pendingRequests.append("------------------------------------------------------------\n");
    
        boolean hasPendingRequests = false;
    
        // Loop through all requests in the repository
        for (Map.Entry<String, ReplenishmentRequest> entry : Repository.REPLENISHMENT_REQUEST.entrySet()) {
            ReplenishmentRequest request = entry.getValue();
    
            // Check if the status is PENDING
            if (request.getStatus() == InventoryRequestStatus.PENDING) {
                hasPendingRequests = true;
                pendingRequests.append("Request ID: ").append(request.getRequestId()).append("\n");
                pendingRequests.append("Medicine ID: ").append(request.getMedicineId()).append("\n");
                pendingRequests.append("Requested Stock Level: ").append(request.getStockLevel()).append("\n");
                pendingRequests.append("Status: ").append(request.getStatus()).append("\n");
                pendingRequests.append("------------------------------------------------------------\n");
            }
        }
    
        if (!hasPendingRequests) {
            return "No pending replenishment requests found.";
        }
    
        return pendingRequests.toString();
    }
    
    public static boolean approveReplenishmentRequest(String requestId) {
        ReplenishmentRequest request = Repository.REPLENISHMENT_REQUEST.get(requestId);
        if (request == null) {
            System.out.println("Request ID not found.");
            return false;
        }
        
        String medicineId = request.getMedicineId();
        InventoryList inventoryItem = Repository.INVENTORY.get(medicineId);
    
        if (inventoryItem != null && inventoryItem.getInitialStock() < inventoryItem.getLowStocklevelAlert()) {
            int updatedStock = inventoryItem.getInitialStock() + request.getStockLevel();
            inventoryItem.setInitialStock(updatedStock);
    
            Repository.INVENTORY.put(medicineId, inventoryItem);
            request.setStatus(InventoryRequestStatus.APPROVED);
            Repository.REPLENISHMENT_REQUEST.put(requestId, request);
            
            Repository.persistData(FileType.INVENTORY);
            Repository.persistData(FileType.REPLENISHMENT_REQUEST);
    
            return true;
        } else {
            return false;
        }
    }

    public static void rejectReplenishmentRequest(String requestId) {
        ReplenishmentRequest request = Repository.REPLENISHMENT_REQUEST.get(requestId);
        if (request != null) {
            request.setStatus(InventoryRequestStatus.REJECTED);
            Repository.REPLENISHMENT_REQUEST.put(requestId, request);
            Repository.persistData(FileType.REPLENISHMENT_REQUEST);
        } else {
            System.out.println("Request ID not found.");
        }
    }

    public static ReplenishmentRequest getReplenishmentRequestById(String requestId) {
        return Repository.REPLENISHMENT_REQUEST.get(requestId);
    }
}
