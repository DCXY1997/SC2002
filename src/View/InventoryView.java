package src.View;

import java.util.Scanner;
import src.Helper.Helper;
import src.Controller.*;

/**
 * The InventoryView class provides an interface for pharmacists to view and
 * manage the medication inventory in the hospital management system.
 * <p>
 * This class allows pharmacists to:
 * </p>
 * <ul>
 * <li>View all medication inventory.</li>
 * <li>Submit replenishment requests for low-stock medication.</li>
 * <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Integrates with {@link InventoryController} to fetch inventory data.</li>
 * <li>Allows pharmacists to identify and address low-stock inventory.</li>
 * <li>Facilitates submitting replenishment requests via
 * {@link PharmacistController}.</li>
 * </ul>
 *
 * @see InventoryController
 * @see PharmacistController
 * @see Helper
 * @see MainView
 * @author Keng Jia Chi, Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class InventoryView extends MainView {

    /**
     * Displays the actions available in the Medication Inventory View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     * <li>(1) View all medication inventory.</li>
     * <li>(2) Navigate back to the previous menu.</li>
     * </ul>
     */
    @Override
    protected void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Medication Inventory View");
        System.out.println("(1) View All Medication Inventory");
        System.out.println("(2) Back");
    }

    /**
     * Controls the workflow of the Medication Inventory View.
     * <p>
     * Allows the pharmacist to view the full inventory or navigate back to the
     * previous menu.
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
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > View All Medication Inventory");
                    String inventory = InventoryController.checkAllInventory(1);

                    if (inventory.contains("No inventory found")) {
                        System.out.println("No medication inventory available.");
                    } else {
                        System.out.println(inventory);
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

    /**
     * Displays the interface for submitting replenishment requests for
     * low-stock medication.
     * <p>
     * This method provides the following options:
     * </p>
     * <ul>
     * <li>(1) View low-stock medication and submit a replenishment
     * request.</li>
     * <li>(2) Navigate back to the previous menu.</li>
     * </ul>
     * The pharmacist can view a list of low-stock medication and choose to
     * submit a request for a specific medicine by entering its ID.
     */
    public void displayReplenishmentRequest() {
        Scanner sc = new Scanner(System.in);
        int opt = -1;
        Helper.clearScreen();
        do {
            Helper.clearScreen();
            printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Submit Replenishment Request");
            System.out.println("(1) Submit Replenishment Request");
            System.out.println("(2) Back");
            opt = Helper.readInt(1, 2);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Pharmacist View > Submit Replenishment Request");
                    String inventory = InventoryController.checkAllInventory(0);
                    if (inventory.contains("No inventory found")) {
                        System.out.println("No low-stock medication inventory available.");
                        break;
                    } else {
                        System.out.println(inventory);
                    }
                    System.out.println("Enter the medicine ID to replenish: ");
                    String medicineId = Helper.readString();
                    PharmacistController.submitReplenishmentRequest(medicineId);
                    break;
                case 2:
                    Helper.pressAnyKeyToContinue();
                    break;
                default:
                    System.out.println("Invalid input!\n");
            }
        } while (opt != 2);
    }
}
