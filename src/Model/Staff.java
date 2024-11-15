package src.Model;

import java.io.Serializable;
import src.Enum.Gender;
import src.Enum.StaffType;

public class Staff implements Serializable {
    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;
    protected String name;
    protected String password;
    private int age;
    protected StaffType role;
    private Gender gender;
    private String hospitalId;

    // Empty constructor
    public Staff() {
    }

    // Getters and Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(StaffType role) {
        this.role = role;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getPassword() {
        return this.password;
    }

    public StaffType getRole() {
        return this.role;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getHospitalId() {
        return this.hospitalId;
    }

    // Parameterized constructor
    public Staff(String name, String password, StaffType role, Gender gender, int age, String hospitalId) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.role = role;
        this.gender = gender;
        this.hospitalId = hospitalId;
    }
}

