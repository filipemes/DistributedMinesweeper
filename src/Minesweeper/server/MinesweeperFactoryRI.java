package Minesweeper.server;

import Minesweeper.client.ObserverLobbyRI;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * 
 * @author filipe
 */
public interface MinesweeperFactoryRI extends Remote {

    public LobbySessionRI login(String username, String password,ObserverLobbyRI obs) throws RemoteException;

    public boolean register(String username, String password) throws RemoteException;

}
