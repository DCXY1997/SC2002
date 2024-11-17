package src.Controller;

import src.Enum.StaffType;
import src.Model.Admin;
import src.Model.Doctor;
import src.Model.Patient;
import src.Model.Pharmacist;
import src.Model.Staff;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.AdminView;
import src.View.PatientView;
import src.View.PharmacistView;

/** StaffController is a controller class that acts as a "middleman"
 * between the view classes - {@link AdminView},  {@link PatientView}, {@link PharmacistView} and {@link Doctor View}
 * and the model class - {@link Admin}, {@link Doctor}, {@link Patient}, {@link Pharmacist} and {@link Staff}. <p>
 * 
 * It can authenticate staff, manage passwords, and retrieve staff information by ID. It interacts with the repository to persist staff data and ensure consistency.
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */

public class StaffController {

    /**
     * Authenticates a staff member using their username, password, and role.
    * This method verifies the credentials against the stored staff data in the repository.
    *
    * @param username The username of the staff member.
    * @param password The password of the staff member.
    * @param role     The role of the staff member (e.g., DOCTOR, ADMIN).
    * @return {@code true} if authentication is successful, {@code false} otherwise.
    */

    public static boolean authenticate(String username, String password, StaffType role) {
        // Retrieve the Staff object from the STAFF map
        Staff staff = Repository.STAFF.get(username);

        // Check if the staff member exists
        if (staff == null) {
            return false; // Staff not found
        }

        // Verify password
        if (!staff.getPassword().equals(password)) {
            return false; // Incorrect password
        }

        // Verify role
        if (staff.getRole() != role) { // Compare staff role with expected role
            return false; // Incorrect role
        }

        // Authentication successful
        return true;
    }
    
    /**
    * Changes the password for a given staff member.
    * This method verifies if the new password matches the confirmation password and updates it in the repository.
    *
    * @param staff           The staff member whose password is to be changed.
    * @param password        The new password.
    * @param confirmPassword The confirmation of the new password.
    * @return {@code true} if the password was successfully changed, {@code false} otherwise.
    */

    public static boolean changePassword(Staff staff, String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            staff.setPassword(confirmPassword);
            Repository.persistData(FileType.STAFF); // Persist the updated password
            return true;
        } else {
            return false;
        }
    }

    /**
    * Retrieves a staff member by their unique login ID.
    *
    * @param loginId The unique login ID of the staff member.
    * @return The {@link Staff} object associated with the provided login ID, or {@code null} if not found.
     */

    public static Staff getStaffById(String loginId) {
        return Repository.STAFF.get(loginId);
    }
}