package clases;

import java.util.Random;

public class Player {
	private Warrior warrior;
	private Weapon weapon;
	private int currentHP;
	
	public void atack(Player defender) {
		Random rand = new Random();
		
		// Set the atack chance 
		int missChance = rand.nextInt(100)+1;
		if((warrior.getAgility() + weapon.getAgility())*10 >= missChance) {
			System.out.println("El ataque ha acertado");
			
			int dodgeChance = rand.nextInt(50)+1;
			if(defender.getWarrior().getAgility() + defender.getWeapon().getAgility() < dodgeChance) {
				int atckDmg = warrior.getStrength() + weapon.getStrength() 
					- defender.getWarrior().getDefense() - defender.getWeapon().getDefense();
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
		if(warrior.getSpeed() + weapon.getSpeed() > defender.getWarrior().getSpeed() + defender.getWeapon().getSpeed()) {
			Random rand = new Random();
			int chanceSpeed = warrior.getSpeed() + weapon.getSpeed() - defender.getWarrior().getSpeed() - defender.getWeapon().getSpeed();
			if(chanceSpeed >= rand.nextInt()) {
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

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	public int getTotalStrength() {
		int totalStrength = this.getWarrior().getStrength() + this.getWeapon().getStrength();
		return totalStrength;
	}
	
	public int getTotalDefense() {
		int totalDefense = this.getWarrior().getDefense() + this.getWeapon().getDefense();
		return totalDefense;
	}
	
	public int getTotalAgility() {
		int totalAgility = this.getWarrior().getAgility() + this.getWeapon().getAgility();
		return totalAgility;
	}
	
	public int getTotalSpeed() {
		int totalSpeed = this.getWarrior().getSpeed() + this.getWeapon().getSpeed();
		return totalSpeed;
	}
	
}
