package src.Model;

import java.io.Serializable;

import src.Enum.InventoryRequestStatus;

public class ReplenishmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    private String requestId;
    private String medicineId;
    private int stockLevel;
    private InventoryRequestStatus status;

    // Constructor
    public ReplenishmentRequest(String requestId, String medicineId, int stockLevel, InventoryRequestStatus status) {
        this.requestId =  requestId;
        this.medicineId = medicineId;
        this.stockLevel = stockLevel;
        this.status = status;
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public InventoryRequestStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryRequestStatus status) {
        this.status = status;
    }

}