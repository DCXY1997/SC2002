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

public class DoctorController {

    public List<Patient> getAllPatients(Doctor doctor){
        List<Patient> patientsUnderDoctor = new ArrayList<>();
        for(Appointment appointment:AppointmentList.getInstance().getAppointments()){
            if(appointment.getAttendingDoctor().equals(doctor)){
                Patient patient = appointment.getPatient();
                if(!patientsUnderDoctor.contains(patient)){
                    patientsUnderDoctor.add(appointment.getPatient());
                }
            }
        }
        return patientsUnderDoctor;
    }

    
    
    //public MedicalRecord getPatientRecord(int patientID){
        //record not done yet
    //}

    public List<Schedule> getSchedule(Doctor doctor){
        return doctor.getAvailability();
    }

    public void setAvailibility(Doctor doctor, LocalDateTime from, LocalDateTime to){
        Schedule schedule = new Schedule(doctor, from, to);
        doctor.addAvailability(schedule);
    }

    public List<Appointment> getRequestedAppointments(Doctor doctor){
        List<Appointment> appointments = new ArrayList<>();
        for(Appointment appointment:AppointmentList.getInstance().getAppointments()){
            if(appointment.getAttendingDoctor().equals(doctor) && appointment.getStatus().equals(AppointmentStatus.PENDING)){
                appointments.add(appointment);
            }
        }
        return appointments;
    }   

    public List<Appointment> getUpcomingAppointments(Doctor doctor){
        List<Appointment> appointments = new ArrayList<>();
        for(Appointment appointment:AppointmentList.getInstance().getAppointments()){
            if(appointment.getAttendingDoctor().equals(doctor) && (appointment.getAppointmentDate().isAfter(LocalDateTime.now()))){
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public Appointment getAppointmentById(int id) {
        for(Appointment appointment : AppointmentList.getInstance().getAppointments()){
            if(appointment.getAppointmentId() == id){
                return appointment;
            }
        }
        return null;
    }
}
