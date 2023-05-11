package clases;

import javax.net.ssl.SSLException;
import java.sql.*;

public class DataBaseConn {

    private Connection conn;
    private Statement stmnt;

    public DataBaseConn() {
        String url = "jdbc:mysql://localhost/raceWar?serverTimezone=UTC";
        String usr = "root";
        String pas = "1234";

        try {
            // Start driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver charged successfully");

            // Create DB connection
            conn = DriverManager.getConnection(url, usr, pas);
            System.out.println("Connection created successfully");

            // Instance the statement object with the connection
            stmnt = conn.createStatement();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not charged successfully");
        } catch (SQLException e) {
            System.out.println("Error in connection");
        }

    }

    public ResultSet getQueryRS(String query) {

        ResultSet rs = null;
        try {
            rs = stmnt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error in query execution");
        }

        return rs;
    }

    public void insertData(String update) {

        try {
            stmnt.executeUpdate(update);
            System.out.println("Update has occurred");
        } catch (SQLException e) {
            System.out.println("Error in update");
        }

    }

    public void closeConn() {

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in close");
        }

    }

}
