package Minesweeper.server;

import Minesweeper.client.ObserverGameRI;
import Minesweeper.client.ObserverLobbyRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author filipe
 */
public class LobbySessionImpl implements LobbySessionRI {

    private MinesweeperFactoryImpl minesweeperFactoryImpl;
    private Player player;
    private ObserverLobbyRI observerLobbyRI;

    public LobbySessionImpl(MinesweeperFactoryImpl minesweeperFactoryImpl, Player player, ObserverLobbyRI observerLobbyRI) throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        this.minesweeperFactoryImpl = minesweeperFactoryImpl;
        this.player = player;
        this.observerLobbyRI = observerLobbyRI;
    }

    @Override
    public SubjectGameRI newGame(int numberPlayers, String initialGameMode, ObserverGameRI observerGameRI) throws RemoteException {
        SubjectGameRI subjectGameRI = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            subjectGameRI = (SubjectGameRI) new SubjectGameImpl(this.minesweeperFactoryImpl, timestamp, observerGameRI, this.player, initialGameMode, numberPlayers);
            observerGameRI.setSubjectGameRI(subjectGameRI);
            this.minesweeperFactoryImpl.addGame(subjectGameRI);
            this.minesweeperFactoryImpl.notifyAllObserversToGames();
        } catch (RemoteException ex) {
            Logger.getLogger(LobbySessionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectGameRI;
    }

    @Override
    public ArrayList<SubjectGameRI> getAllGames() throws RemoteException {
        return this.minesweeperFactoryImpl.getAllGames();
    }

    @Override
    public SubjectGameRI selectGame(Timestamp timestamp, ObserverGameRI observerGame) throws RemoteException {
        try {
            SubjectGameRI aux = this.minesweeperFactoryImpl.getSubjectGame(timestamp);
            if (aux.attach(observerGame, this.player)) {
                observerGame.setSubjectGameRI(aux);
                this.minesweeperFactoryImpl.notifyAllObserversToGames();
                return aux;
            } else {
                return null;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LobbySessionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void logout() throws RemoteException {
        this.minesweeperFactoryImpl.logout(this.player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public ObserverLobbyRI getObserverLobbyRI() {
        return this.observerLobbyRI;
    }

    @Override
    public ArrayList<Player> getAllLoggedPlayers() throws RemoteException {
        return this.minesweeperFactoryImpl.getAllPlayers();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        this.minesweeperFactoryImpl.setMessage(message);
        this.minesweeperFactoryImpl.notifyAllObserversToMessages();
    }

    @Override
    public String getMessage() throws RemoteException {
        return this.minesweeperFactoryImpl.getMessage();
    }

}
