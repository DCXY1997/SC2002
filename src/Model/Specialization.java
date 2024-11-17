package src.Model;

import java.io.Serializable;

/**
 * Represents a specialization in a particular medical field or expertise.
 *
 * <p>
 * The {@code Specialization} class is used to define and manage the
 * specializations of medical professionals such as doctors.</p>
 *
 * <p>
 * This class implements {@link Serializable} to allow instances of
 * {@code Specialization} to be serialized and stored persistently.</p>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Stores the name and description of the specialization.</li>
 * <li>Supports comparison and meaningful string representation.</li>
 * </ul>
 *
 * @author Jasmine Tye
 * @version 1.0
 * @since 2024-11-17
 */
public class Specialization implements Serializable {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    // Attributes
    /**
     * The name of the specialization.
     */
    private String specializationName;

    /**
     * A brief description of the specialization.
     */
    private String specializationDescription;

    // Constructors
    /**
     * Constructs a new {@code Specialization} with the specified name and
     * description.
     *
     * @param specializationName The name of the specialization.
     * @param specializationDescription A brief description of the
     * specialization.
     */
    public Specialization(String specializationName, String specializationDescription) {
        this.specializationName = specializationName;
        this.specializationDescription = specializationDescription;
    }

    // Getters
    /**
     * Returns the name of the specialization.
     *
     * @return The specialization name.
     */
    public String getSpecializationName() {
        return specializationName;
    }

    /**
     * Returns a brief description of the specialization.
     *
     * @return The specialization description.
     */
    public String getSpecializationDescription() {
        return specializationDescription;
    }

    // Setters
    /**
     * Sets the name of the specialization.
     *
     * @param specializationName The new specialization name.
     */
    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    /**
     * Sets the description of the specialization.
     *
     * @param specializationDescription The new specialization description.
     */
    public void setSpecializationDescription(String specializationDescription) {
        this.specializationDescription = specializationDescription;
    }

    // Overrides
    /**
     * Returns a string representation of the specialization.
     *
     * @return A string combining the name and description of the
     * specialization.
     */
    @Override
    public String toString() {
        return specializationName + " - " + specializationDescription;
    }

    /**
     * Compares this specialization to another object for equality.
     *
     * <p>
     * Two specializations are considered equal if their names are
     * identical.</p>
     *
     * @param obj The object to compare with.
     * @return {@code true} if the specializations have the same name;
     * {@code false} otherwise.
     */
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

    /**
     * Returns a hash code value for the specialization.
     *
     * @return A hash code based on the specialization name.
     */
    @Override
    public int hashCode() {
        return specializationName != null ? specializationName.hashCode() : 0;
    }
}
