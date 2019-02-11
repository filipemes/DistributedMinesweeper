package Minesweeper.server;

import Minesweeper.client.ObserverGameRI;
import Minesweeper.client.ObserverLobbyRI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface LobbySessionRI extends Remote {

    public SubjectGameRI newGame(int numberPlayers, String initialGameMode, ObserverGameRI observerGameRI) throws RemoteException;

    public ArrayList<SubjectGameRI> getAllGames() throws RemoteException;

    public SubjectGameRI selectGame(Timestamp timestamp, ObserverGameRI observerGame) throws RemoteException;

    public Player getPlayer() throws RemoteException;

    public void logout() throws RemoteException;

    public void sendMessage(String message) throws RemoteException;

    public String getMessage() throws RemoteException;

    public ArrayList<Player> getAllLoggedPlayers() throws RemoteException;

}
