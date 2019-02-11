package Minesweeper.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

    public static DB DBInstance = null;
    private Connection connection;
    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/Minesweeper";
    private static final String username = "postgres";
    private static final String password = "postgres";
    public static final String createTableGames=" CREATE TABLE Games(id Serial PRIMARY KEY, gameMode varchar(20));";
    public static final String createTablePlayerGame="CREATE TABLE PlayerGame(idPlayer int references Players(id), idGame int references Games(id),PRIMARY KEY(idPlayer,idGame));";
     public static final String createTablePlayers="CREATE TABLE Players(id Serial PRIMARY KEY,username varchar(20),gameswon int,gameslost int,password text);";
    public DB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Singleton Pattern
     * @return 
     */
    public static DB getDBInstance() {
        if (DBInstance == null) {
            DBInstance = new DB();
            Logger.getLogger(DB.class.getName()).log(Level.INFO, "New Connection");
            return DBInstance;
        }
        Logger.getLogger(DB.class.getName()).log(Level.INFO, "Reuse Connection");
        return DBInstance;
    }
    /**
     * This method allows to execute a query and return a statement
     * @param query
     * @return 
     */
    public Statement executeQueryStatement(String query){
        try {
            this.connection.setAutoCommit(false);
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            this.connection.commit();
            return statement;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * This method allows to execute a query and return a Result
     * @param query
     * @return 
     */
    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            this.connection.setAutoCommit(false);
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            rs = statement.getResultSet();
            this.connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
