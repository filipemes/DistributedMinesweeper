package Minesweeper.client;

import Minesweeper.server.LobbySessionRI;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ObserverLobbyRI  extends Remote{
    
    public void updateLobbyPlayer()throws RemoteException;
    
    public void updateLobbyGame()throws RemoteException;
    
    public void updateLobbyMesssage()throws RemoteException;
    
    public void setLobbySessionRI(LobbySessionRI sub)throws RemoteException;
    
    public LobbySessionRI getLobbySessionRI() throws RemoteException;
    
}
