package src.Controller;

import src.Enum.StaffType;
import src.Model.Staff;
import src.Repository.FileType;
import src.Repository.Repository;

public class StaffController {
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

    public static boolean changePassword(Staff staff, String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            staff.setPassword(confirmPassword);
            Repository.persistData(FileType.STAFF); // Persist the updated password
            return true;
        } else {
            return false;
        }
    }
}