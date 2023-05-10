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
	private ArrayList<Warrior> warriors;
	
	public WarriorContainer() {

		warriors = new ArrayList<Warrior>();

		String url = "jdbc:mysql://localhost/raceWar?serverTimezone=UTC";
		String usr = "root";
		String pas = "1234";

		// ArrayList with all the weapons
		ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
		for (Weapon w : new WeaponContainer().getWeapons()) {
			weaponList.add(w);
		}

		// ArrayList to fill each warrior weapons
		ArrayList<Weapon> weaponAvailable = new ArrayList<Weapon>();

		try {
			// Start driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver charged successfully");

			// Create DB connection
			Connection conn = DriverManager.getConnection(url, usr, pas);
			System.out.println("Connection created successfully");

			// Instance the statements objects with the connections
			Statement stmnt1 = conn.createStatement(); // This statement is used to call the warriors query
			Statement stmnt2 = conn.createStatement(); // This Statement is used to call the weapon-available query

			// This Result Set call the query that have all the values for the Warriors class
			ResultSet rs1 = stmnt1.executeQuery("SELECT w.playable, r.race, w.hp, w.strength, w.defense, " +
												"w.agility, w.speed, w.image_path, w.defeatPoints, w.id, " +
												"w.name, w.race_id\n" +
												"FROM warriors w\n" +
												"INNER JOIN races r on w.race_id = r.id;");

			int race = 0; // int keep the actual warrior race

			int position = 0; // int to know the position in ArrayList warriors

			while (rs1.next()) {
				// Keep the race_id of the actual warrior
				race = rs1.getInt(12);

				// Clear the ArrayList weaponAvailable
				weaponAvailable.clear();

				// Create the query to match warrior with his weapons
				ResultSet rs2 = stmnt2.executeQuery("SELECT * FROM weapon_available WHERE race_id = " + race);
				// Iterator used to fill the ArrayList weaponAvailable
				while (rs2.next()) {
					weaponAvailable.add(weaponList.get(rs2.getInt(1) - 1));
				}

				// Instance the new warrior in the ArrayList warriors using the first query(rs1)
				warriors.add(new Warrior(rs1.getBoolean(1), rs1.getString(2),
						rs1.getInt(3), rs1.getInt(4), rs1.getInt(5),
						rs1.getInt(6), rs1.getInt(7), rs1.getString(8),
						rs1.getInt(9), rs1.getInt(10), rs1.getString(11)));

				// Set the weapons list of this warrior then sum one in the variable position
				warriors.get(position).setWeapons(weaponAvailable);
				position ++;
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Driver not charged successfully");
		} catch (SQLException e) {
			System.out.println("Connection not created successfully");
		}

	}

	
	public ArrayList<Warrior> getWarriors() {
		return warriors;
	}

	public Warrior getRandomWarrior() { // Method to get a random warrior from the ArrayList
		Warrior w;

		Random rand = new Random();

		w = warriors.get(rand.nextInt(warriors.size()));

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
