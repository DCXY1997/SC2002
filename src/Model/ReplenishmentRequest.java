package src.Model;

import java.io.Serializable;

import src.Enum.InventoryRequestStatus;

/**
 * The ReplenishmentRequest class represents a request to replenish stock levels
 * for a specific medicine.
 * <p>
 * This class includes details such as the request ID, associated medicine ID,
 * requested stock level, and the status of the request. It is serializable,
 * allowing it to be saved and retrieved from persistent storage.
 * </p>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Tracks the unique request ID.</li>
 * <li>Maintains the ID of the medicine for which the request is made.</li>
 * <li>Records the requested stock level.</li>
 * <li>Stores the current status of the request using
 * {@link InventoryRequestStatus}.</li>
 * </ul>
 *
 * @see InventoryRequestStatus
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class ReplenishmentRequest implements Serializable {

    /**
     * For Java Serializable. Ensures compatibility during serialization and
     * deserialization processes.
     */
    private static final long serialVersionUID = 1L;

    // Attributes
    private String requestId;
    private String medicineId;
    private int stockLevel;
    private InventoryRequestStatus status;

    /**
     * Constructs a new ReplenishmentRequest object with the specified details.
     *
     * @param requestId The unique identifier of the replenishment request.
     * @param medicineId The unique identifier of the medicine for which the
     * request is made.
     * @param stockLevel The quantity of stock requested for replenishment.
     * @param status The {@link InventoryRequestStatus} of the request.
     */
    public ReplenishmentRequest(String requestId, String medicineId, int stockLevel, InventoryRequestStatus status) {
        this.requestId = requestId;
        this.medicineId = medicineId;
        this.stockLevel = stockLevel;
        this.status = status;
    }

    /**
     * Retrieves the unique ID of the replenishment request.
     *
     * @return The request ID.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the unique ID of the replenishment request.
     *
     * @param requestId The new request ID.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Retrieves the ID of the medicine associated with the replenishment
     * request.
     *
     * @return The medicine ID.
     */
    public String getMedicineId() {
        return medicineId;
    }

    /**
     * Sets the ID of the medicine associated with the replenishment request.
     *
     * @param medicineId The new medicine ID.
     */
    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * Retrieves the stock level requested for replenishment.
     *
     * @return The requested stock level.
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Sets the stock level requested for replenishment.
     *
     * @param stockLevel The new stock level.
     */
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /**
     * Retrieves the current status of the replenishment request.
     *
     * @return The {@link InventoryRequestStatus} of the request.
     */
    public InventoryRequestStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the replenishment request.
     *
     * @param status The new {@link InventoryRequestStatus} of the request.
     */
    public void setStatus(InventoryRequestStatus status) {
        this.status = status;
    }

}
