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
        }while(opt != 2);

    }

    public static Appointment promptCompletedAppointments(Patient patient){
        List<Appointment> appointments = AppointmentController.viewCompleteAppointments(patient);
        for (int i = 0; i < appointments.size(); i++){
            System.out.println((i+1)+".");
            PaymentController.generateReceipt(appointments.get(i).getOutcome().getOutcomeId());
        }
        System.out.println("====================================================");
        System.out.println("Enter the index of the appointment to be paid for: ");
        int index = Helper.readInt(1, appointments.size());
        return appointments.get(index-1);
    }
}
