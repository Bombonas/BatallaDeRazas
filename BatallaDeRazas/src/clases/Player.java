package clases;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String name;
	private Warrior warrior;
	private Weapon weapon;
	private int currentHP;
	private ArrayList<Weapon> items;
	
	public Player(Warrior w, String name) {
        items = new ArrayList<Weapon>();
		Random rand = new Random();
        this.name = name;
		warrior = w;
		weapon = null;
		currentHP = w.getHp();
        items = new ArrayList<Weapon>();
	}
	
	public String atack(Player defender) {
		Random rand = new Random();
		String ret = name + "'S TURN\n";
		// Set the atack chance 
		int missChance = rand.nextInt(100)+1;
		if(getTotalAgility()*10 >= missChance) {
			ret += "Successful Attack";
            int dodgeChance = rand.nextInt(50)+1;
            if(defender.getTotalAgility()/2 < dodgeChance) {
                int atckDmg = getTotalStrength() - defender.getTotalDefense();
                ret += "\n"+defender.getName() + " has suffered " + atckDmg + " damage points";
                if(defender.getCurrentHP()-atckDmg < 0) {
                    defender.setCurrentHP(0);
                }else {
                    defender.setCurrentHP(defender.getCurrentHP()-atckDmg);
                }

            }else ret += "\n"+defender.getName() + " has dodged the attack";
			
		}else  ret += name + " missed attack";

        return ret + "\n\n";
	}
	
	public boolean swapTurn(Player defender) {
		// 
		boolean ret = true;
		if(getTotalSpeed() > defender.getTotalSpeed()) {
			Random rand = new Random();
			int chanceSpeed = (getTotalSpeed() - defender.getTotalSpeed()) * 10;
			if(chanceSpeed >= rand.nextInt(100)+1) {
				ret = false;
			}
		}
		return ret;
	}
	

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setWeapon() { // Method to set a random weapon
        Random rand = new Random();
        weapon = warrior.getWeapons().get(rand.nextInt(warrior.getWeapons().size()));
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
	
	public void setItem(Weapon item) {
		items.add(item);
	}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<Weapon> getItems(){ return items;}

    public String toString() { return name + ": " + warrior.getName() + "\\" + warrior.getRace() + " - " + weapon.getName(); }
	
	public int getTotalStrength() {
        int totalStrength = this.getWarrior().getStrength() + this.getWeapon().getStrength();
        for(Weapon item: items) {
        	totalStrength += item.getStrength();
        }
        return totalStrength;
    }
    
    public int getTotalDefense() {
        int totalDefense = this.getWarrior().getDefense() + this.getWeapon().getDefense();
        for(Weapon item: items) {
        	totalDefense += item.getDefense();
        }
        return totalDefense;
    }
    
    public int getTotalAgility() {
        int totalAgility = this.getWarrior().getAgility() + this.getWeapon().getAgility();
        for(Weapon item: items) {
        	totalAgility += item.getAgility();
        }
        return totalAgility;
    }
    
    public int getTotalSpeed() {
        int totalSpeed = this.getWarrior().getSpeed() + this.getWeapon().getSpeed();
        for(Weapon item: items) {
        	totalSpeed += item.getSpeed();
        }
        return totalSpeed;
    }

    public int getTotalHP() {
        int totalHP = this.getWarrior().getHp() + this.getWeapon().getHp();
        for(Weapon item: items) {
            totalHP += item.getHp();
        }
        return totalHP;
    }

}
