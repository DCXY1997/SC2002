package src.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import src.Enum.MedicineStatus;
import src.Helper.Helper;
import src.Model.AppointmentOutcome;
import src.Model.InventoryList;
import src.Model.Medicine;
import src.Repository.FileType;
import src.Repository.Repository;

/**
 * The {@code AppointmentOutcomeController} class manages operations related to
 * appointment outcomes, specifically handling pending medicine prescriptions,
 * approval, and inventory updates. It interacts with the {@link Repository} to
 * manage appointment outcomes and inventory data.
 *
 * @author Cheah Wei Jun, Bryan
 * @version 1.0
 * @since 2024-11-17
 */
public class AppointmentOutcomeController {

    /**
     * Checks for appointment outcomes with pending medicine prescriptions. If
     * found, displays pending medicine details for each outcome.
     */
    public static void checkPendingMedicinePrescription() {
        String pendingRequests = null;
        AppointmentOutcomeController controller = new AppointmentOutcomeController();

        if (Repository.APPOINTMENT_OUTCOME.isEmpty()) {
            System.out.println("No appointment outcome with pending medicine prescription found.\n");
            return;
        }

        boolean hasPendingPrescription = false;

        // Loop through all requests in the repository
        for (Map.Entry<String, AppointmentOutcome> entry : Repository.APPOINTMENT_OUTCOME.entrySet()) {
            int i = 0;
            AppointmentOutcome appointmentOutcome = entry.getValue();

            // Create a list to store pending medicines for the current appointment
            List<Medicine> pendingMedicines = new ArrayList<>();
            List<Integer> pendingAmount = new ArrayList<>();

            // Check each medicine in the prescribed medicines list
            for (Medicine medicine : appointmentOutcome.getPrescribedMedicines()) {
                if (medicine.getStatus() == MedicineStatus.PENDING) {
                    hasPendingPrescription = true;
                    // Add the pending medicine to the list
                    pendingMedicines.add(medicine);
                    pendingAmount.add(appointmentOutcome.getMedicineAmount().get(i++));
                }
            }

            // If there are any pending medicines, print them
            if (!pendingMedicines.isEmpty()) {
                pendingRequests = controller.manageAppointmentOutcome(pendingMedicines, appointmentOutcome, pendingAmount, false);
            }
            if (pendingRequests != null) {
                System.out.println(pendingRequests.toString());
            }
        }

        if (!hasPendingPrescription) {
            System.out.println("No appointment outcome with pending medicine prescription.\n");
        }
    }

