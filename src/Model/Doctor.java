package src.Model;

import java.util.ArrayList;
import java.util.List;
import src.Enum.Gender;
import src.Enum.StaffType;

/**
 * Represents a doctor in the hospital management system.
 *
 * <p>
 * The {@code Doctor} class extends {@link Staff} and includes additional
 * attributes and methods specific to doctors, such as specializations,
 * appointment lists, and availability schedules.</p>
 *
 * <p>
 * This class ensures the use of a singleton instance of {@link AppointmentList}
 * to manage appointments across the system.</p>
 *
 * @author Jasmine Tye Jia Wen, Bryan
 * @version 1.0
 * @since 2024-11-17
 */
public class Doctor extends Staff {

    /**
     * For Java Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The list of specializations associated with the doctor.
     */
    private List<Specialization> docSpecialization;

    /**
     * The list of appointments assigned to the doctor. This uses a singleton
     * {@link AppointmentList}.
     */
    private AppointmentList appointList;

    /**
     * The list of schedules representing the doctor's availability.
     */
    private List<Schedule> availability;

    /**
     * Default constructor for {@code Doctor}.
     * <p>
     * Initializes the doctor with a default role and a singleton appointment
     * list.</p>
     */
    public Doctor() {
        super();
        this.role = StaffType.DOCTOR;
        this.appointList = AppointmentList.getInstance(); // Use singleton instance
    }

    /**
     * Parameterized constructor for {@code Doctor}.
     * <p>
     * Initializes the doctor with specified attributes.</p>
     *
     * @param name The name of the doctor.
     * @param password The doctor's password for authentication.
     * @param gender The gender of the doctor.
     * @param age The age of the doctor.
     * @param hospitalId The unique hospital ID assigned to the doctor.
     * @param docSpecialization The list of specializations for the doctor.
     * @param appointList The appointment list assigned to the doctor.
     * @param availability The schedule representing the doctor's availability.
     */
    public Doctor(String name, String password, Gender gender, int age, String hospitalId,
            List<Specialization> docSpecialization, AppointmentList appointList, List<Schedule> availability) {
        super(name, password, StaffType.DOCTOR, gender, age, hospitalId);
        this.docSpecialization = docSpecialization != null ? new ArrayList<>(docSpecialization) : new ArrayList<>();
        this.appointList = AppointmentList.getInstance(); // Use singleton instance
        this.availability = availability != null ? new ArrayList<>(availability) : new ArrayList<>();
    }

    /**
     * Returns the list of specializations for the doctor.
     *
     * @return A list of {@link Specialization} objects.
     */
    public List<Specialization> getDocSpecialization() {
        return docSpecialization;
    }

    /**
     * Adds a specialization to the doctor's list of specializations.
     *
     * @param specialization The specialization to add.
     */
    public void addSpecialization(Specialization specialization) {
        if (specialization != null) {
            this.docSpecialization.add(specialization);
        }
    }

    /**
     * Returns the singleton {@link AppointmentList} for the doctor.
     *
     * @return The appointment list.
     */
    public AppointmentList getAppointList() {
        return appointList;
    }

    /**
     * Adds an appointment to the doctor's appointment list.
     *
     * @param appointment The appointment to add.
     */
    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointList.addAppointment(appointment);
        }
    }

    /**
     * Returns the doctor's availability schedule.
     *
     * @return A list of {@link Schedule} objects.
     */
    public List<Schedule> getAvailability() {
        return availability;
    }

    /**
     * Sets the doctor's availability schedule.
     *
     * @param availability A list of {@link Schedule} objects.
     */
    public void setAvailability(List<Schedule> availability) {
        this.availability = availability;
    }

    /**
     * Adds a schedule to the doctor's availability.
     *
     * @param schedule The schedule to add.
     */
    public void addAvailability(Schedule schedule) {
        if (schedule != null) {
            this.availability.add(schedule);
        }
    }

    /**
     * Returns a string representation of the doctor.
     *
     * @return A string containing the doctor's ID and name.
     */
    @Override
    public String toString() {
        return "Doctor[ID=" + super.getHospitalId() + ", Name=" + name + "]";
    }

    /**
     * Compares this doctor with another object for equality based on their
     * hospital IDs.
     *
     * @param obj The object to compare.
     * @return {@code true} if the objects are equal; otherwise, {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Doctor doctor = (Doctor) obj;
        return super.getHospitalId().equals(doctor.getHospitalId());
    }

    /**
     * Returns the hash code for this doctor based on the hospital ID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return super.getHospitalId().hashCode();
    }
}
