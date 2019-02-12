package Minesweeper.server;

import Minesweeper.client.ObserverLobbyRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author filipe
 */
public class MinesweeperFactoryImpl extends UnicastRemoteObject implements  MinesweeperFactoryRI {

    private final HashMap<Player, LobbySessionRI> lobbySessionRIList;

    private final HashMap<Timestamp, SubjectGameRI> games;

    private static DB db;

    private String message;
    

    public MinesweeperFactoryImpl() throws RemoteException {
        super();
        this.lobbySessionRIList = new HashMap<>();
        this.games = new HashMap<>();
    }

    @Override
    public LobbySessionRI login(String username, String password, ObserverLobbyRI obs) throws RemoteException {
        for (Player p : this.lobbySessionRIList.keySet()) {
            if (p.getUsername().compareTo(username) == 0) {
                return null;
            }
        }
        Player p = null;
        String hashed = null;
        ResultSet rs = null;
        if (exists(username)) {
            String query = "SELECT * from Players where username like '" + username + "'";
            rs = db.getDBInstance().executeQuery(query);
            try {
                rs.next();
            } catch (SQLException ex) {
                Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                hashed = rs.getString("password");
            } catch (SQLException ex) {
                Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!BCrypt.checkpw(password, hashed)) {
                return null;
            }
            try {
                p = new Player(rs.getInt("id"), username, rs.getInt("gameswon"), rs.getInt("gameslost"));
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            LobbySessionRI lobbySessionRI = (LobbySessionRI) new LobbySessionImpl(this, p,obs);
            this.lobbySessionRIList.put(p, lobbySessionRI);
            obs.setLobbySessionRI(lobbySessionRI);
            notifyAllObserversToLoggedPlayers();
            return lobbySessionRI;
        }
        return null;
    }

    public boolean exists(String username) {
        ResultSet rs = null;
        try {
            String query = "select * from Players where username like '" + username + "'";
            rs = db.getDBInstance().executeQuery(query);
            if (rs.next() == true) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addGame(SubjectGameRI subjectGameRI) throws RemoteException {
        this.games.put(subjectGameRI.getTimestamp(), subjectGameRI);
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        if (exists(username) == false) {
            String query = "Insert Into Players( username, password, gameswon, gameslost ) VALUES('" + username + "','" + BCrypt.hashpw(password, BCrypt.gensalt()) + "', 0, 0 )";
            db.getDBInstance().executeQuery(query);
            return true;
        }
        return false;
    }

    public ArrayList<SubjectGameRI> getAllGames() {
        ArrayList<SubjectGameRI> gamesAux = new ArrayList<>();
        for (Timestamp timestamp : this.games.keySet()) {
            gamesAux.add(this.games.get(timestamp));
        }
        return gamesAux;
    }

    public SubjectGameRI getSubjectGame(Timestamp timestamp) {
        return this.games.get(timestamp);
    }

    public void logout(Player p) {
        this.lobbySessionRIList.remove(p);
        notifyAllObserversToLoggedPlayers();
    }

    public Player getPlayer(String username) {
        for (Player p : this.lobbySessionRIList.keySet()) {
            if (p.getUsername().compareTo(username) == 0) {
                return p;
            }
        }
        return null;
    }

    public void endGame(Timestamp timestamp) {
        /*String query = "Insert Into Games(gameMode) VALUES('" + gameMode + "')";
        Statement statement = db.getDBInstance().executeQueryStatement(query);
        ResultSet rs = null;
        try {
        rs = statement.getGeneratedKeys();
        } catch (SQLException ex) {
        Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        int idGame = -1;
        try {
        if (rs.next()) {
        idGame = rs.getInt(1);
        }
        } catch (SQLException ex) {
        Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query2 = "Insert Into PlayerGame(idplayer,idgame) VALUES(" + idPlayer + "," + idGame + ")";
        db.getDBInstance().executeQuery(query2);*/
        if (this.games.containsKey(timestamp)) {
            this.games.remove(timestamp);
            notifyAllObserversToGames();
        }
    }

    public void addPlayer(Player p, LobbySessionRI session) {
        this.lobbySessionRIList.put(p, session);
    }

    public void notifyAllObserversToLoggedPlayers() {
        for (Player p : this.lobbySessionRIList.keySet()) {
            try {
                ((LobbySessionImpl) this.lobbySessionRIList.get(p)).getObserverLobbyRI().updateLobbyPlayer();
            } catch (RemoteException ex) {
                Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void notifyAllObserversToGames() {
        for (Player p : this.lobbySessionRIList.keySet()) {
            try {
                ((LobbySessionImpl) this.lobbySessionRIList.get(p)).getObserverLobbyRI().updateLobbyGame();
            } catch (RemoteException ex) {
                Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void notifyAllObserversToMessages() {
        for (Player p : this.lobbySessionRIList.keySet()) {
            try {
                ((LobbySessionImpl) this.lobbySessionRIList.get(p)).getObserverLobbyRI().updateLobbyMesssage();
            } catch (RemoteException ex) {
                Logger.getLogger(MinesweeperFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player>players=new ArrayList<>();
        for(Player p:this.lobbySessionRIList.keySet()){
            players.add(p);
        }
        return players;
    }

}
