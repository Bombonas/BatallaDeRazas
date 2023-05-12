package clases;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundsInfo {

    private int idPlayer, idOpponent, idOpponentWeapon, idBattle, idRound,
                injuriesCaused, injuriesSuffered;

    public RoundsInfo(Player cpu, Player usr) {
        idOpponent = cpu.getWarrior().getIdWarrior();
        idOpponentWeapon = cpu.getWeapon().getIdWeapon();

        idRound = 1;
        injuriesCaused = 0;
        injuriesSuffered = 0;

        DataBaseConn conn = new DataBaseConn();

        ResultSet rs = conn.getQueryRS("SELECT count(id) FROM players;");

        try {
            rs.next();

            idBattle = rs.getInt(1) + 1;
            idPlayer = rs.getInt(1) + 1;

            conn.insertData("INSERT INTO players (name, warrior_id, weapon_id) " +
                                   "VALUES ('" + usr.getName() + "', " + usr.getWarrior().getIdWarrior() +", " +
                                                 usr.getWeapon().getIdWeapon() + ");");

            conn.insertData("INSERT INTO battles (player_id) VALUES (" + idPlayer + ");");

            conn.closeConn();

        } catch(SQLException e) {
            System.out.println("Error executing query");
        }

    }

    public void setInjuriesCaused(int injuries) { injuriesCaused = injuries; }

    public void setInjuriesSuffered(int injuries) { injuriesSuffered = injuries; }

    public void setIdOpponent(int idOpponent) { this.idOpponent = idOpponent; }

    public void setIdOpponentWeapon(int idOpponentWeapon) { this.idOpponentWeapon = idOpponentWeapon; }

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
