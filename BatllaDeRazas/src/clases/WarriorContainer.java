package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WarriorContainer {
	private ArrayList<Warrior> warriors;
	
	public WarriorContainer() {

		// Total de armas
		ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
		for (Weapon w : new WeaponContainer().getWeapons()) {
			weaponList.add(w);
		}

		// Armas de cada jugador
		ArrayList<Weapon> weaponAvailable = new ArrayList<Weapon>();

		warriors = new ArrayList<Warrior>();

		String url = "jdbc:mysql://localhost/raceWar?serverTimezone=UTC";
		String usr = "root";
		String pas = "1234";

		String update = "";
		String query = "";
		String data = "";

		try {
			// Ejercicio A
			// Cargar driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver cargado correctamente");

			// Crear conexion DDBB
			Connection conn = DriverManager.getConnection(url, usr, pas);
			System.out.println("Conexion creada correctamente");

			// Instanciar objeto de la clase Statement
			Statement stmnt1 = conn.createStatement();
			Statement stmnt2 = conn.createStatement();

			// Iniciamos un resultSet para cojer los warriors
			ResultSet rs1 = stmnt1.executeQuery("SELECT w.playable, r.race, w.hp, w.strength, w.defense, " +
												"w.agility, w.speed, w.image_path, w.defeatPoints, w.id, " +
												"w.name, w.race_id\n" +
												"FROM warriors w\n" +
												"INNER JOIN races r on w.race_id = r.id;");

			int race = 0; // int para saber la raza del warrior

			while (rs1.next()) {
				// Guardamos el id de raza del luchador
				race = rs1.getInt(12);

				// Limpiamos la lista de armas
				weaponAvailable.clear();

				// Creamos una consulta para comprobar las armas disponibles de cada luchador
				ResultSet rs2 = stmnt2.executeQuery("SELECT * FROM weapon_available WHERE race_id = " + race);
				// Iteramos sobre rs2 para sacar la lsita de armas disponibles
				while (rs2.next()) {
					weaponAvailable.add(weaponList.get(rs2.getInt(1) - 1));
				}

				warriors.add(new Warrior(rs1.getBoolean(1), rs1.getString(2),
						rs1.getInt(3), rs1.getInt(4), rs1.getInt(5),
						rs1.getInt(6), rs1.getInt(7), weaponAvailable, rs1.getString(8),
						rs1.getInt(9), rs1.getInt(10), rs1.getString(11)));
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Driver no cargado correctamente");
		} catch (SQLException e) {
			System.out.println("Conexion no creada correctamente");
		}

	}

	
	public ArrayList<Warrior> getWarriors() {
		return warriors;
	}

	public String toString() {
		String data = "";

		for (Warrior w : warriors) {
			data += w + "\n";
		}

		return data;
	}

}
