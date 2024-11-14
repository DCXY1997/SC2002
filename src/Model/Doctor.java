package src.Model;

import java.util.ArrayList;
import java.util.List;
import src.Enum.Gender;
import src.Enum.StaffType;

public class Doctor extends Staff {
    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;
    // Attributes
    private List<Specialization> docSpecialization;
    private AppointmentList appointList;
    private List<Schedule> availability;

    // Empty constructor
    public Doctor() {
        super();
        this.role = StaffType.DOCTOR;
    }

    // Parameterized constructor
    public Doctor(String name, String password, Gender gender, int age, String hospitalId,
            List<Specialization> docSpecialization, AppointmentList appointList, List<Schedule> availability) {
        super(name, password, StaffType.DOCTOR, gender, age, hospitalId);
        this.docSpecialization = docSpecialization != null ? new ArrayList<>(docSpecialization) : new ArrayList<>();
        this.appointList = appointList != null ? appointList : new AppointmentList();
        this.availability = availability != null ? new ArrayList<>(availability) : new ArrayList<>();
    }

    // Getters and Setters
    public List<Specialization> getDocSpecialization() {
        return docSpecialization;
    }

    // Method to add a specialization
    public void addSpecialization(Specialization specialization) {
        if (specialization != null) {
            this.docSpecialization.add(specialization);
        }
    }

    public AppointmentList getAppointList() {
        return appointList;
    }

    // Method to add an appointment to the appointment list
    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointList.addAppointment(appointment);
        }
    }

    public List<Schedule> getAvailability() {
        return availability;
    }

    // Method to add availability to the schedule
    public void addAvailability(Schedule schedule) {
        if (schedule != null) {
            this.availability.add(schedule);
        }
    }
}
