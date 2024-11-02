package src.Model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentList {

    // Attribute
    private final List<Appointment> appointments;

    // Constructor
    public AppointmentList() {
        this.appointments = new ArrayList<>();
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
