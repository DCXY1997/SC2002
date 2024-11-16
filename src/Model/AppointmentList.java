package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Make AppointmentList a singleton so that there’s only one instance across the application.
public class AppointmentList implements Serializable {

    /**
     * For Java Serializable
     */
    private static final long serialVersionUID = 1L;

    // Singleton instance
    private static final AppointmentList appointmentList = new AppointmentList();

    private final List<Appointment> appointments;

    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    // Method to access the single instance
    public static AppointmentList getInstance() {
        return appointmentList;
    }

    // Getter for appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Method to add an appointment
    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointments.add(appointment);
        }
    }
}
