package src.Controller;

import java.util.Scanner;
import src.Enum.*;
import src.Helper.*;
import src.Model.*;
import src.Repository.*;

/**
 * The {@code PharmacistController} class handles operations related to pharmacist authentication,
 * password management, and submission of replenishment requests for inventory items.
 * It interacts with the {@link Repository} to manage data and the {@link Helper} class for user input and utility functions.
 * 
 * <p>This class is primarily used by pharmacists to manage their accounts and ensure sufficient stock levels in the inventory.</p>
 * 
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */

public class PharmacistController {
	
	Scanner sc = new Scanner(System.in);
	
	/**
     * Authenticates a pharmacist's login credentials.
     * 
     * @param id The unique identifier of the pharmacist.
     * @param password The password entered by the pharmacist.
     * @return {@code true} if the credentials are valid, {@code false} otherwise.
     */

	public static boolean authenticate(String id, String password)
	{
		Staff pharmacist = Repository.STAFF.get(id);
		
		if (pharmacist == null)
			return false;
		
		if (!pharmacist.getPassword().equals(password))
			return false;
		
		return true;
	}
	
	 /**
     * Changes the password for the authenticated pharmacist.
     * 
     * @param pharmacist The {@link Staff} object representing the pharmacist.
     * @param password The new password to set.
     * @param confirmPassword The confirmation of the new password.
     * @return {@code true} if the password was successfully changed, {@code false} otherwise.
     */

	public static boolean changePassword(Staff pharmacist, String password, String confirmPassword)
	{
		if (password.equals(confirmPassword))
		{
			pharmacist.setPassword(confirmPassword);
			Repository.persistData(FileType.STAFF);
			return true;
		}
		else
			return false;
	}
	
	/**
     * Submits a replenishment request for a specified medicine.
     * 
     * <p>This method allows pharmacists to request additional stock for medicines that are
     * running low. The request is sent to the admin for approval.</p>
     * 
     * @param medicineId The unique identifier of the medicine to replenish.
     */
	
	public static void submitReplenishmentRequest(String medicineId)
	{
		int opt = -1;
		Scanner sc = new Scanner(System.in);
		InventoryList inventoryItem = Repository.INVENTORY.get(medicineId);
	    if (inventoryItem == null) {
	        System.out.println("Invalid medicine ID. Please check the ID and try again.\n");
			Helper.pressAnyKeyToContinue();
	        return; // Exit the method if the ID is invalid
	    }
	    else if (inventoryItem.getInitialStock() > inventoryItem.getLowStocklevelAlert())
	    {
	    	System.out.println("Notice: The medicine ID is not in low stock.");
	    	do
	    	{	
		    	System.out.println("(1) Continue Submit Replenishment Request");
		        System.out.println("(2) Back");
		        opt = Helper.readInt();
		        if (opt == 2)
		        {
		        	Helper.pressAnyKeyToContinue();
		        	return;
		        }
	    	}while(opt<1 || opt>2);
	    	
	    }
	    
	    // Generate a unique ID for the replenishment request
	    int uniqueId = Helper.generateUniqueId(Repository.INVENTORY);
	    String requestId = String.format("R%03d", uniqueId); 

	    // Prompt for replenishment amount
	    System.out.println("Enter the replenishment amount: ");
	    int amount = Helper.readInt();

	    // Create a new replenishment request
	    ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(requestId, medicineId, amount, InventoryRequestStatus.PENDING);

	    // Add the replenishment request to the repository
	    Repository.REPLENISHMENT_REQUEST.put(replenishmentRequest.getRequestId(), replenishmentRequest);
	    System.out.println("Replenishment request is sent to the admin and pending approval.\n");
		Helper.pressAnyKeyToContinue();
	}
	
}
