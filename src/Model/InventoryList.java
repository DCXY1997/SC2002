package src.Model;

import java.io.Serializable;

public class InventoryList implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    private Medicine medicine;
    private int initialStock;
    private int lowStocklevelAlert;

    // Constructor
    public InventoryList(Medicine medicine, int initialStock, int lowStockLevelAlert) {
        this.medicine =  medicine;
        this.initialStock = initialStock;
        this.lowStocklevelAlert = lowStockLevelAlert;
    }

    // Getters and Setters
    public Medicine getMedicine() {
        return medicine;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public int getLowStocklevelAlert() {
        return lowStocklevelAlert;
    }

    public void setMedicine(Medicine medicine){
        this.medicine = medicine;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }

    public void setLowStocklevelAlert(int lowStocklevelAlert) {
        this.lowStocklevelAlert = lowStocklevelAlert;
    }
}
