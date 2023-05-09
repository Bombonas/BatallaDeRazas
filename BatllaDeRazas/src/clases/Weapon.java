package clases;

public class Weapon {
	private String name;
	private int hp;
	private int strength;
	private int defense;
	private int agility;
	private int speed;
	private int defeatPoints;
	private int idWeapon;
	private String url;
	
	 public Weapon(String name, int hp, int strength, int defense, int agility, int speed, int defeatPoints,
	            int idWeapon, String url) {
	        super();
	        this.name = name;
	        this.hp = hp;
	        this.strength = strength;
	        this.defense = defense;
	        this.agility = agility;
	        this.speed = speed;
	        this.defeatPoints = defeatPoints;
	        this.idWeapon = idWeapon;
            this.url = url;
	}
	
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getAgility() {
        return agility;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getDefeatPoints() {
        return defeatPoints;
    }
    public void setDefeatPoints(int defeatPoints) {
        this.defeatPoints = defeatPoints;
    }
    public int getIdWeapon() {
        return idWeapon;
    }
    public void setIdWeapon(int idWeapon) {
        this.idWeapon = idWeapon;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
         return name;
    }
	
}
