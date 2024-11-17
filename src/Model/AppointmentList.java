package src.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AppointmentList} class is a singleton that manages the list of
 * appointments in the application. This ensures that there is only one instance
 * of the appointment list throughout the application, providing centralized
 * management of appointments.
 *
 * <p>
 * This class provides methods to access, add, and retrieve appointments, and is
 * designed to be thread-safe for consistent access to the list of
 * appointments.</p>
 *
 * <p>
 * <b>Features:</b></p>
 * <ul>
 * <li>Singleton design pattern to ensure a single instance.</li>
 * <li>Stores and manages a list of {@link Appointment} objects.</li>
 * <li>Provides thread-safe access to the appointment list.</li>
 * </ul>
 *
 * @author Keng Jia Chi, Jasmine Tye Jia Wen
 * @version 1.0
 * @since 2024-11-17
 */
public class AppointmentList implements Serializable {

    /**
     * For Java Serializable, used to ensure compatibility during
     * deserialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The single instance of the {@code AppointmentList}.
     */
    private static final AppointmentList appointmentList = new AppointmentList();

    /**
     * The list of appointments managed by the application.
     */
    private final List<Appointment> appointments;

    /**
     * Private constructor to prevent instantiation from other classes.
     * Initializes an empty list of appointments.
     */
    private AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    /**
     * Provides access to the single instance of the {@code AppointmentList}.
     *
     * @return The singleton instance of {@code AppointmentList}.
     */
    public static AppointmentList getInstance() {
        return appointmentList;
    }

    /**
     * Retrieves the list of all appointments.
     *
     * @return A {@link List} of {@link Appointment} objects.
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Adds an appointment to the list.
     *
     * @param appointment The {@link Appointment} object to add. If
     * {@code null}, the method does nothing.
     */
    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointments.add(appointment);
        }
    }
}
