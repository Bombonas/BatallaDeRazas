package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class WarriorContainer {
	private ArrayList<Warrior> warriors, bosses;
	private ArrayList<Weapon> items;
	
	public WarriorContainer() {
		warriors = new ArrayList<Warrior>();
		bosses = new ArrayList<Warrior>();
		items = new ArrayList<Weapon>();

		// ArrayList with all the weapons
		ArrayList<Weapon> weaponList = new ArrayList<Weapon>();

		WeaponContainer wc = new WeaponContainer();
		for (Weapon w : wc.getWeapons()) {
			weaponList.add(w);
		}

		for(Weapon w : wc.getItems()){
			items.add(w);
		}


		// ArrayList to fill each warrior weapons
		ArrayList<Weapon> weaponAvailable = new ArrayList<Weapon>();

		try {
			DataBaseConn conn1 = new DataBaseConn(); // This connection is used to call the warriors query
			DataBaseConn conn2 = new DataBaseConn(); // This connection is used to call the weaponAvailable query

			// This Result Set call the query that have all the values for the Warriors class
			ResultSet rs1 = conn1.getQueryRS("SELECT w.playable, r.race, w.hp, w.strength, w.defense, " +
												"w.agility, w.speed, w.idle_animation, w.attack_animation, " +
												"w.death_animation, w.defeatPoints, w.id, " +
												"w.name, w.race_id\n" +
												"FROM warriors w\n" +
												"INNER JOIN races r on w.race_id = r.id;");

			int race; // int keep the actual warrior race

			int warriorPosition = 0, bossPosition = 0; // int to know the position in ArrayList warriors

			while (rs1.next()) {
				// Keep the race_id of the actual warrior
				race = rs1.getInt(14);

				// Clear the ArrayList weaponAvailable
				weaponAvailable.clear();

				// Create the query to match warrior with his weapons
				ResultSet rs2 = conn2.getQueryRS("SELECT * FROM weapon_available WHERE race_id = " + race);
				// Iterator used to fill the ArrayList weaponAvailable
				while (rs2.next()) {
					weaponAvailable.add(weaponList.get(rs2.getInt(1) - 1));
				}

				if(race != 4) {
					// Instance the new warrior in the ArrayList warriors using the first query(rs1)
					warriors.add(new Warrior(rs1.getBoolean(1), rs1.getString(2),
							rs1.getInt(3), rs1.getInt(4), rs1.getInt(5),
							rs1.getInt(6), rs1.getInt(7), rs1.getString(8),
							rs1.getString(9), rs1.getString(10), rs1.getInt(11),
							rs1.getInt(12), rs1.getString(13)));

					// Set the weapons list of this warrior then sum one in the variable position
					warriors.get(warriorPosition).setWeapons(weaponAvailable);
					warriorPosition++;
				}else{
					bosses.add(new Warrior(rs1.getBoolean(1), rs1.getString(2),
							rs1.getInt(3), rs1.getInt(4), rs1.getInt(5),
							rs1.getInt(6), rs1.getInt(7), rs1.getString(8),
							rs1.getString(9), rs1.getString(10), rs1.getInt(11),
							rs1.getInt(12), rs1.getString(13)));

					// Set the weapons list of this warrior then sum one in the variable position
					bosses.get(bossPosition).setWeapons(weaponAvailable);
					bossPosition++;
				}
			}

			conn1.closeConn();
			conn2.closeConn();

		} catch (SQLException e) {
			System.out.println("Error executing ResultSet");
		}
	}

	
	public ArrayList<Warrior> getWarriors() {
		return warriors;
	}

	public ArrayList<Warrior> getBosses(){
		return bosses;
	}

	public Warrior getRandomWarrior() { // Method to get a random warrior from the ArrayList
		Warrior w;
		Random rand = new Random();
		w = warriors.get(rand.nextInt(warriors.size()));
		return w;
	}

	public Warrior getRandomBoss() { // Method to get a random warrior from the ArrayList
		Warrior w;
		Random rand = new Random();
		while(true){
			w = bosses.get(rand.nextInt(bosses.size()));
			if(!w.getName().equals("finalBoss")) break;
		}
		return w;
	}

	public Warrior getTheFinalBoss(){// Method to get the final boss
		Warrior w;
		Random rand = new Random();
		while(true){
			w = bosses.get(rand.nextInt(bosses.size()));
			if(w.getName().equals("finalBoss")) break;
		}
		return w;
	}

	public Weapon getRandomItem(int quality){// Method to get a random item
		Random rand = new Random();
		Weapon w;

		while(true){
			w = items.get(rand.nextInt(items.size()));
			if(w.getQuality() == quality) break;
		}
		return w;
	}

	public String toString() {
		String data = "";

		for (Warrior w : warriors) {
			data += w + "\n";
		}

		return data;
	}

}
