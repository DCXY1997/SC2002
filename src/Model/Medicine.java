package src.Model;

import java.io.Serializable;
import src.Enum.MedicineStatus;

/**
 * The {@code Medicine} class is used for defining
 * medicines prescribed by the hospital
 *
 * @author Cheah Wei Jun, Bryan
 * @version 1.0
 * @since 2024-11-17
 */

public class Medicine implements Serializable {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID associated with medicine.
     */
    private String medicineId;

    /**
     * Name of medicine.
     */
    private String medicineName;

    /**
     * Dispense Status of medicine,
     * it is changed when Patient pays for it.
     */
    private MedicineStatus status;

    /**
     * Price of medicine.
     */
    private float medicinePrice;

    /**
     * Description of what the medicine does.
     */
    private String medicineDescription;

    /**
     * Constructs a new {@code Medicine} with the specified details.
     *
     * @param medicineId The unique identifier for the medicine.
     * @param medicalName The medicine's name.
     * @param medicinePrice The medicine's price for payment.
     * @param medicineDescription The medicine's description.
     */
    public Medicine(String medicineId, String medicineName, float medicinePrice, String medicineDescription) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicinePrice = medicinePrice;
        this.medicineDescription = medicineDescription;
        this.status = MedicineStatus.PENDING;
    }

    /**
     * Constructs a clone {@code Medicine} with the same details as the input Medicine.
     *
     * @param clone The medicine object to be cloned
     * This clone is used for Medicine in AppointmentOutcome object to keep track of medicine
     * prescribed by Doctor, and to separate from the Medicine object used in Inventory.
     */
    public Medicine(Medicine clone) {
        this.medicineId = clone.medicineId;
        this.medicineName = clone.medicineName;
        this.medicinePrice = clone.medicinePrice;
        this.medicineDescription = clone.medicineDescription;
        this.status = clone.status;
    }

    // Getters and Setters
    /**
     * Returns the unique identifier of the medicine.
     *
     * @return The medicine ID.
     */
    public String getMedicineId() {
        return medicineId;
    }

    /**
     * Sets the unique identifier of the medicine.
     *
     * @param medicineId The new medicine ID.
     */
    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * Returns the medicine's name.
     *
     * @return The medicine's name.
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Sets the medicine's name.
     *
     * @param medicalName The new name.
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Return the medicine's status.
     *
     * @return The status.
     */
    public MedicineStatus getStatus() {
        return status;
    }

    /**
     * Sets the medicine's status.
     *
     * @param status The status.
     */
    public void setStatus(MedicineStatus status) {
        this.status = status;
    }

    /**
     * Return the medicine's price.
     *
     * @return The price.
     */
    public float getMedicinePrice() {
        return medicinePrice;
    }

    /**
     * Sets the medicine's price.
     *
     * @param medicinePrice The price.
     */
    public void setMedicinePrice(float medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    /**
     * Return the medicine's description.
     *
     * @return The description.
     */
    public String getMedicineDescription() {
        return medicineDescription;
    }

    /**
     * Sets the medicine's description.
     *
     * @param medicineDescription The description.
     */
    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
    }
}
