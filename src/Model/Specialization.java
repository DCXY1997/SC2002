package src.Model;

public class Specialization {

    // Attributes
    private String specializationName;
    private String specializationDescription;

    // Constructor
    public Specialization(String specializationName, String specializationDescription) {
        this.specializationName = specializationName;
        this.specializationDescription = specializationDescription;
    }

    // Getters
    public String getSpecializationName() {
        return specializationName;
    }

    public String getSpecializationDescription() {
        return specializationDescription;
    }

    // Setters
    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public void setSpecializationDescription(String specializationDescription) {
        this.specializationDescription = specializationDescription;
    }
}