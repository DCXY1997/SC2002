package src.View;

import src.Helper.Helper;
import src.Controller.PaymentController;

public class PaymentView extends MainView{

    public void printActions()
    {
        printBreadCrumbs("Hospital Management App View > Login View > Patient View > Payment View");
        System.out.println("(1) Make Payment");
        System.out.println("(2) Back");
    }

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
}
