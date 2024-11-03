package src.Model;

import java.io.Serializable;
import src.Enum.*;

public class Pharmacist extends Staff implements Serializable{
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
