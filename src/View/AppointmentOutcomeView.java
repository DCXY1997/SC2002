package src.View;

import java.util.Map; 
import src.Model.*;
import src.Controller.*;
import java.util.*;
import src.Helper.Helper;
import src.Repository.*;
import src.Enum.*;

public class AppointmentOutcomeView extends MainView{
	
	
	protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Appointment Outcome View");
        System.out.println("(1) View Appointment Outcome");
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
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > View Appointment Outcome");
                    String pendingRequests = AppointmentOutcomeController.checkPendingMedicinePrescription();
                    
                    if (pendingRequests.contains("No pending medicine prescription found.")) 
                    {
                        System.out.println("No appointment outcome with pending medicine prescription available.\n");
                    } 
                    else 
                    {
                        System.out.println(pendingRequests);
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
	
	public void displayPrescriptionStatus()
	{
		Scanner sc = new Scanner(System.in);
		int opt = -1;
		do
		{
			Helper.clearScreen();
			printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Manage Prescription Status");
			System.out.println("(1) Update Prescription Status");
	        System.out.println("(2) Back");
			opt = Helper.readInt(1, 2);
			switch (opt)
			{
				case 1: 
					Helper.clearScreen();
					printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Manage Prescription Status");
					System.out.println("Enter the appointment outcome ID: ");
					String outcomeId = Helper.readString();
			        AppointmentOutcomeController.managePendingMedicinePrescription(outcomeId);
					break;
				case 2:
					Helper.pressAnyKeyToContinue();
					break;
				default:
					System.out.println("Invalid input!");
					
			}	
		}while(opt != 2);
	}
    

}
