package src.View;

import java.util.List;
import src.Controller.AppointmentController;
import src.Controller.PaymentController;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.Patient;

public class PaymentView extends MainView{

    private Patient patient;
    
    public PaymentView(Patient patient) {
        if (patient == null) {
            System.out.println("Error: Patient is null in PaymentView.");
            throw new IllegalArgumentException("Patient cannot be null");
        }
        this.patient = patient;
    }

    @Override
    public void printActions()
    {
        printBreadCrumbs("Hospital Management App View > Login View > Patient View > Payment View");
        System.out.println("(1) Make Payment");
        System.out.println("(2) Back");
    }

    @Override
    public void viewApp()
    {
        PaymentController paymentController = new PaymentController();
        int opt = -1;
        do
        {
            printActions();
            opt = Helper.readInt(1,3);
            switch(opt)
            {
                case 1:
                    Helper.clearScreen();
                    viewCompletedAppointments(patient);
                    paymentController.checkAppointmentId();
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
        }while(opt != 2);

    }

    public static void viewCompletedAppointments(Patient patient){
        List<Appointment> appointments = AppointmentController.viewCompleteAppointments(patient);
        for (Appointment appointment : appointments){
            PaymentController.generateReceipt(appointment.getOutcome().getOutcomeId());
        }
    }
}
