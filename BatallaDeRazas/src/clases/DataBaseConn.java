package clases;

import javax.net.ssl.SSLException;
import java.sql.*;

public class DataBaseConn {

    private Connection conn;
    private Statement stmnt;
    private ResultSet rs;

    // The DataBaseConn constructor open a connection with the database
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

            // Start the ResultSet
            rs = null;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not charged successfully");
        } catch (SQLException e) {
            System.out.println("Error in connection");
        }

    }

    // The method getQueryRS returns a result set with the query passed as a parameter
    public ResultSet getQueryRS(String query) {

        try {
            rs = stmnt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error in query execution");
        }

        return rs;
    }

    // The method insertData does an insert with the update string passed as a parameter
    public void insertData(String update) {

        try {
            stmnt.executeUpdate(update);
            System.out.println("Update has occurred");
        } catch (SQLException e) {
            System.out.println("Error in update");
        }

    }

    // The method closeConn close the result set if its running, the statement and the connection
    public void closeConn() {

        try {
            if (rs != null) rs.close();
            stmnt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in close");
        }

    }

}
