package src.Controller;

import java.util.Scanner;
import src.Enum.*;
import src.Helper.*;
import src.Model.*;
import src.Repository.*;

public class PharmacistController {
	
	Scanner sc = new Scanner(System.in);
	
	public static boolean authenticate(String id, String password)
	{
		Staff pharmacist = Repository.STAFF.get(id);
		
		if (pharmacist == null)
			return false;
		
		if (!pharmacist.getPassword().equals(password))
			return false;
		
		return true;
	}
	
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
