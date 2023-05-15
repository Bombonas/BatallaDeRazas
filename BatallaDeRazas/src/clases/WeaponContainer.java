package clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Queue;

public class WeaponContainer {
    private ArrayList<Weapon> weapons;
    private ArrayList<Weapon> items;
    public WeaponContainer() {

        weapons = new ArrayList<Weapon>();
        items = new ArrayList<Weapon>();

        DataBaseConn conn = new DataBaseConn();
        ResultSet rs = conn.getQueryRS("SELECT * FROM weapons");

        try {
            // Iterate the result set for each row
            while (rs.next()) {

                // add an Instance of each weapon in the arrayList
                weapons.add(new Weapon(rs.getString(2), rs.getInt(4), rs.getInt(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
                        rs.getInt(1), rs.getString(3), 0));
            }
            rs.close();

            rs = conn.getQueryRS("SELECT * FROM items");
            while (rs.next()){
                items.add(new Weapon(rs.getString(2), rs.getInt(4), rs.getInt(5),
                        rs.getInt(6), rs.getInt(7), rs.getInt(8), 0,
                        rs.getInt(1), rs.getString(3), rs.getInt(9)));
            }
        } catch (SQLException e) {
            System.out.println("Error running ResultSet");
        } finally {
            conn.closeConn();
        }


    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<Weapon> getItems(){
        return items;
    }
}
