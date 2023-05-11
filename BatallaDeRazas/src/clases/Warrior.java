package clases;

import java.util.ArrayList;

public class Warrior {
	private boolean playable;
	private String name;
	private String race;
	private int hp;
	private int strength;
	private int defense;
	private int agility;
	private int speed;
	private ArrayList<Weapon> weapons;
	private String urlIdle;
	private String urlAttack;
	private String urlDeath;
	private int defeatPoints;
	private int idWarrior;
	
	public Warrior(boolean playable, String race, int hp, int strength, int defense, int agility, int speed,
			String urlIdle, String urlAttack, String urlDeath, int defeatPoints, int idWarrior, String name) {
		super();
		weapons = new ArrayList<Weapon>();
		this.name = name;
		this.playable = playable;
		this.race = race;
		this.hp = hp;
		this.strength = strength;
		this.defense = defense;
		this.agility = agility;
		this.speed = speed;
		this.urlIdle = urlIdle;
		this.urlAttack = urlAttack;
		this.urlDeath = urlDeath;
		this.defeatPoints = defeatPoints;
		this.idWarrior = idWarrior;
	}

	//Seters and Geters
	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
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

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		for (Weapon w : weapons) { this.weapons.add(w); }
	}

	public String getUrlIdle() {
		return urlIdle;
	}

	public String getUrlAttack() {
		return urlAttack;
	}

	public String getUrlDeath() {
		return urlDeath;
	}

	public int getDefeatPoints() {
		return defeatPoints;
	}

	public void setDefeatPoints(int defeatPoints) {
		this.defeatPoints = defeatPoints;
	}

	public int getIdWarrior() {
		return idWarrior;
	}

	public void setIdWarrior(int idWarrior) {
		this.idWarrior = idWarrior;
	}

	public String getName() { return name; }

	public String toString() {

		return name + ":\nWeapons: " + weapons +
					  "\nRace: " + race +
					  "\nHealth Points: " + hp +
					  "\nStrength: " + strength +
					  "\nDefense: " + defense +
					  "\nAgility: " + agility +
					  "\nSpeed: " + speed +
					  "\nDefeat Points: " + defeatPoints +
					  "\nID: " + idWarrior +
					  "\nURL's: " + urlIdle + " - " + urlAttack + " - " + urlDeath;
	}
}
