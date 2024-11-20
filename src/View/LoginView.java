package src.View;

import java.util.ArrayList;
import src.Controller.PatientController;
import src.Controller.StaffController;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Model.AppointmentList;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Staff;

/**
 * The LoginView class provides an interface for handling user authentication
 * for the hospital management system. Users can log in as staff (Admin, Doctor,
 * Pharmacist) or as patients.
 * <p>
 * This class facilitates:
 * </p>
 * <ul>
 * <li>Authentication of staff and patients.</li>
 * <li>Redirecting users to their respective views upon successful login.</li>
 * <li>Handling incorrect login attempts and error messages.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Integrates with {@link StaffController} to authenticate staff
 * logins.</li>
 * <li>Integrates with {@link PatientController} to authenticate patient
 * logins.</li>
 * <li>Provides redirection to views like {@link AdminView}, {@link DoctorView},
 *   {@link PharmacistView}, and {@link PatientView} based on user roles.</li>
 * </ul>
 *
 * @see StaffController
 * @see PatientController
 * @see AdminView
 * @see DoctorView
 * @see PharmacistView
 * @see PatientView
 * @see Helper
 * @see MainView
 * @author Keng Jia Chi, Jasmine Tye
 * @version 1.0
 * @since 2024-11-17
 */
public class LoginView extends MainView {

    /**
     * Reference to the AdminView for handling admin-related actions after
     * login.
     */
    private AdminView adminView = new AdminView();

    /**
     * Reference to the PharmacistView for handling pharmacist-related actions
     * after login.
     */
    private PharmacistView pharmacistView = new PharmacistView();

    /**
     * Displays the login options available for staff members.
     * <p>
     * The menu options include:
     * </p>
     * <ul>
     * <li>(1) Login as Admin</li>
     * <li>(2) Login as Doctor</li>
     * <li>(3) Login as Pharmacist</li>
     * <li>(4) Back to the previous menu</li>
     * </ul>
     */
    @Override
    protected void printActions() {
        printBreadCrumbs("Hospital Management App View > Login View");
        System.out.println("Choose employee type:");
        System.out.println("(1) Admin");
        System.out.println("(2) Doctor");
        System.out.println("(3) Pharmacist");
        System.out.println("(4) Back");
    }

    /**
     * Displays the login view for staff and redirects to appropriate staff
     * views upon successful authentication.
     */
    @Override
    public void viewApp() {
        viewApp(true);
    }

    /**
     * Manages the login process for staff or patients based on the specified
     * mode.
     * <p>
     * When {@code isStaff} is true, the method authenticates staff logins using
     * {@link StaffController}. Otherwise, it handles patient logins.
     * </p>
     *
     * @param isStaff {@code true} for staff login; {@code false} for patient
     * login.
     */
    public void viewApp(boolean isStaff) {
        if (isStaff) {
            int role = -1;
            StaffType staffType = null;
            Staff loggedInStaff = null;

            do {
                printActions();
                role = Helper.readInt(1, 4);
                switch (role) {
                    case 1:
                        staffType = StaffType.ADMIN;
                        break;
                    case 2:
                        staffType = StaffType.DOCTOR;
                        break;
                    case 3:
                        staffType = StaffType.PHARMACIST;
                        break;
                    case 4:
                        return; // Exit the method if 'Back' is selected
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } while (role <= 0 || role > 4);

            // Handle staff login
            String loginId;
            String password;

            System.out.println("\nLogin ID:");
            loginId = Helper.readString();
            System.out.println("\nPassword:");
            password = Helper.readString();

            boolean loginSuccess = StaffController.authenticate(loginId, password, staffType);
            if (loginSuccess) {
                System.out.println("Login successful, welcome " + loginId);
                Helper.pressAnyKeyToContinue();

                if (staffType == StaffType.ADMIN) {
                    adminView.viewApp();
                } else if (staffType == StaffType.DOCTOR) {
                    loggedInStaff = StaffController.getStaffById(loginId);

                    // Create a new Doctor instance with required parameters
                    Doctor doc = new Doctor(
                            loggedInStaff.getName(),
                            loggedInStaff.getPassword(),
                            loggedInStaff.getGender(),
                            loggedInStaff.getAge(),
                            loggedInStaff.getHospitalId(),
                            new ArrayList<>(),
                            AppointmentList.getInstance(),
                            new ArrayList<>()
                    );

                    DoctorView doctorView = new DoctorView(doc);
                    doctorView.viewApp();
                } else if (staffType == StaffType.PHARMACIST) {
                    loggedInStaff = StaffController.getStaffById(loginId);
                    pharmacistView.viewApp();
                }
            } else {
                System.out.println("Invalid username/password or employee position");
            }
        } else {
            viewAppPatient();
        }
    }

    /**
     * Handles the login process for patients.
     * <p>
     * Authenticates patients using {@link PatientController} and redirects to
     * {@link PatientView} upon successful login.
     * </p>
     */
    public void viewAppPatient() {
        String patientId;
        String password;

        System.out.println("\nPatient ID:");
        patientId = Helper.readString();
        System.out.println("\nPassword:");
        password = Helper.readString();

        boolean loginSuccess = PatientController.authenticate(patientId, password);
        if (loginSuccess) {
            Patient loggedInPatient = PatientController.getPatientById(patientId);

            if (loggedInPatient == null) {
                System.out.println("Error: Patient not found.");
                return;
            }

            System.out.println("Patient login successful, welcome " + loggedInPatient.getPatientId());
            PatientView patientView = new PatientView(loggedInPatient);
            patientView.viewApp();
        } else {
            System.out.println("Invalid patient ID/password.");
        }
    }
}
