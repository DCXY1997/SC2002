package src.View;

import java.util.Map; 
import src.Model.*;
import src.Controller.*;
import java.util.*;
import src.Helper.Helper;
import src.Repository.*;
import src.Enum.*;

/**
 * The {@code AppointmentOutcomeView} class handles the user interface for managing appointment outcomes.
 * It interacts with the {@link AppointmentOutcomeController} and displays pending prescriptions, updates prescription statuses, 
 * and provides options to view appointment outcomes.
 * 
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>View all appointment outcomes with pending prescriptions.</li>
 *   <li>Update prescription status for a specific appointment outcome.</li>
 *   <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p><b>Associated Controllers:</b></p>
 * <ul>
 *   <li>{@link AppointmentOutcomeController}</li>
 * </ul>
 *
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class AppointmentOutcomeView extends MainView {

    /**
     * Prints the available actions for the Appointment Outcome view.
     * This method provides options to view appointment outcomes or navigate back.
     */
    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Appointment Outcome View");
        System.out.println("(1) View Appointment Outcome");
        System.out.println("(2) Back");
    }

    /**
     * Handles the main navigation for the Appointment Outcome view.
     * Users can choose to view appointment outcomes or navigate back.
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
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > View Appointment Outcome");
                    AppointmentOutcomeController.checkPendingMedicinePrescription();
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
     * Displays all appointment outcomes that have pending prescriptions.
     * For each outcome with pending prescriptions, the details and associated medicines are listed.
     */
    public void displayAllPendingOutcome() {
        boolean hasPendingPrescriptions = false;

        System.out.println("All Appointment Outcomes with Pending Prescriptions:");
        System.out.println("------------------------------------------------------------");

        for (Map.Entry<String, AppointmentOutcome> entry : Repository.APPOINTMENT_OUTCOME.entrySet()) {
            AppointmentOutcome outcome = entry.getValue();
            List<Medicine> prescribedMedicines = outcome.getPrescribedMedicines();
            boolean outcomeHasPending = false;

            for (Medicine medicine : prescribedMedicines) {
                if (medicine.getStatus() == MedicineStatus.PENDING) {
                    if (!outcomeHasPending) {
                        System.out.println("Appointment Outcome ID: " + outcome.getOutcomeId());
                        System.out.println("Appointment Date: " + outcome.getDateDiagnosed());
                        System.out.println("Doctor Notes: " + outcome.getDoctorNotes());
                        System.out.println("Pending Medicines:");
                        outcomeHasPending = true;
                        hasPendingPrescriptions = true;
                    }

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

    /**
     * Displays the prescription status management menu.
     * Allows users to update the status of prescriptions for a specific appointment outcome.
     */
    public void displayPrescriptionStatus() {
        int opt = -1;
        do {
            Helper.clearScreen();
            printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Manage Prescription Status");
            System.out.println("(1) Update Prescription Status");
            System.out.println("(2) Back");
            opt = Helper.readInt(1, 2);
            switch (opt) {
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
        } while (opt != 2);
    }
}
