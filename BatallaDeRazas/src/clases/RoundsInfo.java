package clases;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundsInfo {

    private int idPlayer, idOpponent, idOpponentWeapon, idBattle, idRound,
                injuriesCaused, injuriesSuffered;

    // This constructor save the required info of cpu and usr, and insert info into the tables battles and players
    public RoundsInfo(Player cpu, Player usr) {
        idOpponent = cpu.getWarrior().getIdWarrior();
        idOpponentWeapon = cpu.getWeapon().getIdWeapon();

        idRound = 1;
        injuriesCaused = 0;
        injuriesSuffered = 0;

        DataBaseConn conn = new DataBaseConn();

        // The players ID  is the same of the battle ID with this select we know the last ID battle
        ResultSet rs = conn.getQueryRS("SELECT count(id) FROM players;");

        try {
            rs.next();

            // Save the actual ID battle summing one to the last player ID
            idBattle = rs.getInt(1) + 1;
            idPlayer = rs.getInt(1) + 1;

            // Make an insert into players with the battle info
            conn.insertData("INSERT INTO players (name, warrior_id, weapon_id) " +
                                   "VALUES ('" + usr.getName() + "', " + usr.getWarrior().getIdWarrior() +", " +
                                                 usr.getWeapon().getIdWeapon() + ");");

            // Make an insert into battles with the battle info
            conn.insertData("INSERT INTO battles (player_id) VALUES (" + idPlayer + ");");

            conn.closeConn();

        } catch(SQLException e) {
            System.out.println("Error in RoundsInfo constructor");
        }

    }

    public void setInjuriesCaused(int injuries) { injuriesCaused = injuries; }

    public void setInjuriesSuffered(int injuries) { injuriesSuffered = injuries; }

    public void setIdOpponent(int idOpponent) { this.idOpponent = idOpponent; }

    public void setIdOpponentWeapon(int idOpponentWeapon) { this.idOpponentWeapon = idOpponentWeapon; }

    // The method updateData do an insert in the database with the round values if the player has lost
    public void updateData(int battlePoints) {

        DataBaseConn conn = new DataBaseConn();

        conn.insertData("INSERT INTO rounds " +
                "(id, battle_id, opponent_id, opponent_weapon_id, injuries_caused, injuries_suffered, battle_points) " +
                "VALUES (" + idRound + ", " + idBattle + ", " + idOpponent + ", " + idOpponentWeapon + ", " +
                injuriesCaused + ", " + injuriesSuffered + ", " + battlePoints + ");");

        idRound ++;

        injuriesSuffered = 0;
        injuriesCaused =0;

        conn.closeConn();

    }

    // The method updateData do an insert in the database with the round values if the player has won
    public void updateData() {

        DataBaseConn conn = new DataBaseConn();

        conn.insertData("INSERT INTO rounds " +
                "(id, battle_id, opponent_id, opponent_weapon_id, injuries_caused, injuries_suffered, battle_points) " +
                "VALUES (" + idRound + ", " + idBattle + ", " + idOpponent + ", " + idOpponentWeapon + ", " +
                injuriesCaused + ", " + injuriesSuffered + ", 0);");

        idRound ++;

        injuriesSuffered = 0;
        injuriesCaused =0;

        conn.closeConn();

    }
}
