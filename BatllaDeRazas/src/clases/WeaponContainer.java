package clases;

import java.util.ArrayList;

public class WeaponContainer {
    private ArrayList<Weapon> weapons;

    public WeaponContainer() {

        weapons = new ArrayList<Weapon>();
        Weapon daga = new Weapon("Daga", 0, 0, 0, 0, 3, 10, 1);
        Weapon sword = new Weapon("Sword", 0, 1, 0, 0, 1, 10, 1);
        Weapon scimitar = new Weapon("Scimitar", 0, 0, 0, 0, 2, 14, 1);

        weapons.add(daga);
        weapons.add(sword);
        weapons.add(scimitar);
    }
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
}
