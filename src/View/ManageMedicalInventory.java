package src.View;

import src.Controller.AdminController;
import src.Controller.InventoryController;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Repository.Repository;

/**
 * The ManageMedicalInventory class provides an interface for administrators to
 * manage the medical inventory in the hospital management system.
 * <p>
 * This class allows administrators to:
 * </p>
 * <ul>
 * <li>Add new medical inventory items.</li>
 * <li>Remove existing medical inventory items.</li>
 * <li>Update medical inventory details such as stock level or low-stock alert
 * thresholds.</li>
 * <li>Navigate back to the previous menu.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Integrates with {@link InventoryController} to manage inventory
 * operations.</li>
 * <li>Provides detailed prompts to guide users through inventory management
 * actions.</li>
 * </ul>
 *
 * @see InventoryController
 * @see AdminController
 * @see Helper
 * @see Repository
 * @see MainView
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class ManageMedicalInventory extends MainView {

    /**
     * Displays the actions available in the Manage Medical Inventory View.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     * <li>(1) Add a new medical inventory item.</li>
     * <li>(2) Remove an existing medical inventory item.</li>
     * <li>(3) Update details of a medical inventory item.</li>
     * <li>(4) Exit the menu.</li>
     * </ul>
     */
    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Medical Inventory View");
        System.out.println("What would you like to do?");
        System.out.println("(1) Add Medical Inventory Item");
        System.out.println("(2) Remove Medical Inventory Item");
        System.out.println("(3) Update Medical Inventory Item");
        System.out.println("(4) Exit");
    }

    /**
     * Controls the workflow of the Manage Medical Inventory View.
     * <p>
     * Allows the administrator to choose from the available inventory
     * management actions and perform the desired operation.
     * </p>
     */
    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 4);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Medical Inventory View > Add Medical Inventory Item");
                    promptAddMedicalInventory();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Medical Inventory View > Remove Medical Inventory Item");
                    promptRemoveMedicalInventory();
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Login View > Admin View > Manage Medical Inventory View > Update Medical Inventory Item");
                    promptUpdateMedicalInventory();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 4) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 4);
    }

    /**
     * Prompts the administrator to add a new medical inventory item.
     * <p>
     * Collects the required details for the new inventory item, such as:
     * </p>
     * <ul>
     * <li>Medicine name</li>
     * <li>Price</li>
     * <li>Description</li>
     * <li>Initial stock level</li>
     * <li>Low-stock alert threshold</li>
     * </ul>
     *
     * @return {@code true} if the inventory item is successfully added,
     * {@code false} otherwise.
     */
    private boolean promptAddMedicalInventory() {
        System.out.println("Enter Medicine Name:");
        String name = Helper.readString();

        System.out.println("Enter Medicine Price:");
        float price = (float) Helper.readFloat();

        System.out.println("Enter Medicine Description:");
        String description = Helper.readString();

        System.out.println("Enter Medicine Initial Stock Count:");
        int stock = Helper.readInt();

        System.out.println("Enter Medicine Low Stock Level Alert Count:");
        int stockLow = Helper.readInt();

        String medicineId = generateMedicalInventoryId(name);

        InventoryController.addMedicalInventory(medicineId, name, price, description, stock, stockLow);
        return true;
    }

    /**
     * Generates a unique ID for a new medical inventory item.
     *
     * @param name The name of the medicine.
     * @return A unique string ID for the inventory item.
     */
    public String generateMedicalInventoryId(String name) {
        String prefix = "";
        int uniqueId = Helper.generateUniqueId(Repository.INVENTORY);
        return prefix + String.format("%03d", uniqueId);
    }

    /**
     * Prompts the administrator to remove an existing medical inventory item.
     * <p>
     * Validates the item ID before proceeding with the removal.
     * </p>
     *
     * @return {@code true} if the inventory item is successfully removed,
     * {@code false} otherwise.
     */
    public boolean promptRemoveMedicalInventory() {
        System.out.println("Enter the medical inventory item ID you want to remove: ");
        String medicineId = Helper.readString();

        if (Repository.INVENTORY.containsKey(medicineId)) {
            if (!InventoryController.removeMedicalInventoryItem(medicineId)) {
                System.out.println("Medical Inventory Removal canceled!");
                return false;
            }
        } else {
            System.out.println("Medical Inventory Item Not Found!");
            return false;
        }
        return true;
    }

    /**
     * Prompts the administrator to update an existing medical inventory item.
     * <p>
     * The administrator can choose to update:
     * </p>
     * <ul>
     * <li>Stock count</li>
     * <li>Low-stock alert threshold</li>
     * </ul>
     *
     * @return {@code true} if the inventory item is successfully updated,
     * {@code false} otherwise.
     */
    private boolean promptUpdateMedicalInventory() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management View > Admin View > Update a Medical Inventory Item");

        System.out.println("Enter the medical inventory ID that you want to update: ");
        String medicineId = Helper.readString();

        if (InventoryController.searchMedicalInventoryById(medicineId) == null) {
            System.out.println("Medical inventory item not found!");
            return false;
        }

        System.out.println("What would you like to update?");
        System.out.println("(1) Update Stock Count");
        System.out.println("(2) Update Low Stock Level Alert");
        int choice = Helper.readInt(1, 2);

        boolean success = false;

        if (choice == 1) {
            System.out.println("Please enter the new stock level: ");
            int newStockLevel = Helper.readInt();
            success = InventoryController.updateMedicalInventoryStockLevel(medicineId, newStockLevel);

            if (success) {
                System.out.println("Stock level updated successfully for Medicine ID: " + medicineId);
            } else {
                System.out.println("Failed to update stock level for Medicine ID: " + medicineId);
            }

        } else if (choice == 2) {
            System.out.println("Please enter the new low stock level alert: ");
            int newLowStockLevelAlert = Helper.readInt();
            success = InventoryController.updateMedicalInventoryLowStockAlert(medicineId, newLowStockLevelAlert);

            if (success) {
                System.out.println("Low stock level alert updated successfully for Medicine ID: " + medicineId);
            } else {
                System.out.println("Failed to update low stock level alert for Medicine ID: " + medicineId);
            }
        }

        return success;
    }
}
