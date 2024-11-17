package src.View;

import src.Controller.AdminController;
import src.Controller.InventoryController;
import src.Helper.Helper;

/**
 * The DisplayMedicationInventory class provides an interface for administrators
 * to view the medical inventory in the hospital management system.
 * <p>
 * This class allows administrators to:
 * </p>
 * <ul>
 * <li>View a detailed record of all medical inventory items.</li>
 * <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Integrates with {@link InventoryController} to fetch inventory
 * records.</li>
 * <li>Provides a simple interface to display all available medical inventory
 * items.</li>
 * </ul>
 *
 * @see InventoryController
 * @see AdminController
 * @see Helper
 * @see MainView
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class DisplayMedicationInventory extends MainView {

    /**
     * Displays the actions available in the Medical Inventory View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     * <li>(1) View the medical inventory.</li>
     * <li>(2) Navigate back to the previous menu.</li>
     * </ul>
     */
    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Medical Inventory View");
        System.out.println("(1) View Medical Inventory");
        System.out.println("(2) Back");
    }

    /**
     * Controls the workflow of the Medical Inventory View.
     * <p>
     * Allows the administrator to choose from the available options and
     * interact with the medical inventory interface.
     * </p>
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
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Display Medical Inventory View");
                    String medicalInventory = InventoryController.getInventoryRecord();  // Get details from controller
                    System.out.println(medicalInventory);
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
}
