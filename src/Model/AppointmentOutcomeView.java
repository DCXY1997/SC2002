package src.View;

import java.util.Map; 
import src.Model.*;
import src.Controller.*;
import java.util.List;
import src.Helper.Helper;
import src.Repository.*;
import src.Enum.*;

public class AppointmentOutcomeView extends MainView{
	
	
	protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Appointment Outcome View");
        System.out.println("(1) View Pending Medicine Prescription");
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
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Display Pending Medicine Prescription");
                    String pendingRequests = AppointmentOutcomeController.checkPendingMedicinePrescription();
                    
                    if (pendingRequests.contains("No pending medicine prescription found.")) {
                        System.out.println("No pending medicine prescription available.");
                    } else {
                        System.out.println(pendingRequests);

                        System.out.println("\n(1) Manage a pending prescription");
                        System.out.println("(2) Back");

                        int choice = Helper.readInt(1, 2);
                        if (choice == 1) {
                            System.out.println("Enter the Outcome ID to manage:");
                            String requestId = Helper.readString();
                            managePendingMedicinePrescription(requestId);
                        }
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
	
	private void managePendingMedicinePrescription(String outcomeId) {
		int stockLevel = 0;
	    // Retrieve the appointment outcome using the provided ID
	    AppointmentOutcome outcome = Repository.APPOINTMENT_OUTCOME.get(outcomeId);
	    if (outcome == null) {
	        System.out.println("Outcome ID not found.");
	        return;
	    }

	    // Display details of prescribed medicines and check for pending statuses
	    boolean hasPendingMedicine = false;
	    System.out.println("Prescribed Medicines for Appointment Outcome ID: " + outcomeId);
	    for (Medicine medicine : outcome.getPrescribedMedicines()) {
	    	if (medicine.getStatus() == MedicineStatus.PENDING)
	    	{
	    		// Get the inventory details for the current medicine
		        InventoryList inventoryItem = Repository.INVENTORY.get(medicine.getMedicineId());
		        stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0; // If null, assume stock is 0

		        System.out.println("Medicine ID: " + medicine.getMedicineId());
		        System.out.println("Medicine Name: " + medicine.getMedicineName());
		        System.out.println("Current Status: " + medicine.getStatus());
		        System.out.println("Amount Prescribed: " + medicine.getMedicineAmount());
		        System.out.println("Amount Available in Stock: " + stockLevel);
		        System.out.println("------------------------------------------------------------");

		        hasPendingMedicine = true;
	    	} 
	    }

	    if (!hasPendingMedicine) {
	        System.out.println("No pending medicines found for this appointment outcome.");
	        return;
	    }

	    System.out.println("\nEnter the Medicine ID to be approved: ");
	    String medicineIdToApprove = Helper.readString();

	    boolean medicineApproved = false;
	    // Approve the specific medicine if found and pending
	    InventoryList inventoryItem = Repository.INVENTORY.get(medicineIdToApprove);
	    stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0;
	    for (Medicine medicine : outcome.getPrescribedMedicines()) {
	        if (medicine.getMedicineId().equals(medicineIdToApprove) && medicine.getStatus() == MedicineStatus.PENDING && (stockLevel>=medicine.getMedicineAmount())) {
	            medicine.setStatus(MedicineStatus.DISPENSED);
	            System.out.println("Medicine ID " + medicine.getMedicineId() + " has been approved.");
	            medicineApproved = true;
	            break;
	        }
	    }

	    if (!medicineApproved) {
	        System.out.println("Medicine ID "+medicineIdToApprove+" is not approved.");
	    } else {
	        System.out.println("Prescription approval process completed.");
	    }
	}


	public void displayAllPendingOutcome() {
	    boolean hasPendingPrescriptions = false;

	    System.out.println("All Appointment Outcomes with Pending Prescriptions:");
	    System.out.println("------------------------------------------------------------");

	    // Iterate through all appointment outcomes in the repository
	    for (Map.Entry<String, AppointmentOutcome> entry : Repository.APPOINTMENT_OUTCOME.entrySet()) {
	        AppointmentOutcome outcome = entry.getValue();
	        List<Medicine> prescribedMedicines = outcome.getPrescribedMedicines();
	        boolean outcomeHasPending = false;

	        for (Medicine medicine : prescribedMedicines) {
	            if (medicine.getStatus() == MedicineStatus.PENDING) {
	                if (!outcomeHasPending) {
	                    // Print the header once for each outcome with pending prescriptions
	                    System.out.println("Appointment Outcome ID: " + outcome.getOutcomeId());
	                    System.out.println("Appointment Date: " + outcome.getDateDiagnosed());
	                    System.out.println("Doctor Notes: " + outcome.getDoctorNotes());
	                    System.out.println("Pending Medicines:");
	                    outcomeHasPending = true;
	                    hasPendingPrescriptions = true;
	                }

	                // Print the medicine details
	                System.out.println(" - " + medicine.getMedicineName() + " | Status: " + medicine.getStatus());
	            }
	        }

	        if (outcomeHasPending) {
	            System.out.println("------------------------------------------------------------");
	        }
	    }

	    if (!hasPendingPrescriptions) {
	        System.out.println("No pending prescriptions found.");
	    }
	}

}
