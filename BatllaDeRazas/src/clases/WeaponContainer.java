package clases;

import java.sql.*;
import java.util.ArrayList;

public class WeaponContainer {
    private ArrayList<Weapon> weapons;

    public WeaponContainer() {

        weapons = new ArrayList<Weapon>();

        String url = "jdbc:mysql://localhost/raceWar?serverTimezone=UTC";
        String usr = "root";
        String pas = "1234";

        try {
            // Start driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver charged successfully");

            // Create DB connection
            Connection conn = DriverManager.getConnection(url, usr, pas);
            System.out.println("Connection created successfully");

            // Instance the statement object with the connection
            Statement stmnt = conn.createStatement();

            // Instance the Result Set with a query that fetch all the weapons
            ResultSet rs = stmnt.executeQuery("SELECT * FROM weapons");

            // Iterate the result set for each row
            while (rs.next()) {

                // add an Instance of each weapon in the arrayList
                weapons.add(new Weapon(rs.getString(2), rs.getInt(4), rs.getInt(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
                        rs.getInt(1), rs.getString(3)));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not charged successfully");
        } catch (SQLException e) {
            System.out.println("Connection not created successfully");
        }
    }
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
}
