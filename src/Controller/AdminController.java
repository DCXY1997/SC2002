package src.Controller;
import java.util.ArrayList;
import java.util.Map;

import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Model.Staff;
import src.Repository.FileType;
import src.Repository.Repository;

public class AdminController {
    public static boolean displayStaffListByRole(StaffType role) {
    	ArrayList<Staff> staffNameList = new ArrayList<Staff>();
    	//can't just iterate through map, need to do modification to loop through, need to import packages for map.entry
        for (Map.Entry<String, Staff> entry : Repository.STAFF.entrySet()) {
        	Staff staff = entry.getValue();
        	if (staff.getRole().equals(role)) {
        		staffNameList.add(staff);
        	}    
        }
        if(staffNameList.size() != 0) {
        	for(Staff staff : staffNameList) {
            	System.out.println(staff.getName());
            }
        	return true;
        }
        return false;
	}

    public static boolean displayStaffListByGender(Gender gender) {
    	ArrayList<Staff> staffNameList = new ArrayList<Staff>();
    	//can't just iterate through map, need to do modification to loop through, need to import packages for map.entry
        for (Map.Entry<String, Staff> entry : Repository.STAFF.entrySet()) {
        	Staff staff = entry.getValue();
        	if (staff.getGender().equals(gender)) {
        		staffNameList.add(staff);
        	}
        }
        if(staffNameList.size() != 0) {
        	for(Staff staff : staffNameList) {
            	System.out.println(staff.getName());
            }
        	return true;
        }
        return false;
	}

    public static boolean displayStaffListByAge(int age) {
    	ArrayList<Staff> staffNameList = new ArrayList<Staff>();
    	//can't just iterate through map, need to do modification to loop through, need to import packages for map.entry
        for (Map.Entry<String, Staff> entry : Repository.STAFF.entrySet()) {
        	Staff staff = entry.getValue();
        	if (staff.getAge() == age) {
        		staffNameList.add(staff);
        	}
        }
        if(staffNameList.size() != 0) {
        	for(Staff staff : staffNameList) {
            	System.out.println(staff.getName());
            }
        	return true;
        }
        return false;
	}

    public static void addStaffAccount(String name, String password, Gender gender, int age, String hospitalId, StaffType role) {
		Staff staff = new Staff(name, password, role, gender, age, hospitalId);
        
        //Update Hash Map
        Repository.STAFF.put(staff.getHospitalId(), staff);
    
        // Persist data to file
        Repository.persistData(FileType.STAFF);

        System.out.println("Staff added successfully!");
	}

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
