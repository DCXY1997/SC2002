package src.Model;

import java.util.HashMap;
import java.util.Scanner;

public class Inventory {

	Scanner sc = new Scanner(System.in);
	private HashMap<Medicine, Integer[]> stockLevel;

	public Inventory()
	{
		this.stockLevel = new HashMap<>();
	}
	
	public void addStock(Medicine medicine, int stock)
	{
		if (stockLevel.containsKey(medicine))
		{
			Integer[] stockAlert = stockLevel.get(medicine);
			stockAlert[0] += stock;
			stockLevel.put(medicine, stockAlert);
			System.out.println("Updated stock for "+medicine.getMedicineName()+": "+stockAlert[0]);
		}
		else
		{
			System.out.println("Medicine not found in inventory.");
		}
	}
	
	public int getStock(Medicine medicine)
	{
		
		if (stockLevel.containsKey(medicine))
		{
			return stockLevel.get(medicine)[0];
		}
		else
		{
			System.out.println("Medicine not found in inventory.");
			return -1;
		}
	}
	
	public void setStock(Medicine medicine, int stock)
	{
		if (stockLevel.containsKey(medicine))
		{
			stockLevel.get(medicine)[0] += stock;
		}
		else
		{
			System.out.println("Medicine not found in inventory.");
		}
	}
	
	public void setStockAlert(Medicine medicine, int alert)
	{

		if (stockLevel.containsKey(medicine))
		{
			stockLevel.get(medicine)[1] = alert;
		}
		else
		{
			System.out.println("Medicine not found in inventory.");
		}
	}
	
	public void withdrawStock(Medicine medicine, int stock)
	{
		if (stockLevel.containsKey(medicine))
		{
			stockLevel.get(medicine)[0] -= stock;
		}
		else
		{
			System.out.println("Medicine not found in inventory.");
		}
	}
	
	public String getAllStock()
	{
		StringBuilder sb = new StringBuilder();
		for (Medicine medicine : stockLevel.keySet())
		{
			Integer[] stockAlert = stockLevel.get(medicine);
			sb.append("Medicine: ").append(medicine.getMedicineName())
			.append(" | Stock: ").append(stockAlert[0])
			.append(" | Alert Threshold: ").append(stockAlert[1])
			.append("\n");
			
		}
		return sb.toString();
	}
	
	public String getLowStock()
	{
		StringBuilder sb = new StringBuilder();
		for (Medicine medicine : stockLevel.keySet())
		{
			Integer[] stockAlert = stockLevel.get(medicine);
			if (stockAlert[0] < stockAlert[1])
			{
				sb.append("Medicine: ").append(medicine.getMedicineName())
				.append(" | Stock: ").append(stockAlert[0])
				.append(" | Alert Threshold: ").append(stockAlert[1])
				.append("\n");
			}
		}
		if(sb.length() == 0)
			sb.append("All medicines are above the alert threshold.");
		return sb.toString();
	}
	
	public int submitReplenishmentRequest()
	{
		System.out.println("Enter the replenishment amount: ");
		return (sc.nextInt());
	}
}
