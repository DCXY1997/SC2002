package src.View;

import src.Model.AppointmentOutcome;
import src.Model.Medicine;
import java.util.List;

public class AppointmentOutcomeView {

	public void displayApointmentOutcome(AppointmentOutcome outcome, int outcomeId)
	{
		System.out.println("Appointment Outcome: ");
		System.out.println("Appointment Outcome ID: "+outcome.getOutcomeId());
		System.out.println("Appointment Date: "+outcome.getDateDiagnosed());
		List<Medicine> prescribedMedicines = outcome.getPrescribedMedicines();
		System.out.println("Prescribed Medicines:");
		for (Medicine medicine : prescribedMedicines) {
            // Print the medicine name and status
            System.out.println(" - " + medicine.getMedicineName() + " | Status: " + medicine.getStatus());
        }
		System.out.println("Doctor Notes: "+outcome.getDoctorNotes());
	}
}
