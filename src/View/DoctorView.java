package src.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import src.Controller.DoctorController;
import src.Enum.AppointmentStatus;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Schedule;

public class DoctorView extends MainView{
    private DoctorController doctorController;
    public DoctorView(DoctorController doctorController){
        this.doctorController = doctorController;
    }

    Doctor dummy = new Doctor(); //Just here for syntax, but parameter parsing will be based on the Doctor class tied to the HospitalID

    @Override
	public void printActions(){
		Helper.clearScreen();
        printBreadCrumbs("Hospital Management App View > Login View > Doctor View");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View All Current Patients");
        System.out.println("(2) View Patient Medical Records");
        System.out.println("(3) Update Patient Medical Records");
        System.out.println("(4) View Personal Schedule");
        System.out.println("(5) Set Availability for Appointments");
        System.out.println("(6) Accept or Decline Appointment Requests");
        System.out.println("(7) View Upcoming Appointments");
		System.out.println("(8) Record Appointment Outcome");
        System.out.println("(9) Logout");
	}

    @Override
	public void viewApp(){ 
		int opt = -1; 
		do { 
            printActions();
            opt = Helper.readInt(1,8);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    displayAllPatients(dummy);
                    break;
                case 2:
                    Helper.clearScreen();
                    promptGetPatientRecords();
                    break;
                case 3:
                    Helper.clearScreen();
                    //promptUpdatePatientRecords();
                    break;
                case 4:
                	Helper.clearScreen();
                	displaySchedule(dummy);
                    break;
                case 5:
					Helper.clearScreen();
					promptSetAvailibility(dummy);
					break;
                case 6:
                	Helper.clearScreen();
                	promptAppointmentRequests(dummy);
                	break;
                case 7:
                    Helper.clearScreen();
                	displayUpcomingAppointments(dummy);
                	break;
                case 8:
					promptRecordOutcome();
                case 9:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if (opt != 8) {
                Helper.pressAnyKeyToContinue();
            }
        } while (opt != 8);
	}

    public void displayAllPatients(Doctor doctor){
        List<Patient> patientlist = doctorController.getAllPatients(doctor);
        for (Patient patient:patientlist){
            System.out.println("Patient ID: "+ patient.getPatientId());
            System.out.println("Patient Name: " + patient.getName());
        }
    }

    private void promptGetPatientRecords(){
        System.out.println("Key in PatientID of Patient");
        int pid = Helper.readInt();
        //printPatientRecords(doctorController.getPatientRecords(pid));
    }

    //private void printPatientRecords(MedicalRecord record){

    //}


    private void displaySchedule(Doctor doctor){
        List<Schedule> docSchedule = doctorController.getSchedule(doctor);
        for(Schedule schedule: docSchedule){
            System.out.println("From " + schedule.getStartTime() + " to " + schedule.getEndTime());
        }
    }

    private void promptSetAvailibility(Doctor doctor){
        System.out.println("Key in Availibility date and time from(format: yyyy-mm-dd hh:mm):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");Helper.readString();
        String availibility = Helper.readString();
        LocalDateTime from = LocalDateTime.parse(availibility, formatter);
        System.out.println("Key in Availibility date and time to(format: yyyy-mm-dd hh:mm):");
        availibility = Helper.readString();
        LocalDateTime to = LocalDateTime.parse(availibility, formatter);
        doctorController.setAvailibility(doctor, from, to);
    }

    private void promptAppointmentRequests(Doctor doctor){
        List<Appointment> appointList = doctorController.getRequestedAppointments(doctor);
        int indexinput;
        String ARinput;
        do { 
            for (int i=0; i<=appointList.size(); i++){
                System.out.println(i + ":");
                printAppointment(appointList.get(i));
            }
            System.out.println("Enter index of appointment: ");
            System.out.println("Enter -1 to exit");
            indexinput = Helper.readInt();
            if(indexinput<0){
                break;
            }
            System.out.println("Enter A to Accept or R to Reject this appointment: ");
            ARinput = Helper.readString();

            if (ARinput.equals("A")) {
                System.out.println("Appointment accepted: ");
                appointList.get(indexinput).setStatus(AppointmentStatus.CONFIRMED);
            } else if (ARinput.equals("R")) {
                System.out.println("Appointment rejected: ");
                appointList.get(indexinput).setStatus(AppointmentStatus.CANCELLED);
            }
            appointList.remove(indexinput);
        } while (indexinput != -1);

    }

    private void displayUpcomingAppointments(Doctor doctor){
        List<Appointment> appointList = doctorController.getUpcomingAppointments(doctor);
        for(Appointment appointment: appointList){
            printAppointment(appointment);
        }
    }

    private void printAppointment(Appointment appointment){
        System.out.println(appointment.getAppointmentId());
        System.out.println(appointment.getAppointmentDate());
        System.out.println(appointment.getPatient());
        System.out.println(appointment.getAttendingDoctor());
        System.out.println();
    }

    private void promptRecordOutcome(){
        int id;
        System.out.println("Enter AppointmentID: ");
        id = Helper.readInt();
        if(doctorController.getAppointmentById(id) == null){
            System.out.println("Invalid AppointmentID");
            return;
        }
        System.out.println("Please enter Appointment Outcome: ");
        //incomplete stuff AppointmentOutcome outcome = AppointmentOutcome(int outcomeId, List<Medicine> prescribedMedicines, List<Diagnosis> patientDiagnosis, String doctorNotes, LocalDateTime dateDiagnosed);

    }
}
