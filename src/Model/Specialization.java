package src.Model;

import java.io.Serializable;

public class Specialization implements Serializable {

    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;

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

    // Override toString to display meaningful information about the specialization
    @Override
    public String toString() {
        return specializationName + " - " + specializationDescription;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Specialization that = (Specialization) obj;
        return specializationName != null ? specializationName.equals(that.specializationName) : that.specializationName == null;
    }

    @Override
    public int hashCode() {
        return specializationName != null ? specializationName.hashCode() : 0;
    }
}
