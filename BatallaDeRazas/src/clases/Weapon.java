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
    private int quality;
	private String url;
	
	 public Weapon(String name, int hp, int strength, int defense, int agility, int speed, int defeatPoints,
	            int idWeapon, String url, int quality) {
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
            this.quality = quality;
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
    public int getStrength() {
        return strength;
    }
    public int getDefense() {
        return defense;
    }
    public int getAgility() {
        return agility;
    }
    public int getSpeed() {
        return speed;
    }
    public int getDefeatPoints() {
        return defeatPoints;
    }
    public int getIdWeapon() {
        return idWeapon;
    }
    public String getUrl() {
        return url;
    }
    public int getQuality(){ return quality;}
    public String toString() {
         return name+ " " +quality;
    }
	
}
