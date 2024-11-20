package src.Controller;

import java.util.ArrayList;
import java.util.Map;

import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Model.Admin;
import src.Model.Staff;
import src.Model.Doctor;
import src.Repository.FileType;
import src.Repository.Repository;
import src.View.AdminView;
import src.View.DisplayStaffView;
import src.View.ManageStaffAccountView;

/**
 * AdminController is a controller class that acts as a "middleman" between the
 * view classes - {@link AdminView},  {@link DisplayStaffView} and
 * {@link ManageStaffAccountView} and the model class - {@link Admin} and
 * {@link Staff}.
 * <p>
 *
 * It can view/add/update/remove staff
 *
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class AdminController {

    /**
     * Overloading function to display staff list according to role
     *
     * @param role the role of staff
     * @return {@code true} if display successfully. Otherwise, {@code false}
     */
    public static boolean displayStaffListByRole(StaffType role) {
        ArrayList<Staff> staffNameList = new ArrayList<Staff>();
        //can't just iterate through map, need to do modification to loop through, need to import packages for map.entry
        for (Map.Entry<String, Staff> entry : Repository.STAFF.entrySet()) {
            Staff staff = entry.getValue();
            if (staff.getRole().equals(role)) {
                staffNameList.add(staff);
            }
        }
        if (staffNameList.size() != 0) {
            for (Staff staff : staffNameList) {
                System.out.println(staff.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * Overloading function to display staff list according to gender
     *
     * @param gender the gender of the staff
     * @return {@code true} if display successfully. Otherwise, {@code false}
     */
    public static boolean displayStaffListByGender(Gender gender) {
        ArrayList<Staff> staffNameList = new ArrayList<Staff>();
        //can't just iterate through map, need to do modification to loop through, need to import packages for map.entry
        for (Map.Entry<String, Staff> entry : Repository.STAFF.entrySet()) {
            Staff staff = entry.getValue();
            if (staff.getGender().equals(gender)) {
                staffNameList.add(staff);
            }
        }
        if (staffNameList.size() != 0) {
            for (Staff staff : staffNameList) {
                System.out.println(staff.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * Overloading function to display staff list according to age
     *
     * @param age the age of the staff
     * @return {@code true} if display successfully. Otherwise, {@code false}
     */
    public static boolean displayStaffListByAge(int age) {
        ArrayList<Staff> staffNameList = new ArrayList<Staff>();
        //can't just iterate through map, need to do modification to loop through, need to import packages for map.entry
        for (Map.Entry<String, Staff> entry : Repository.STAFF.entrySet()) {
            Staff staff = entry.getValue();
            if (staff.getAge() == age) {
                staffNameList.add(staff);
            }
        }
        if (staffNameList.size() != 0) {
            for (Staff staff : staffNameList) {
                System.out.println(staff.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * function to add new staff account
     *
     * @param name the name of the new staff
     * @param password the password of the new staff
     * @param gender the gender of the new staff
     * @param age the age of the new staff
     * @param hospitalId the loginId of the new staff
     * @param role the role of the new staff
     */
    public static void addStaffAccount(String name, String password, Gender gender, int age, String hospitalId, StaffType role) {
        Staff staff;
        if (role == StaffType.DOCTOR) {
            staff = new Doctor(name, password, gender, age, hospitalId, null, null, null);
        } else {
            staff = new Staff(name, password, role, gender, age, hospitalId);
        }
        // Update Hash Map
        Repository.STAFF.put(staff.getHospitalId(), staff);
        // Persist data to file
        Repository.persistData(FileType.STAFF);
        System.out.println("Staff added successfully! ID: " + hospitalId);
    }

    /**
     * Function to remove staff from the database
     * <p>
     * @param hospitalID the targeted staff's login ID
     * @return {@code true} if remove successfully. Otherwise, {@code false} if
     * staff id is not found
     */
    public static boolean removeStaffAccount(String hospitalId) {
        if (Repository.STAFF.containsKey(hospitalId)) {
            // Prompt confirmation before removal
            if (Helper.promptConfirmation("remove this staff")) {
                Repository.STAFF.remove(hospitalId);
                Repository.persistData(FileType.STAFF); // Persist changes
                System.out.println("Staff removed successfully.");
                return true;
            } else {
                return false; // Don't print the cancellation message here
            }
        } else {
            System.out.println("Staff not found!");
            return false;
        }
    }

    /**
     * function to return the staff object by searching staff's login id
     *
     * @param hospitalId the targeted staff's login id
     * @return staff object as a list
     */
    public static ArrayList<Staff> searchStaffById(String hospitalId) {
        //create an array list to store staff object
        ArrayList<Staff> searchList = new ArrayList<Staff>();
        //if STAFF hash map contains a key equal to the value stored in the variable name
        if (Repository.STAFF.containsKey(hospitalId)) {
            Staff searchedStaff = Repository.STAFF.get(hospitalId);
            searchList.add(searchedStaff);
        }
        return searchList;
    }

    /**
     * function to update staff name
     *
     * @param hospitalId targeted staff's login id
     * @param name targeted staff's name
     * @param attributeCode the attribute code for the detail that user choose
     * to update
     * @return {@code true} if update staff is successful. Otherwise,
     * {@code false}
     */
    public static boolean updateStaffAccount(String hospitalId, String name, int attributeCode) {
        // Create a list to store Staff objects
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.isEmpty()) {
            // Staff not found
            return false;
        }

        // Loop through staff objects
        for (Staff staff : updateList) {
            Staff staffToUpdate = Repository.STAFF.get(hospitalId);
            if (staffToUpdate != null) {
                if (attributeCode == 1) {
                    staffToUpdate.setName(name);
                }
                Repository.STAFF.put(hospitalId, staffToUpdate);
            }
        }

        Repository.persistData(FileType.STAFF);
        return true;
    }

    /**
     * Overloading method of update staff gender
     * <p>
     * @param hospitalId targeted staff's login id
     * @param attributeCode the attribute code for the detail that user choose
     * to update
     * @param gender targeted staff's gender
     * @return {@code true} if update staff is successful. Otherwise,
     * {@code false}
     */
    public static boolean updateStaffAccount(String hospitalId, int attributeCode, Gender gender) {
        // Create a list to store Staff objects
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.isEmpty()) {
            // Staff not found
            return false;
        }

        // Loop through staff objects
        for (Staff staff : updateList) {
            Staff staffToUpdate = Repository.STAFF.get(hospitalId);
            if (staffToUpdate != null) {
                if (attributeCode == 2) {
                    staffToUpdate.setGender(gender);
                }
                Repository.STAFF.put(hospitalId, staffToUpdate);
            }
        }

        Repository.persistData(FileType.STAFF);
        return true;
    }

    /**
     * Overloading method that update the age of the staff
     * <p>
     * @param hospitalId targeted staff's login id
     * @param attributeCode the attribute code for the detail that user choose
     * to update
     * @param age targeted staff's age
     * @return {@code true} if update staff is successful. Otherwise,
     * {@code false}
     */
    public static boolean updateStaffAccount(String hospitalId, int attributeCode, int age) {
        // Create a list to store Staff objects
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.isEmpty()) {
            // Staff not found
            return false;
        }

        // Loop through staff objects
        for (Staff staff : updateList) {
            Staff staffToUpdate = Repository.STAFF.get(hospitalId);
            if (staffToUpdate != null) {
                if (attributeCode == 3) {
                    staffToUpdate.setAge(age);
                }
                Repository.STAFF.put(hospitalId, staffToUpdate);
            }
        }

        Repository.persistData(FileType.STAFF);
        return true;
    }
}
