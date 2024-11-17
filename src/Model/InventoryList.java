package src.Model;

import java.io.Serializable;

/**
 * The InventoryList class represents an inventory item in the medical inventory system.
 * <p>
 * It includes details about the medicine, its initial stock level, and a low stock level alert threshold.
 * This class is serializable, allowing it to be saved and retrieved from persistent storage.
 * </p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Tracks the medicine associated with the inventory item.</li>
 *   <li>Maintains the current stock level of the medicine.</li>
 *   <li>Defines a low stock alert threshold for the medicine.</li>
 * </ul>
 *
 * @see Medicine
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class InventoryList implements Serializable {

    /**
     * For Java Serializable.
     * Ensures compatibility during serialization and deserialization processes.
     */

    private static final long serialVersionUID = 1L;

    // Attributes
    private Medicine medicine;
    private int initialStock;
    private int lowStocklevelAlert;

     /**
     * Constructs a new InventoryList object with the specified medicine, stock level, and low stock alert threshold.
     *
     * @param medicine          The {@link Medicine} associated with this inventory item.
     * @param initialStock      The initial stock level of the medicine.
     * @param lowStockLevelAlert The low stock alert threshold for the medicine.
     */

    public InventoryList(Medicine medicine, int initialStock, int lowStockLevelAlert) {
        this.medicine =  medicine;
        this.initialStock = initialStock;
        this.lowStocklevelAlert = lowStockLevelAlert;
    }

    /**
     * Retrieves the medicine associated with this inventory item.
     *
     * @return The {@link Medicine} object.
     */

    public Medicine getMedicine() {
        return medicine;
    }
    /**
     * Retrieves the initial stock level of the medicine.
     *
     * @return The initial stock level.
     */

    public int getInitialStock() {
        return initialStock;
    }
    /**
     * Retrieves the low stock level alert threshold for the medicine.
     *
     * @return The low stock level alert threshold.
     */

    public int getLowStocklevelAlert() {
        return lowStocklevelAlert;
    }
    /**
     * Sets the medicine associated with this inventory item.
     *
     * @param medicine The {@link Medicine} object to associate with this inventory item.
     */

    public void setMedicine(Medicine medicine){
        this.medicine = medicine;
    }
    /**
     * Sets the initial stock level of the medicine.
     *
     * @param initialStock The new stock level.
     */

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }
     /**
     * Sets the low stock level alert threshold for the medicine.
     *
     * @param lowStocklevelAlert The new low stock alert threshold.
     */
    
    public void setLowStocklevelAlert(int lowStocklevelAlert) {
        this.lowStocklevelAlert = lowStocklevelAlert;
    }
}
