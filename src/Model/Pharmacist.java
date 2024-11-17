package src.Model;

import src.Enum.*;

/**
 * The Pharmacist class represents a specific type of {@link Staff} member in the hospital management system.
 * This class extends the {@link Staff} class and sets the role to {@link StaffType#PHARMACIST}.
 * <p>
 * It provides constructors to initialize a pharmacist with default or specific details.
 * </p>
 * 
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Default constructor for creating a generic pharmacist with default values.</li>
 *   <li>Parameterized constructor for creating a pharmacist with specific details such as name, password, gender, age, and hospital ID.</li>
 * </ul>
 * 
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class Pharmacist extends Staff {
    /**
     * Serialization identifier for the Pharmacist class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for the Pharmacist class.
     * <p>
     * This initializes a Pharmacist object with default values and sets the role to {@link StaffType#PHARMACIST}.
     * </p>
     */
    public Pharmacist() {
        super();
        this.role = StaffType.PHARMACIST;
    }

    /**
     * Parameterized constructor for the Pharmacist class.
     * <p>
     * This constructor initializes a Pharmacist object with specific details such as name, password, gender, age, and hospital ID.
     * The role is automatically set to {@link StaffType#PHARMACIST}.
     * </p>
     * 
     * @param name       The name of the pharmacist.
     * @param password   The password of the pharmacist.
     * @param gender     The {@link Gender} of the pharmacist.
     * @param age        The age of the pharmacist.
     * @param hospitalId The unique hospital ID assigned to the pharmacist.
     */
    public Pharmacist(String name, String password, Gender gender, int age, String hospitalId) {
        super(name, password, StaffType.PHARMACIST, gender, age, hospitalId);
    }
}
