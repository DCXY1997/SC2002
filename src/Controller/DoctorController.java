package src.Controller;

import java.time.LocalDateTime;
import java.util.*;
import src.Enum.AppointmentStatus;
import src.Model.Appointment;
import src.Model.AppointmentList;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;
import src.Model.Specialization;
import src.Repository.FileType;
import src.Repository.Repository;

public class DoctorController {

    public static List<Patient> getAllPatients(Doctor doctor) {
        Doctor currentDoctor = (Doctor) Repository.STAFF.get(doctor.getHospitalId());
        List<Appointment> docAppointments = AppointmentController.viewDoctorAppointments(currentDoctor);
        List<Patient> patientsUnderDoctor = new ArrayList<>();
        for (Appointment appointment : docAppointments) {
            Patient patient = appointment.getPatient();
            if (!patientsUnderDoctor.contains(patient)) {
                patientsUnderDoctor.add(patient);
            }
        }
        return patientsUnderDoctor;
    }

    public static List<Schedule> getSchedule(Doctor doctor) {
        // Retrieve the doctor using the hospitalId from the repository
        Doctor currentDoctor = (Doctor) Repository.STAFF.get(doctor.getHospitalId());

        // Check if the doctor is found
        if (currentDoctor != null) {
            return currentDoctor.getAvailability();
        } else {
            System.out.println("Error: Doctor not found.");
            return new ArrayList<>();
        }
    }

    public static void addAvailability(Doctor doctor, LocalDateTime from, LocalDateTime to) {
        Schedule newSchedule = new Schedule(from, to);
        Repository.readData(FileType.STAFF);

        if (Repository.STAFF.containsKey(doctor.getHospitalId())) {
            Doctor existingDoctor = (Doctor) Repository.STAFF.get(doctor.getHospitalId());
            List<Schedule> availability = existingDoctor.getAvailability();

            List<Schedule> conflictingSchedules = new ArrayList<>();

            // Check for overlapping schedules
            for (Schedule schedule : availability) {
                if (from.isBefore(schedule.getEndTime()) && to.isAfter(schedule.getStartTime())) {
                    conflictingSchedules.add(schedule);
                }
            }

            if (!conflictingSchedules.isEmpty()) {
                // Display conflicting schedules
                System.out.println("\nConflicting time ranges found:");
                for (Schedule conflict : conflictingSchedules) {
                    System.out.println("From " + conflict.getStartTime() + " to " + conflict.getEndTime());
                }
                System.out.println("Failed to add to schedule!\n");
            } else {
                // No conflicts; add the new schedule
                existingDoctor.addAvailability(newSchedule);
                Repository.STAFF.put(existingDoctor.getHospitalId(), existingDoctor);
                Repository.persistData(FileType.STAFF);
                System.out.println("Availability " + newSchedule.getStartTime() + " to " + newSchedule.getEndTime() + " added successfully to Doctor " + doctor.getName());
            }
        } else {
            System.out.println("Error: Doctor with ID " + doctor.getHospitalId() + " does not exist in the repository.");
        }
    }

    public static boolean updateDoctorAvailability(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime, List<Schedule> filteredSchedules) {
        List<Schedule> doctorAvailability = new ArrayList<>(doctor.getAvailability());
        doctorAvailability.addAll(filteredSchedules);

        doctor.setAvailability(filteredSchedules);
        // Persist the updated availability
        Repository.readData(FileType.STAFF);
        Repository.STAFF.put(doctor.getHospitalId(), doctor);
        Repository.persistData(FileType.STAFF);
        return true;
    }

    public static List<Appointment> getRequestedAppointments(Doctor doctor) {
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : AppointmentList.getInstance().getAppointments()) {
            if (appointment.getAttendingDoctor().equals(doctor)
                    && appointment.getStatus().equals(AppointmentStatus.PENDING)
                    && doctor.getHospitalId().equals(doctor.getHospitalId())) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public static void displayPersonalInformation(String loginId) {
        Doctor doctor = (Doctor) Repository.STAFF.get(loginId);

        if (doctor != null) {
            System.out.println("Doctor ID: " + doctor.getHospitalId());
            System.out.println("Name: " + doctor.getName());
            System.out.println("Age: " + doctor.getAge());
            System.out.println("Gender: " + doctor.getGender());

            List<Specialization> specializations = doctor.getDocSpecialization();
            System.out.println("Doctor's Specialization List: ");
            if (specializations.isEmpty()) {
                System.out.println("NIL");
            } else {
                for (Specialization specialization : specializations) {
                    System.out.println(specialization);
                }
            }
        } else {
            System.out.println("Doctor not found.");
        }

    }

    public static void addSpecialization(Doctor doctor, Specialization specialization) {
        if (doctor != null && specialization != null) {
            Repository.readData(FileType.STAFF);

            // Check if the doctor exists in the repository
            if (Repository.STAFF.containsKey(doctor.getHospitalId())) {
                // Retrieve the existing doctor from the HashMap
                Doctor existingDoctor = (Doctor) Repository.STAFF.get(doctor.getHospitalId());

                // Check if the specialization already exists for the doctor
                if (existingDoctor.getDocSpecialization().contains(specialization)) {
                    System.out.println("Error: Doctor already has the specialization " + specialization.getSpecializationName() + ".");
                } else {
                    // Add the specialization if it doesn't exist
                    existingDoctor.addSpecialization(specialization);

                    // Update the repository with the modified doctor
                    Repository.STAFF.put(existingDoctor.getHospitalId(), existingDoctor);
                    Repository.persistData(FileType.STAFF);

                    System.out.println("Specialization " + specialization.getSpecializationName() + " added successfully to Doctor " + existingDoctor.getName());
                }
            } else {
                System.out.println("Error: Doctor with ID " + doctor.getHospitalId() + " does not exist in the repository.");
            }
        } else {
            System.out.println("Error: Doctor or Specialization is null.");
        }
    }

    public static List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        // Iterate through all doctors in the repository (STAFF)
        for (Object doctorObj : Repository.STAFF.values()) {
            if (doctorObj instanceof Doctor) {
                Doctor doctor = (Doctor) doctorObj;

                // Check if the doctor's hospitalId starts with "D"
                if (doctor.getHospitalId().startsWith("D")) {
                    doctors.add(doctor);
                }
            }
        }
        if (doctors.isEmpty()) {
            System.out.println("No doctors found with a hospital ID starting with 'D'.");
        }

        return doctors;
    }
}
