package src.Model;

import src.Enum.*;

public class Pharmacist extends Staff{
	private static final long serialVersionUID = 1L;
	
	public Pharmacist()
	{
		super();
		this.role = StaffType.PHARMACIST;
	}
	
	public Pharmacist(String name, String password, Gender gender, int age, String hospitalId)
	{
		super(name, password, StaffType.PHARMACIST, gender, age, hospitalId);
	}	
	
}
