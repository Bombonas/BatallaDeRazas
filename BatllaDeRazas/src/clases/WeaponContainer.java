package clases;

import java.util.ArrayList;

public class WeaponContainer {
	private ArrayList<Weapon> weapons;
	 
	public WeaponContainer() {
		
		weapons = new ArrayList<Weapon>();
		Weapon daga = new Weapon("Daga", 1, 1, 1, 1, 3, 10, 1);
		Weapon sword = new Weapon("Sword", 1, 2, 1, 1, 2, 10, 1);
		Weapon scimitar = new Weapon("Scimitar", 1, 1, 1, 1, 3, 14, 1);
		
		weapons.add(daga);
		weapons.add(sword);
		weapons.add(scimitar);
	}
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
}
