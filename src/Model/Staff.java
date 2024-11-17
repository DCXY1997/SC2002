package src.Model;

import java.io.Serializable;
import src.Enum.Gender;
import src.Enum.StaffType;

/**
 * The Staff class represents an employee in a hospital management system.
 * <p>
 * This class serves as a base class for specific staff roles such as {@link Admin}.
 * It includes common attributes such as name, password, age, role, gender, and hospital ID.
 * </p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Tracks personal details like name, age, and gender.</li>
 *   <li>Defines the staff's role using {@link StaffType}.</li>
 *   <li>Manages the staff's credentials for authentication.</li>
 * </ul>
 *
 * @see Admin
 * @see Gender
 * @see StaffType
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class Staff implements Serializable {

    /**
     * For Java Serializable.
     * Ensures compatibility during serialization and deserialization processes.
     */
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String password;
    private int age;
    protected StaffType role;
    private Gender gender;
    private String hospitalId;

    /**
     * Default constructor for the Staff class.
     * <p>
     * Creates a Staff object with default values.
     * </p>
     */
    public Staff() {
    }

    /**
     * Constructs a new Staff object with the specified attributes.
     *
     * @param name       The name of the staff member.
     * @param password   The password for the staff member's account.
     * @param role       The role of the staff member (e.g., {@link StaffType#ADMIN}).
     * @param gender     The gender of the staff member (e.g., {@link Gender#MALE}, {@link Gender#FEMALE}).
     * @param age        The age of the staff member.
     * @param hospitalId The unique hospital ID assigned to the staff member.
     */
    public Staff(String name, String password, StaffType role, Gender gender, int age, String hospitalId) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.role = role;
        this.gender = gender;
        this.hospitalId = hospitalId;
    }

    /**
     * Sets the name of the staff member.
     *
     * @param name The name of the staff member.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the staff member.
     *
     * @return The name of the staff member.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the password for the staff member's account.
     *
     * @param password The password for the staff member's account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the password for the staff member's account.
     *
     * @return The password for the staff member's account.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the age of the staff member.
     *
     * @param age The age of the staff member.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Retrieves the age of the staff member.
     *
     * @return The age of the staff member.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Sets the role of the staff member.
     *
     * @param role The {@link StaffType} of the staff member.
     */
    public void setRole(StaffType role) {
        this.role = role;
    }

    /**
     * Retrieves the role of the staff member.
     *
     * @return The {@link StaffType} of the staff member.
     */
    public StaffType getRole() {
        return this.role;
    }

    /**
     * Sets the gender of the staff member.
     *
     * @param gender The {@link Gender} of the staff member.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the gender of the staff member.
     *
     * @return The {@link Gender} of the staff member.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Sets the unique hospital ID assigned to the staff member.
     *
     * @param hospitalId The unique hospital ID of the staff member.
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * Retrieves the unique hospital ID assigned to the staff member.
     *
     * @return The unique hospital ID of the staff member.
     */
    public String getHospitalId() {
        return this.hospitalId;
    }
}
