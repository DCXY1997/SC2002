package src.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import src.Enum.PaymentStatus;
import src.Enum.ServiceType;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.AppointmentOutcome;
import src.Model.Medicine;
import src.Repository.FileType;
import src.Repository.Repository;

/**
 * The {@code PaymentController} class handles the logic for generating payment
 * receipts, processing payments, and updating payment statuses for medical
 * appointments.
 *
 * <p>
 * This class interacts with the {@link Repository} to retrieve appointment
 * outcomes and update payment information.</p>
 *
 * <p>
 * <b>Main Responsibilities:</b></p>
 * <ul>
 * <li>Generate receipts for appointment outcomes.</li>
 * <li>Process payments using cash transactions.</li>
 * <li>Update payment statuses in the system.</li>
 * </ul>
 *
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class PaymentController {

    /**
     * Generates a receipt for the specified appointment outcome.
     *
     * @param outcomeId The unique identifier of the appointment outcome.
     * @return The total amount to be paid for the appointment.
     */
    public static double generateReceipt(String outcomeId) {
        double total = 0;
        boolean paid = false;
        StringBuilder prescribedBuilder = new StringBuilder();

        // Iterate through all appointment outcomes in the repository
        for (Map.Entry<String, AppointmentOutcome> entry : Repository.APPOINTMENT_OUTCOME.entrySet()) {
            AppointmentOutcome outcome = entry.getValue();
            if (outcome.getOutcomeId().equals(outcomeId)) {
                if (outcome.getPaymentStatus() == PaymentStatus.COMPLETED) {
                    paid = true;
                    System.out.println("Payment has been made for appointment " + outcomeId + ".");
                    Helper.pressAnyKeyToContinue();
                    return total;
                }
                Helper.clearScreen();
                prescribedBuilder.append("                       Hospital SCSA                       ").append("\n");
                prescribedBuilder.append("***********************************************************").append("\n");
                prescribedBuilder.append("Receipt").append("\n");
                prescribedBuilder.append("Appointment Outcome ID: ").append(outcome.getOutcomeId()).append("\n");
                prescribedBuilder.append("Appointment Date: ").append(outcome.getDateDiagnosed()).append("\n\n");
                prescribedBuilder.append("Medicines Prescribed:").append("\n");

                List<Medicine> prescribedMedicines = new ArrayList<>();
                for (Medicine medicine : outcome.getPrescribedMedicines()) {
                    prescribedMedicines.add(medicine);
                }

                for (Medicine medicine : prescribedMedicines) {
                    prescribedBuilder.append("Medicine ID: ").append(medicine.getMedicineId()).append("\n");
                    prescribedBuilder.append("Medicine Name: ").append(medicine.getMedicineName()).append("\n");
                    prescribedBuilder.append("Price: $").append(String.format("%.2f", medicine.getMedicinePrice())).append("\n");
                    prescribedBuilder.append("------------------------------------------------------------\n");
                    total += medicine.getMedicinePrice();
                }

                if (outcome.getServices().isEmpty()) {
                    prescribedBuilder.append("No Services to be paid");
                } else {
                    for (ServiceType service : outcome.getServices()) {
                        switch (service) {
                            case CONSULTATION:
                                prescribedBuilder.append("Consulation fee: ").append("$25.00").append("\n\n");
                                total += 25;
                                break;
                            case XRAY:
                                prescribedBuilder.append("XRAY fee: ").append("$100.00").append("\n\n");
                                total += 100;
                                break;
                            case BLOOD_TEST:
                                prescribedBuilder.append("Blood test fee: ").append("$50.00").append("\n\n");
                                total += 50;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        if (!paid) {
            prescribedBuilder.append("Total pending amount: $").append(String.format("%.2f", total)).append("\n");
            prescribedBuilder.append("***********************************************************").append("\n");
            System.out.println(prescribedBuilder.toString());
        }

        return total;
    }

    /**
     * Checks if an appointment has an associated outcome and processes payment
     * if available.
     *
     * @param appointment The appointment to check.
     */

    public void checkAppointmentId(Appointment appointment) {
        boolean hasOutcome = false;
        String outcomeId = appointment.getOutcome().getOutcomeId();
        for (Map.Entry<String, AppointmentOutcome> entry : Repository.APPOINTMENT_OUTCOME.entrySet()) {
            AppointmentOutcome outcome = entry.getValue();
            if (outcome.getOutcomeId().equals(outcomeId)) {
                hasOutcome = true;
                makePayment(outcomeId);
            }
        }
        if (!hasOutcome) {
            System.out.println("No appointment " + outcomeId + " found.\n");
        }
    }

    /**
     * Processes payment for the specified appointment outcome.
     *
     * @param outcomeId The unique identifier of the appointment outcome to
     * process payment for.
     */

    public void makePayment(String outcomeId) {
        boolean success = false;
        boolean confirmation = false;
        double total;
        total = generateReceipt(outcomeId);
        if (total != 0) {
            System.out.println();
            confirmation = Helper.promptConfirmation("make payment");
            if (confirmation) {
                success = payByCash(total);
                if (success) {
                    System.out.println("Payment successfully received. Thank you!\n");
                    AppointmentOutcome outcome = Repository.APPOINTMENT_OUTCOME.get(outcomeId);
                    outcome.setPaymentStatus(PaymentStatus.COMPLETED);
                    Repository.APPOINTMENT_OUTCOME.put(outcomeId, outcome);
                    Repository.persistData(FileType.APPOINTMENT_OUTCOME);
                }
            }
            if (!confirmation || !success) {
                System.out.println("Payment not successful.\n");
            }
        }
    }

    /**
     * Handles cash payments for a specified total amount.
     *
     * @param total The total amount to be paid.
     * @return {@code true} if the payment is successful, {@code false}
     * otherwise.
     */

    public boolean payByCash(double total) {
        double insert = 0;
        boolean success = false;
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter the amount of cash paid: ");
            String userInput = sc.nextLine();
            insert = Float.parseFloat(userInput);
        } catch (NumberFormatException e) {
            return success;
        }

        if (insert < total) {
            System.out.println("Not enough cash.");
        } else {
            System.out.println("\nAmount inserted: $" + (String.format("%.2f", insert)));
            System.out.println("Change: $" + (String.format("%.2f", (insert - total))));
            System.out.println();
            success = true;
        }
        return success;
    }

    /*public static void generateQR()
    {
        int[][] qrCode = {
            {1, 0, 1, 1, 0, 1, 1, 0, 1, 0},
            {1, 0, 0, 1, 0, 1, 0, 1, 1, 0},
            {1, 1, 1, 0, 1, 0, 1, 0, 0, 1},
            {0, 1, 1, 0, 0, 1, 1, 1, 1, 0},
            {1, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 1, 1, 0, 1, 1, 0, 1},
            {0, 1, 1, 1, 0, 1, 1, 0, 1, 0},
            {1, 1, 0, 1, 0, 1, 0, 1, 0, 1}
        };

        // Print the QR code in the console
        for (int i = 0; i < qrCode.length; i++) {
            for (int j = 0; j < qrCode[i].length; j++) {
                if (qrCode[i][j] == 1) {
                    System.out.print("██"); 
                } else {
                    System.out.print("  ");  
                }
            }
            System.out.println(); 
        }
    }*/
}