    /**
     * Allows the management of a specific appointment outcome with pending
     * medicine prescriptions. Prompts the user to approve or dispense
     * prescribed medicines based on stock availability.
     *
     * @param outcomeId The ID of the appointment outcome to manage.
     */
    public static void managePendingMedicinePrescription(String outcomeId) {
        int i = 0, again = 0, stockLevel = 0;
        String pendingRequests = null;
        boolean pending = true, dispensed = false;
        AppointmentOutcomeController controller = new AppointmentOutcomeController();
        List<Medicine> pendingMedicines = new ArrayList<>();
        List<Integer> pendingAmount = new ArrayList<>();

        AppointmentOutcome outcome = Repository.APPOINTMENT_OUTCOME.get(outcomeId);
        if (outcome == null) {
            System.out.println("Outcome ID not found.\n");
            Helper.pressAnyKeyToContinue();
            //Helper.clearScreen();
            return;
        }

        boolean hasPendingMedicine = false;
        Helper.clearScreen();
        for (Medicine medicine : outcome.getPrescribedMedicines()) {
            if (medicine.getStatus() == MedicineStatus.PENDING) {
                pendingMedicines.add(medicine);
                pendingAmount.add(outcome.getMedicineAmount().get(i++));
                hasPendingMedicine = true;
            }
        }
        if (hasPendingMedicine) {
            pendingRequests = controller.manageAppointmentOutcome(pendingMedicines, outcome, pendingAmount, true);
            System.out.println(pendingRequests);
        } else {
            System.out.println("No pending medicines found for this appointment outcome.\n");
            Helper.pressAnyKeyToContinue();
            return;
        }

        System.out.println("Prescribed Medicines for Appointment Outcome ID: " + outcomeId + "\n");
        do {
            System.out.println("\nEnter the Medicine ID to be approved: ");
            String medicineIdToApprove = Helper.readString();

            boolean medicineFound = false; // Flag to track if the medicine is found and checked
            int index = 0;
            again = 0;
            for (Medicine medicine : outcome.getPrescribedMedicines()) {
                if (medicine.getMedicineId().equals(medicineIdToApprove)) {
                    medicineFound = true; // Set flag to true when a match is found
                    if (medicine.getStatus() != MedicineStatus.PENDING) {
                        System.out.println("Medicine ID " + medicineIdToApprove + " status is not pending.");
                        pending = false;
                    } else {
                        InventoryList inventoryItem = Repository.INVENTORY.get(medicineIdToApprove);
                        stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0;

                        if (stockLevel < outcome.getMedicineAmount().get(index)) {
                            System.out.println("Medicine ID " + medicineIdToApprove + " does not have enough stock level.");
                            break;
                        } else {
                            dispensed = true;
                            String record;
                            medicine.setStatus(MedicineStatus.DISPENSED);
                            int newStockLevel = stockLevel - outcome.getMedicineAmount().get(index);
                            inventoryItem.setInitialStock(newStockLevel);
                            Repository.INVENTORY.put(medicineIdToApprove, inventoryItem);

                            System.out.println("Medicine ID " + medicine.getMedicineId() + " has been approved." + "\n");
                            System.out.println("Printing updated appointment outcome...");
                            record = controller.manageAppointmentOutcome(outcome.getPrescribedMedicines(), outcome, outcome.getMedicineAmount(), true);
                            System.out.println(record + "\n");

                            Repository.persistData(FileType.INVENTORY);
                            Repository.persistData(FileType.APPOINTMENT_OUTCOME);
                            System.out.println("(1) Continue Prescribe");
                            System.out.println("(2) Back");
                            again = Helper.readInt(1, 2);
                        }
                    }
                    break;
                }
                index++;
            }
            // Print message only if no matching medicine was found after checking all items
            if (!medicineFound) {
                System.out.println("Medicine ID " + medicineIdToApprove + " not found.\n");
                break;
            }
        } while (again != 2 && dispensed && pending);
        Helper.pressAnyKeyToContinue();
    }

    /**
     * Generates a formatted summary of pending medicines for an appointment
     * outcome. Includes prescription details and optional inventory
     * information.
     *
     * @author Bryan
     * @param pendingMedicines The list of pending medicines.
     * @param appointmentOutcome The appointment outcome containing the pending
     * prescriptions.
     * @param pendingAmount The list of prescription amounts for the pending
     * medicines.
     * @param inventory A boolean flag indicating whether to include inventory
     * details in the summary.
     * @return A formatted string containing the details of pending medicines.
     */
    public String manageAppointmentOutcome(List<Medicine> pendingMedicines, AppointmentOutcome appointmentOutcome, List<Integer> pendingAmount, boolean Inventory) {
        int i = 0;
        StringBuilder pendingRequests = new StringBuilder();

        pendingRequests.append("Medicine Prescription:\n");
        pendingRequests.append("------------------------------------------------------------\n");
        pendingRequests.append("Appointment Outcome ID: ").append(appointmentOutcome.getOutcomeId()).append("\n");
        for (Medicine medicine : pendingMedicines) {
            pendingRequests.append("Medicine ID: ").append(medicine.getMedicineId()).append("\n");
            pendingRequests.append("Medicine Name: ").append(medicine.getMedicineName()).append("\n");
            pendingRequests.append("Prescription Amount: ").append(pendingAmount.get(i++)).append("\n");
            pendingRequests.append("Status: ").append(medicine.getStatus()).append("\n");
            if (Inventory) {
                InventoryList inventoryItem = Repository.INVENTORY.get(medicine.getMedicineId());
                int stockLevel = (inventoryItem != null) ? inventoryItem.getInitialStock() : 0;
                pendingRequests.append("Stock Level: ").append(stockLevel).append("\n");
            }
            pendingRequests.append("------------------------------------------------------------\n");
        }
        pendingRequests.append("------------------------------------------------------------\n");
        return pendingRequests.toString();
    }

    public static String generateApptOutcomeId() {
        String prefix = "OUT";
        int uniqueId = Helper.generateUniqueId(Repository.APPOINTMENT_OUTCOME);
        return prefix + String.format("%03d", uniqueId);
    }

}
