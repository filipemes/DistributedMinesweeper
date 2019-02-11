package Minesweeper.server;

import Minesweeper.client.ObserverGameRI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.TreeMap;



public interface SubjectGameRI extends Remote {

    public boolean attach(ObserverGameRI o, Player p) throws RemoteException;
     
    public Move getGameState() throws RemoteException;

    public void setGameState(Move s) throws RemoteException;

    public InitialState getInitialState() throws RemoteException;

    public void setInitialState(InitialState s) throws RemoteException;

    public Player getCurrentPlayer() throws RemoteException;

    public int getMaxPlayersGame() throws RemoteException;

    public boolean checkFireMarkedFieldPressed(Player player, int x, int y) throws RemoteException;

    public String getInitialGameMode() throws RemoteException;

    public Timestamp getTimestamp()throws RemoteException;
    
    public int getCurrentNumberOfPlayer()throws RemoteException;
    
    public boolean getIsReady()throws RemoteException;
    
    public void endGame()throws RemoteException;
}
