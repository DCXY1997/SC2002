package src.View;

import java.util.ArrayList;
import java.util.List;
import src.Controller.AppointmentController;
import src.Enum.AppointmentStatus;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.Doctor;

public class DisplayDoctorAppointment extends MainView {

    private Doctor doctor;

    public DisplayDoctorAppointment(Doctor doctor) {
        if (doctor == null) {
            System.out.println("Error: Doctor is null in DisplayDoctorAppointment.");
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        this.doctor = doctor;
        System.out.println("DisplayDoctorAppointment initialized with doctor: " + doctor.getHospitalId());
    }

    @Override
    public void printActions() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Doctor View > Handle Appointments");
        System.out.println("What would you like to do?");
        System.out.println("(1) View Your Appointment Requests");
        System.out.println("(2) Accept/ Decline Appointment Requests");
        System.out.println("(3) View Upcoming Appointments");
        System.out.println("(4) View Past Appointments");
        System.out.println("(5) Back");
    }

    @Override
    public void viewApp() {
        int opt = -1;
        do {
            printActions();
            opt = Helper.readInt(1, 5);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Doctor View > View Appointment Requests");
                    viewAppointmentRequests();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Doctor View > Accept/Decline Appointments");
                    handleAppointmentRequests();
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Doctor View > View Upcoming Appointments");
                    viewUpcomingAppointments();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs("Hospital Management App View > Doctor View > View Past Appointments");
                    viewPastAppointments();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 5) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 5);
    }

    private void viewAppointmentRequests() {
        // Implement logic to display the appointment requests for the doctor
        List<Appointment> appointmentRequests = AppointmentController.viewDoctorAppointments(doctor);

        if (appointmentRequests.isEmpty()) {
            System.out.println("No appointment requests found for you.");
        } else {
            System.out.println("Your Appointment Requests:");

            for (Appointment appointment : appointmentRequests) {
                if (appointment.getStatus() == AppointmentStatus.PENDING) {
                    System.out.println("Appointment - " + appointment.getAppointmentId() + ":");
                    System.out.println("Patient: " + appointment.getPatient().getName());
                    System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
                    System.out.println("Status: " + appointment.getStatus());
                    System.out.println();
                }
            }
        }
    }

    private void viewUpcomingAppointments() {
        // Implement logic to display the appointment requests for the doctor
        List<Appointment> appointmentRequests = AppointmentController.viewDoctorAppointments(doctor);

        if (appointmentRequests.isEmpty()) {
            System.out.println("No appointment requests found for you.");
        } else {
            System.out.println("Your Appointment Requests:");

            for (Appointment appointment : appointmentRequests) {
                if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
                    System.out.println("Appointment - " + appointment.getAppointmentId() + ":");
                    System.out.println("Patient: " + appointment.getPatient().getName());
                    System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
                    System.out.println("Status: " + appointment.getStatus());
                    System.out.println();
                }
            }
        }
    }

    private void handleAppointmentRequests() {
        // Get the list of pending appointments for the doctor
        List<Appointment> allAppointments = AppointmentController.viewDoctorAppointments(doctor);
        List<Appointment> pendingAppointments = new ArrayList<>();

        // Filter for only pending appointments
        for (Appointment appointment : allAppointments) {
            if (appointment.getStatus() == AppointmentStatus.PENDING) {
                pendingAppointments.add(appointment);
            }
        }

        // Check if there are any pending appointments
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointment requests.");
            return;
        }

        // Display the pending appointments to the doctor
        System.out.println("Pending Appointment Requests:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            Appointment appointment = pendingAppointments.get(i);
            System.out.println((i + 1) + ". Patient: " + appointment.getPatient().getName()
                    + "\nFrom: " + appointment.getAppointmentStartDate()
                    + " to " + appointment.getAppointmentEndDate()
                    + "\nAppointment Status: " + appointment.getStatus());
            System.out.println("-----------------------------------------------------------------");
        }

        // Ask for the appointment index to handle
        System.out.print("Enter the appointment number you want to handle: ");
        int appointmentIndex = Helper.readInt(1, pendingAppointments.size());

        // Get the selected appointment (index is 1-based, so we subtract 1)
        Appointment selectedAppointment = pendingAppointments.get(appointmentIndex - 1);

        // Display the selected appointment details
        System.out.println("Selected Appointment with Patient: " + selectedAppointment.getPatient().getName());
        System.out.println("From: " + selectedAppointment.getAppointmentStartDate()
                + " to " + selectedAppointment.getAppointmentEndDate());
        System.out.println("Status: " + selectedAppointment.getStatus());

        // Prompt to accept or decline the appointment
        System.out.println("(1) Accept");
        System.out.println("(2) Decline");
        System.out.print("Enter your choice: ");
        int choice = Helper.readInt(1, 2);

        if (choice == 1) {
            AppointmentController.acceptAppointment(doctor, selectedAppointment);
            System.out.println("Appointment accepted.");
        } else {
            AppointmentController.declineAppointment(selectedAppointment);
            System.out.println("Appointment declined.");
        }
    }

     private void viewPastAppointments() {
         // Implement logic to display the past appointments for the doctor
         List<Appointment> appointmentRequests = AppointmentController.viewCompleteAppointments(doctor);

        if (appointmentRequests.isEmpty()) {
            System.out.println("No appointment requests found for you.");
        } else {
            System.out.println("Your Past Appointment Requests:");

            for (Appointment appointment : appointmentRequests) {
                if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                    System.out.println("Appointment - " + appointment.getAppointmentId() + ":");
                    System.out.println("Patient: " + appointment.getPatient().getName());
                    System.out.println("From: " + appointment.getAppointmentStartDate() + " to " + appointment.getAppointmentEndDate());
                    System.out.println("Status: " + appointment.getStatus());
                    System.out.println();
                }
            }
        }
     }
}
