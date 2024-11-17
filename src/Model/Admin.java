package src.Model;

import src.Enum.Gender;
import src.Enum.StaffType;

/**
 * The Admin class represents an administrator in the system, inheriting from the {@link Staff} class.
 * <p>
 * The Admin class is a specialized type of staff member, always assigned the {@link StaffType#ADMIN} role.
 * It provides constructors for creating administrator accounts with or without parameters.
 * </p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Automatically sets the role to {@link StaffType#ADMIN}.</li>
 *   <li>Supports both default and parameterized constructors.</li>
 * </ul>
 *
 * @see Staff
 * @see StaffType
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class Admin extends Staff {
    /**
     * For Java Serializable.
     * Ensures compatibility during the serialization and deserialization process.
     */

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for the Admin class.
     * <p>
     * Creates an Admin object with default values and automatically assigns the {@link StaffType#ADMIN} role.
     * </p>
     */
   
    public Admin() {
        super();
        this.role = StaffType.ADMIN; // Always set the role to Admin
    }

    /**
     * Parameterized constructor for creating an Admin object with specified details.
     * Automatically assigns the {@link StaffType#ADMIN} role.
     *
     * @param name       The name of the admin.
     * @param password   The password for the admin account.
     * @param gender     The gender of the admin (e.g., MALE, FEMALE).
     * @param age        The age of the admin.
     * @param hospitalId The unique hospital ID assigned to the admin.
     */

    public Admin(String name, String password, Gender gender, int age, String hospitalId) {
        super(name, password, StaffType.ADMIN, gender, age, hospitalId); // Call parent class constructor with role
                                                                         // ADMIN
    }
}