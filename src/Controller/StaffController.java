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

    // public static Staff getStaffById(String loginId) {
    // // Retrieve the staff member from the repository using the loginId
    // Staff staff = Repository.STAFF.get(loginId);

    // if (staff == null) {
    // System.out.println("Staff not found with ID: " + loginId);
    // return null;
    // } else {
    // // Check the staff type and return the appropriate subclass instance
    // if (staff instanceof Doctor) {
    // return (Doctor) staff;
    // } else {
    // System.out.println("Retrieved staff: " + staff.getName() + ", Role: " +
    // staff.getRole());
    // return staff;
    // }
    // }
    // }

    public static Staff getStaffById(String loginId) {
        return Repository.STAFF.get(loginId);
    }

}