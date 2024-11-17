package src.View;

import java.util.List;
import src.Controller.AppointmentController;
import src.Controller.PaymentController;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.Patient;

/**
 * The {@code PaymentView} class handles the user interface for managing patient
 * payments. It interacts with the {@link PaymentController} and displays
 * completed appointments where payment is required.
 *
 * <p>
 * <b>Features:</b></p>
 * <ul>
 * <li>Allows patients to view their completed appointments.</li>
 * <li>Enables patients to select an appointment and make a payment.</li>
 * <li>Provides navigation back to the previous menu.</li>
 * </ul>
 *
 * <p>
 * <b>Associated Controllers:</b></p>
 * <ul>
 * <li>{@link PaymentController}</li>
 * <li>{@link AppointmentController}</li>
 * </ul>
 *
 * <p>
 * <b>Usage:</b></p>
 * <ul>
 * <li>Initialize the view with a valid {@link Patient} object.</li>
 * <li>Use {@link #viewApp()} to launch the payment interface.</li>
 * </ul>
 *
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class PaymentView extends MainView {

    /**
     * The {@link Patient} for whom the payments are being managed.
     */
    private Patient patient;

    /**
     * Constructs a new {@code PaymentView} for a specific patient.
     *
     * @param patient The {@link Patient} for whom the payment view is being
     * initialized. Must not be {@code null}.
     * @throws IllegalArgumentException if the {@code patient} is {@code null}.
     */
    public PaymentView(Patient patient) {
        if (patient == null) {
            System.out.println("Error: Patient is null in PaymentView.");
            throw new IllegalArgumentException("Patient cannot be null");
        }
        this.patient = patient;
    }

    /**
     * Prints the actions available in the payment view. This includes options
     * to make a payment or navigate back to the previous menu.
     */
    @Override
    public void printActions() {
        printBreadCrumbs("Hospital Management App View > Login View > Patient View > Payment View");
        System.out.println("(1) Make Payment");
        System.out.println("(2) Back");
    }

    /**
     * Handles the main navigation for the payment view. Provides options for
     * patients to make a payment or return to the previous menu.
     */
    @Override
    public void viewApp() {
        PaymentController paymentController = new PaymentController();
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 3);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Patient View > Payment View > Make Payment");
                    paymentController.checkAppointmentId(promptCompletedAppointments(patient));
                    Helper.pressAnyKeyToContinue();
                    Helper.clearScreen();
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
     * Prompts the patient to select a completed appointment for payment.
     *
     * <p>
     * Displays a list of completed appointments and allows the patient to
     * choose one. The selected appointment is then returned for further
     * processing.
     * </p>
     *
     * @param patient The {@link Patient} whose completed appointments are being
     * displayed.
     * @return The {@link Appointment} selected by the patient.
     */
    public static Appointment promptCompletedAppointments(Patient patient) {
        List<Appointment> appointments = AppointmentController.viewCompleteAppointments(patient);
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ".");
            PaymentController.generateReceipt(appointments.get(i).getOutcome().getOutcomeId());
        }
        System.out.println("====================================================");
        System.out.println("Enter the index of the appointment to be paid for: ");
        int index = Helper.readInt(1, appointments.size());
        return appointments.get(index - 1);
    }
}
