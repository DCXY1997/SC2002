package src.Model;

import java.io.Serializable;
import java.time.LocalDate;
import src.Enum.Gender;

/**
 * Represents a patient in the hospital management system.
 * 
 * <p>The {@code Patient} class contains the patient's personal information,
 * medical history, and associated appointments.</p>
 * 
 * <p>This class implements {@link Serializable} to allow patient data to be serialized
 * for persistence.</p>
 * 
 * @author Bryan, Darren
 * @version 1.0
 * @since 2024-11-17
 */
public class Patient implements Serializable {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    // Attributes

    /**
     * The unique identifier for the patient.
     */
    private String patientId;

    /**
     * The patient's full name.
     */
    private String name;

    /**
     * The patient's password for authentication.
     */
    private String password;

    /**
     * The patient's age.
     */
    private int age;

    /**
     * The patient's gender.
     */
    private Gender gender;

    /**
     * The patient's date of birth.
     */
    private LocalDate dateOfBirth;

    /**
     * The patient's contact information (e.g., email or phone number).
     */
    private String contactInformation;

    /**
     * The patient's blood type.
     */
    private String bloodType;

    /**
     * The patient's medical record.
     */
    private MedicalRecord medicalRecord;

    /**
     * The list of the patient's appointments.
     */
    private AppointmentList appointList;

    // Constructors

    /**
     * Constructs a new {@code Patient} with no initial values.
     */
    public Patient() {
    }

    /**
     * Constructs a new {@code Patient} with the specified details.
     * 
     * @param patientId         The unique identifier for the patient.
     * @param name              The patient's full name.
     * @param password          The patient's password for authentication.
     * @param age               The patient's age.
     * @param dateOfBirth       The patient's date of birth.
     * @param gender            The patient's gender.
     * @param contactInformation The patient's contact information.
     * @param bloodType         The patient's blood type.
     */
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
    }

    // Getters and Setters

    /**
     * Returns the unique identifier of the patient.
     * 
     * @return The patient ID.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the unique identifier of the patient.
     * 
     * @param patientId The new patient ID.
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Returns the patient's full name.
     * 
     * @return The patient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the patient's full name.
     * 
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the patient's password.
     * 
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the patient's password.
     * 
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the patient's age.
     * 
     * @return The patient's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the patient's age.
     * 
     * @param age The new age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the patient's date of birth.
     * 
     * @return The date of birth.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the patient's date of birth.
     * 
     * @param dateOfBirth The new date of birth.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the patient's gender.
     * 
     * @return The gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the patient's gender.
     * 
     * @param gender The new gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns the patient's contact information.
     * 
     * @return The contact information.
     */
    public String getContactInformation() {
        return contactInformation;
    }

    /**
     * Sets the patient's contact information.
     * 
     * @param contactInformation The new contact information.
     */
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * Returns the patient's blood type.
     * 
     * @return The blood type.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Sets the patient's blood type.
     * 
     * @param bloodType The new blood type.
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Returns the patient's appointment list.
     * 
     * @return The appointment list.
     */
    public AppointmentList getAppointmentList() {
        return appointList;
    }

    /**
     * Sets the patient's appointment list.
     * 
     * @param appointList The new appointment list.
     */
    public void setAppointmentList(AppointmentList appointList) {
        this.appointList = appointList;
    }

    /**
     * Returns the patient's medical record.
     * 
     * @return The medical record.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Sets the patient's medical record.
     * 
     * @param medicalRecord The new medical record.
     */
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    // Overrides

    /**
     * Compares this patient with another object based on patient ID.
     * 
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Patient patient = (Patient) obj;
        return this.patientId.equals(patient.getPatientId());
    }

    /**
     * Returns the hash code for the patient object, based on the patient ID.
     * 
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return patientId.hashCode();
    }
}
