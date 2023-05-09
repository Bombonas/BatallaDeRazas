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
            Statement stmnt = conn.createStatement();

            // Iniciamos un resultSet
            ResultSet rs = stmnt.executeQuery("SELECT * FROM weapons");

            while (rs.next()) {
                weapons.add(new Weapon(rs.getString(2), rs.getInt(4), rs.getInt(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
                        rs.getInt(1), rs.getString(3)));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver no cargado correctamente");
        } catch (SQLException e) {
            System.out.println("Conexion no creada correctamente");
        }
    }
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
}
