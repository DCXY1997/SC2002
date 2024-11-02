package src.Model;

import java.io.Serializable;

import src.Enum.Gender;
import src.Enum.StaffType;

public class Admin extends Staff {
    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;

    // Empty constructor
    public Admin() {
        super();
        this.role = StaffType.ADMIN;  // Always set the role to Admin
    }

    // Parameterized constructor specifically for Admin
    public Admin(String name, String password, Gender gender, int age, String hospitalId) {
        super(name, password, StaffType.ADMIN, gender, age, hospitalId);  // Call parent class constructor with role ADMIN
    }
}