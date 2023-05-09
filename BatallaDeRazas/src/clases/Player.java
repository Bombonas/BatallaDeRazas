package clases;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	private Warrior warrior;
	private Weapon weapon;
	private int currentHP;
	private ArrayList<Weapon> items;
	
	public void atack(Player defender) {
		Random rand = new Random();
		
		// Set the atack chance 
		int missChance = rand.nextInt(100)+1;
		if(getTotalAgility()*10 >= missChance) {
			System.out.println("El ataque ha acertado");
			
			int dodgeChance = rand.nextInt(50)+1;
			if(defender.getTotalAgility() < dodgeChance) {
				int atckDmg = getTotalStrength() - defender.getTotalDefense();
				System.out.println("El defensor se ha comido " + atckDmg + " de daño vaya subnormal");
				if(defender.getCurrentHP()-atckDmg < 0) {
					defender.setCurrentHP(0);
				}else {
					defender.setCurrentHP(defender.getCurrentHP()-atckDmg);
				}
				
			}else System.out.println("El defensor ha esquivado el ataque");
			
		}else System.out.println("El ataque ha fallado");

	}
	
	public boolean swapTurn(Player defender) {
		// 
		boolean ret = true;
		if(getTotalSpeed() > defender.getTotalSpeed()) {
			Random rand = new Random();
			int chanceSpeed = getTotalSpeed() + defender.getTotalSpeed();
			if(chanceSpeed >= rand.nextInt(100)+1) {
				ret = false;
			}
		}
		return ret;
	}

	public void setItem(Weapon item) {
		items.add(item);
	}
	
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

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	
}
