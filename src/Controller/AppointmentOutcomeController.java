package src.Controller;

import java.util.Map;
import java.util.*;
import src.Enum.*;
import src.Helper.Helper;
import src.Model.AppointmentOutcome;
import src.Model.InventoryList;
import src.Model.Medicine;
import src.Repository.*;

public class AppointmentOutcomeController {
	
	public static String checkPendingMedicinePrescription() {
	    StringBuilder pendingRequests = new StringBuilder();
	    
	    if (Repository.APPOINTMENT_OUTCOME.isEmpty()) {
	        return "No pending medicine prescription found.";
	    }

	    pendingRequests.append("Pending Medicine Prescription:\n");
	    pendingRequests.append("------------------------------------------------------------\n");

	    boolean hasPendingPrescription = false;

	    // Loop through all requests in the repository
	    for (Map.Entry<String, AppointmentOutcome> entry : Repository.APPOINTMENT_OUTCOME.entrySet()) {
	        AppointmentOutcome appointmentOutcome = entry.getValue();
	        
	        // Create a list to store pending medicines for the current appointment
	        List<Medicine> pendingMedicines = new ArrayList<>();

	        // Check each medicine in the prescribed medicines list
	        for (Medicine medicine : appointmentOutcome.getPrescribedMedicines()) {
	            if (medicine.getStatus() == MedicineStatus.PENDING) {
	                hasPendingPrescription = true;
	                // Add the pending medicine to the list
	                pendingMedicines.add(medicine);
	            }
	        }

	        // If there are any pending medicines, print them
	        if (!pendingMedicines.isEmpty()) {
	            pendingRequests.append("Appointment Outcome ID: ").append(appointmentOutcome.getOutcomeId()).append("\n");
	            for (Medicine medicine : pendingMedicines) {
	            	pendingRequests.append("Medicine ID: ").append(medicine.getMedicineId()).append("\n");
	                pendingRequests.append("Medicine Name: ").append(medicine.getMedicineName()).append("\n");
	                pendingRequests.append("Amount: ").append(medicine.getMedicineAmount()).append("\n");
	                pendingRequests.append("Status: ").append(medicine.getStatus()).append("\n");
	                pendingRequests.append("------------------------------------------------------------\n");
	                
	            }
	            pendingRequests.append("------------------------------------------------------------\n");
	        }
	    }

	    if (!hasPendingPrescription) {
	        return "No pending replenishment requests found.";
	    }

	    return pendingRequests.toString();
	}
	
	public static void managePendingMedicinePrescription(String outcomeId) {
	    int stockLevel = 0;
	    AppointmentOutcome outcome = Repository.APPOINTMENT_OUTCOME.get(outcomeId);
	    if (outcome == null) 
	    {
	        System.out.println("Outcome ID not found.");
	        return;
	    }

	    boolean hasPendingMedicine = false;
	    System.out.println("Prescribed Medicines for Appointment Outcome ID: " + outcomeId);
	    for (Medicine medicine : outcome.getPrescribedMedicines()) 
	    {
	        if (medicine.getStatus() == MedicineStatus.PENDING)
	        {
	            InventoryList inventoryItem = Repository.INVENTORY.get(medicine.getMedicineId());
	            stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0;

	            System.out.println("Medicine ID: " + medicine.getMedicineId());
	            System.out.println("Medicine Name: " + medicine.getMedicineName());
	            System.out.println("Current Status: " + medicine.getStatus());
	            System.out.println("Amount Prescribed: " + medicine.getMedicineAmount());
	            System.out.println("Amount Available in Stock: " + stockLevel);
	            System.out.println("------------------------------------------------------------");

	            hasPendingMedicine = true;
	        }
	    }

	    if (!hasPendingMedicine) 
	    {
	        System.out.println("No pending medicines found for this appointment outcome.\n");
	        return;
	    }

	    System.out.println("\nEnter the Medicine ID to be approved: ");
	    String medicineIdToApprove = Helper.readString();

	    boolean medicineFound = false; // Flag to track if the medicine is found and checked
	    for (Medicine medicine : outcome.getPrescribedMedicines())
	    {
	        if (medicine.getMedicineId().equals(medicineIdToApprove)) 
	        {
	            medicineFound = true; // Set flag to true when a match is found
	            if (medicine.getStatus() != MedicineStatus.PENDING)
	            {
	                System.out.println("Medicine ID " + medicineIdToApprove + " status is not pending.");
	            } 
	            else 
	            {
	                InventoryList inventoryItem = Repository.INVENTORY.get(medicineIdToApprove);
	                stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0;

	                if (stockLevel < medicine.getMedicineAmount()) 
	                {
	                    System.out.println("Medicine ID " + medicineIdToApprove + " does not have enough stock level.");
	                }
	                else 
	                {
	                    medicine.setStatus(MedicineStatus.DISPENSED);
	                    int newStockLevel = stockLevel - medicine.getMedicineAmount();
	                    inventoryItem.setInitialStock(newStockLevel);
	                    Repository.INVENTORY.put(medicineIdToApprove, inventoryItem);
	                    System.out.println("Medicine ID " + medicine.getMedicineId() + " has been approved.");

	                    Repository.persistData(FileType.INVENTORY);
	                    Repository.persistData(FileType.APPOINTMENT_OUTCOME);
	                }
	            }
	            break; // Exit the loop after processing the matched medicine
	        }
	    }

	    // Print message only if no matching medicine was found after checking all items
	    if (!medicineFound) 
	        System.out.println("Medicine ID " + medicineIdToApprove + " not found.\n");
	}


	
}
