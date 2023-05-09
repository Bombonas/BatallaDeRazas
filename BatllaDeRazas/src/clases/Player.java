package clases;

import java.util.Random;

public class Player {
	private Warrior warrior;
	private Weapon weapon;
	private int currentHP;
	
	public Player(Warrior w) {
		Random rand = new Random();
		warrior = w;
		weapon = w.getWeapons().get(rand.nextInt(w.getWeapons().size()));
		currentHP = w.getHp();
	}
	
	public void atack(Player defender) {
        Random rand = new Random();
        
        // Set the atack chance 
        int missChance = rand.nextInt(100)+1;
        System.out.println(missChance);
        if((warrior.getAgility() + weapon.getAgility())*10 > missChance) {
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

    
    public void swapTurn() {
        
    }
	
}