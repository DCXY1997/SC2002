package src.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        List<Patient> patientsUnderDoctor = new ArrayList<>();
        for (Appointment appointment : AppointmentList.getInstance().getAppointments()) {
            Doctor attendingDoctor = appointment.getAttendingDoctor();
            if (attendingDoctor != null && attendingDoctor.equals(currentDoctor)) { // Corrected comparison
                Patient patient = appointment.getPatient();
                if (!patientsUnderDoctor.contains(patient)) {
                    patientsUnderDoctor.add(patient);
                }
            }
        }
        return patientsUnderDoctor;
    }

    public static List<Schedule> getSchedule(Doctor doctor) {
        // Retrieve the doctor using the hospitalId from the repository
        Doctor currentDoctor = (Doctor) Repository.STAFF.get(doctor.getHospitalId());

        // Check if the doctor is found
        if (currentDoctor != null) {
            // System.out.println("Doctor's Availability List: ");
            // Return the availability list
            return currentDoctor.getAvailability();
        } else {
            // If the doctor is not found, log the error and return an empty list
            System.out.println("Error: Doctor not found.");
            return new ArrayList<>();  // Return an empty list if the doctor is not found
        }
    }

    public static void addAvailibility(Doctor doctor, LocalDateTime from, LocalDateTime to) {
        Schedule schedule = new Schedule(from, to);

        // Load existing staff data from the .dat file
        Repository.readData(FileType.STAFF);

        // Check if the doctor exists in the repository
        if (Repository.STAFF.containsKey(doctor.getHospitalId())) {
            // Retrieve the existing doctor from the HashMap
            Doctor existingDoctor = (Doctor) Repository.STAFF.get(doctor.getHospitalId());

            // Add the new specialization to the existing doctor
            existingDoctor.addAvailability(schedule);

            // Update the doctor in the STAFF HashMap
            Repository.STAFF.put(existingDoctor.getHospitalId(), existingDoctor);

            // Persist the updated STAFF data back to the .dat file
            Repository.persistData(FileType.STAFF);

            System.out.println("Availability " + schedule.getStartTime() + " to " + schedule.getEndTime() + " added successfully to Doctor " + doctor.getName());
        } else {
            System.out.println("Error: Doctor with ID " + doctor.getHospitalId() + " does not exist in the repository.");
        }
    }

    public static boolean updateDoctorAvailability(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime, List<Schedule> filteredSchedules) {
        List<Schedule> updatedSchedule = new ArrayList<>();
        boolean scheduleUpdated = false;

        System.out.println("Doctor's availability size: " + filteredSchedules.size());

        for (Schedule schedule : filteredSchedules) {
            // Check if the appointment fits within the available time slot
            boolean isStartTimeValid = startTime.isEqual(schedule.getStartTime()) || startTime.isAfter(schedule.getStartTime());
            boolean isEndTimeValid = endTime.isBefore(schedule.getEndTime()) || endTime.isEqual(schedule.getEndTime());

            if (isStartTimeValid && isEndTimeValid) {
                scheduleUpdated = true;  // The appointment fits within this schedule
                System.out.println("Schedule fits! Appointment start: " + startTime + " to end: " + endTime);

                // Split the availability into slots around the appointment time
                if (startTime.isAfter(schedule.getStartTime())) {
                    updatedSchedule.add(new Schedule(schedule.getStartTime(), startTime));  // Before appointment                    
                }
                if (endTime.isBefore(schedule.getEndTime())) {
                    updatedSchedule.add(new Schedule(endTime, schedule.getEndTime()));  // After appointment
                }
            } else {
                // If the appointment doesn't fit in this time slot, leave the schedule unchanged
                updatedSchedule.add(schedule);
            }
        }

        if (!scheduleUpdated) {
            return false;
        }

        // Set the updated schedule to the doctor's availability list
        doctor.setAvailability(updatedSchedule);

        // Persist the updated availability
        Repository.readData(FileType.STAFF);
        Repository.STAFF.put(doctor.getHospitalId(), doctor);
        Repository.persistData(FileType.STAFF);

        return true;
    }

    public static List<Appointment> getRequestedAppointments(Doctor doctor, String hospitalId) {
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : AppointmentList.getInstance().getAppointments()) {
            if (appointment.getAttendingDoctor().equals(doctor)
                    && appointment.getStatus().equals(AppointmentStatus.PENDING)
                    && doctor.getHospitalId().equals(hospitalId)) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    // public static List<Appointment> getUpcomingAppointments(Doctor doctor, String hospitalId) {
    //     List<Appointment> appointments = new ArrayList<>();
    //     for (Appointment appointment : AppointmentList.getInstance().getAppointments()) {
    //         if (appointment.getAttendingDoctor().equals(doctor)
    //                 && appointment.getAppointmentDate().isAfter(LocalDateTime.now())
    //                 && doctor.getHospitalId().equals(hospitalId)) {
    //             // hospital
    //             appointments.add(appointment);
    //         }
    //     }
    //     return appointments;
    // }
    // public static Appointment getAppointmentById(int id) {
    //     for (Appointment appointment : AppointmentList.getInstance().getAppointments()) {
    //         if (appointment.getAppointmentId() == id) {
    //             return appointment;
    //         }
    //     }
    //     return null;
    // }
    public static void displayPersonalInformation(String loginId) {
        Doctor doctor = (Doctor) Repository.STAFF.get(loginId);

        if (doctor != null) {
            System.out.println("Doctor ID: " + doctor.getHospitalId() + "\n");
            System.out.println("Name: " + doctor.getName() + "\n");
            System.out.println("Age: " + doctor.getAge() + "\n");
            System.out.println("Gender: " + doctor.getGender() + "\n");

            // System.out.println("Specialization: " + doctor.getDocSpecialization());
            System.out.println("Doctor's Specialization List: ");
            for (Specialization specialization : doctor.getDocSpecialization()) {
                System.out.println(specialization);  // This will now print the meaningful toString() output
            }

            // remove this later
            System.out.println("Doctor's Availability List: ");
            for (Schedule schedule : doctor.getAvailability()) {
                System.out.println(schedule);  // This will now print the meaningful toString() output
            }

            System.out.println("Appointment List: " + doctor.getAppointList());
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

                existingDoctor.addSpecialization(specialization);

                Repository.STAFF.put(existingDoctor.getHospitalId(), existingDoctor);
                Repository.persistData(FileType.STAFF);

                System.out.println("Specialization " + specialization.getSpecializationName() + " added successfully to Doctor " + existingDoctor.getName());
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
