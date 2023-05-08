package clases;

import java.util.ArrayList;

public class WarriorContainer {
	private ArrayList<Warrior> warriors;
	
	public WarriorContainer() {
		ArrayList<Weapon> weaponList = new WeaponContainer().getWeapons();
		warriors = new ArrayList<Warrior>();
		
		warriors.add(new Warrior("Dwarf", 60, 6, 4, 5, 3, weaponList,
			"aquiEstaLaImagen", 20, 1));
		
		warriors.add(new Warrior("Human", 50, 5, 3, 6, 5, weaponList,
				"aquiEstaLaImagen", 20, 2));
		
		warriors.add(new Warrior("Elf", 40, 4, 2, 7, 7, weaponList,
				"aquiEstaLaImagen", 20, 3));
	}

	
	public ArrayList<Warrior> getWarriors() {
		return warriors;
	}
	

}
