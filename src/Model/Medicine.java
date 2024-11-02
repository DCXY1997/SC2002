package src.Model;

import java.io.Serializable;
import src.Enum.MedicineStatus;

public class Medicine implements Serializable{

    private static final long serialVersionUID = 1L;

    // Attributes
    private String medicineId;
    private String medicineName;
    private float medicinePrice;
    private String medicineDescription;

    // Constructor
    public Medicine(String medicineId, String medicineName, float medicinePrice,
            String medicineDescription) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicinePrice = medicinePrice;
        this.medicineDescription = medicineDescription;
    }

    // Getters and Setters
    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public float getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(float medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    public String getMedicineDescription() {
        return medicineDescription;
    }

    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
    }
}