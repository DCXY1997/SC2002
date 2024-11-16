package src.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.Enum.AppointmentStatus;
import src.Enum.Gender;
import src.Enum.StaffType;
import src.Helper.Helper;
import src.Model.Appointment;
import src.Model.AppointmentList;
import src.Model.InventoryList;
import src.Model.Medicine;
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

        System.out.println("Staff added successfully! ID: " + hospitalId);
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

    public static String getAppointmentDetails(String appointmentId) {
        // Load appointments from the Repository (to ensure the latest data is used)
        Repository.readData(FileType.APPOINTMENT_LIST);
    
        // Search for the appointment in the APPOINTMENT_LIST map
        Appointment appointment = Repository.APPOINTMENT_LIST.get(appointmentId);
    
        // If found, format and return the details
        if (appointment != null) {
            return formatAppointmentDetails(appointment);
        }
    
        // If not found, return a "not found" message
        return "Appointment with ID " + appointmentId + " not found.";
    }
    
    // Helper function to format appointment details
    private static String formatAppointmentDetails(Appointment appointment) {
        return String.format(
            "Appointment ID: %s\nPatient Name: %s\nDoctor Name: %s\nStart Time: %s\nEnd Time: %s\nStatus: %s",
            appointment.getAppointmentId(),
            appointment.getPatient().getName(),  // Get the patient's name
            appointment.getAttendingDoctor().getName(),  // Get the doctor's name
            appointment.getAppointmentStartDate(),
            appointment.getAppointmentEndDate(),
            appointment.getStatus()
        );
    }

    public static List<String> getAllAppointmentIds() {
        // Load appointments from the Repository
        Repository.readData(FileType.APPOINTMENT_LIST);
    
        // Collect all appointment IDs
        return new ArrayList<>(Repository.APPOINTMENT_LIST.keySet());
    }
        
    public static String getInventoryRecord() {
        StringBuilder inventoryDetails = new StringBuilder();
        
        if (Repository.INVENTORY.isEmpty()) {
            return "No inventory records found.";
        }
        
        inventoryDetails.append("Medical Inventory:\n");
        inventoryDetails.append("------------------------------------------------------------\n");
        
        for (Map.Entry<String, InventoryList> entry : Repository.INVENTORY.entrySet()) {
            InventoryList inventoryItem = entry.getValue();
            Medicine medicine = inventoryItem.getMedicine();
               
            inventoryDetails.append("Medicine ID: ").append(medicine.getMedicineId()).append("\n");
            inventoryDetails.append("Medicine Name: ").append(medicine.getMedicineName()).append("\n");
            inventoryDetails.append("Medicine Price: $").append(medicine.getMedicinePrice()).append("\n");
            inventoryDetails.append("Medicine Description: ").append(medicine.getMedicineDescription()).append("\n");
            inventoryDetails.append("Initial Stock: ").append(inventoryItem.getInitialStock()).append("\n");
            inventoryDetails.append("Low Stock Level Alert: ").append(inventoryItem.getLowStocklevelAlert()).append("\n");
            inventoryDetails.append("------------------------------------------------------------\n");
        }
        
        return inventoryDetails.toString();
    }

    public boolean approveReplenishmentRequests(String medicalId, int stockCount){
        // Check if the specified medicalId exists in INVENTORY
        if (!Repository.INVENTORY.containsKey(medicalId)) {
            System.out.println("Medical ID not found in inventory.");
            return false;
        }

        // Retrieve the InventoryList item associated with the medicalId
        InventoryList inventoryItem = Repository.INVENTORY.get(medicalId);

        // Check if the initialStock is lower than the lowStockLevelAlert
    if (inventoryItem.getInitialStock() < inventoryItem.getLowStocklevelAlert()) {
        // Update the stock level by adding stockCount to the current initialStock
        int updatedStock = inventoryItem.getInitialStock() + stockCount;
        inventoryItem.setInitialStock(updatedStock);

        // Update the INVENTORY map in the repository
        Repository.INVENTORY.put(medicalId, inventoryItem);
        
        // Persist the changes to the data file
        Repository.persistData(FileType.INVENTORY);

        System.out.println("Stock level updated successfully for Medicine ID: " + medicalId);
        return true;
        } else {
        System.out.println("Replenishment not needed: Stock level is above alert threshold.");
        return false;
    }
}

}
