package src.Controller;

import java.util.*;
import src.Enum.*;
import src.Helper.Helper;
import src.Model.AppointmentOutcome;
import src.Model.InventoryList;
import src.Model.Medicine;
import src.Repository.*;

public class AppointmentOutcomeController {
	
	public static String checkPendingMedicinePrescription() {
	    String pendingRequests = null;
		AppointmentOutcomeController controller = new AppointmentOutcomeController();
	    
	    if (Repository.APPOINTMENT_OUTCOME.isEmpty()) {
	        return "No pending medicine prescription found.";
	    }

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
	            pendingRequests = controller.manageAppointmentOutcome(pendingMedicines, appointmentOutcome);
	        }
	    }

	    if (!hasPendingPrescription) {
	        return "No pending medicine prescription found.";
	    }

	    return pendingRequests;
	}
	
	public static void managePendingMedicinePrescription(String outcomeId) {
	    int stockLevel = 0;
		AppointmentOutcomeController controller = new AppointmentOutcomeController();

	    AppointmentOutcome outcome = Repository.APPOINTMENT_OUTCOME.get(outcomeId);
	    if (outcome == null) 
	    {
	        System.out.println("Outcome ID not found.\n");
			Helper.pressAnyKeyToContinue();
			//Helper.clearScreen();
	        return;
	    }

	    boolean hasPendingMedicine = false;
		int i = 0;
		Helper.clearScreen();
	    System.out.println("Prescribed Medicines for Appointment Outcome ID: " + outcomeId+"\n");
	    for (Medicine medicine : outcome.getPrescribedMedicines()) 
	    {
	        if (medicine.getStatus() == MedicineStatus.PENDING)
	        {
	            InventoryList inventoryItem = Repository.INVENTORY.get(medicine.getMedicineId());
	            stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0;
	            System.out.println("Medicine ID: " + medicine.getMedicineId());
	            System.out.println("Medicine Name: " + medicine.getMedicineName());
	            System.out.println("Current Status: " + medicine.getStatus());
	            System.out.println("Amount Prescribed: " + outcome.getMedicineAmount().get(i++));
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
		int index = 0;
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

	                if (stockLevel < outcome.getMedicineAmount().get(index)) 
	                {
	                    System.out.println("Medicine ID " + medicineIdToApprove + " does not have enough stock level.");
	                }
	                else 
	                {
						String record;
	                    medicine.setStatus(MedicineStatus.DISPENSED);
	                    int newStockLevel = stockLevel - outcome.getMedicineAmount().get(index);
	                    inventoryItem.setInitialStock(newStockLevel);
	                    Repository.INVENTORY.put(medicineIdToApprove, inventoryItem);
	                    System.out.println("Medicine ID " + medicine.getMedicineId() + " has been approved."+"\n");
						System.out.println("Printing updated appointment outcome...");
						record = controller.manageAppointmentOutcome(outcome.getPrescribedMedicines(), outcome);
						System.out.println(record+"\n");
						Repository.persistData(FileType.INVENTORY);
	                    Repository.persistData(FileType.APPOINTMENT_OUTCOME);

	                }
	            }
	            break; // Exit the loop after processing the matched medicine
	        }
			index++;
	    }

	    // Print message only if no matching medicine was found after checking all items
	    if (!medicineFound) 
		{
			System.out.println("Medicine ID " + medicineIdToApprove + " not found.\n");
		}
		Helper.pressAnyKeyToContinue();
	}

	public String manageAppointmentOutcome(List<Medicine> pendingMedicines, AppointmentOutcome appointmentOutcome)
	{
		int i = 0;
		StringBuilder pendingRequests = new StringBuilder();

		pendingRequests.append("Medicine Prescription:\n");
	    pendingRequests.append("------------------------------------------------------------\n");
		pendingRequests.append("Appointment Outcome ID: ").append(appointmentOutcome.getOutcomeId()).append("\n");
		for (Medicine medicine : pendingMedicines) 
		{
			pendingRequests.append("Medicine ID: ").append(medicine.getMedicineId()).append("\n");
			pendingRequests.append("Medicine Name: ").append(medicine.getMedicineName()).append("\n");
			pendingRequests.append("Amount: ").append(appointmentOutcome.getMedicineAmount().get(i++)).append("\n");
			pendingRequests.append("Status: ").append(medicine.getStatus()).append("\n");
			pendingRequests.append("------------------------------------------------------------\n");
			
		}
		pendingRequests.append("------------------------------------------------------------\n");
		return pendingRequests.toString();
	}


	
}
