package src.Controller;

import java.util.Map;
import java.util.*;
import src.Enum.*;
import src.Model.AppointmentOutcome;
import src.Model.Medicine;
import src.Repository.Repository;

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
	            	pendingRequests.append("  Medicine ID: ").append(medicine.getMedicineId()).append("\n");
	                pendingRequests.append("  Medicine Name: ").append(medicine.getMedicineName()).append("\n");
	                pendingRequests.append("  Amount: ").append(medicine.getMedicineAmount()).append("\n");
	                pendingRequests.append("  Status: ").append(medicine.getStatus()).append("\n");
	                pendingRequests.append("  ------------------------------------------------------------\n");
	                
	            }
	            pendingRequests.append("  ------------------------------------------------------------\n");
	        }
	    }

	    if (!hasPendingPrescription) {
	        return "No pending replenishment requests found.";
	    }

	    return pendingRequests.toString();
	}


	
}
