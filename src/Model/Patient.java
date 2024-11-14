package src.Model;

import java.io.Serializable;
import java.time.LocalDate;
import src.Enum.Gender;

public class Patient implements Serializable {

    // Attributes
    private String patientId;
    private String name;
    private String password;
    private int age;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String contactInformation;
    private String bloodType;
    private AppointmentList appointList;
    private static final long serialVersionUID = 1L;

    // Empty constructor
    public Patient() {
    }

    // Parameterized constructor
    public Patient(String patientId, String name, String password, int age, LocalDate dateOfBirth, Gender gender,
            String contactInformation, String bloodType) {
        this.patientId = patientId;
        this.name = name;
        this.password = password;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactInformation = contactInformation;
        this.bloodType = bloodType;
        // this.appointList = new AppointmentList();
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getBloodType() {
        return bloodType;
    }

    public AppointmentList getAppointmentList() {
        return appointList;
    }

    // Setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setAppointmentList(AppointmentList appointList) {
        this.appointList = appointList;
    }
}
