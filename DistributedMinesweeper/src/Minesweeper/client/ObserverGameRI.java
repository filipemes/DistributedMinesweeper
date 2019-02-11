package Minesweeper.client;


import Minesweeper.server.Player;
import Minesweeper.server.SubjectGameRI;
import java.rmi.Remote;
import java.rmi.RemoteException;



public interface ObserverGameRI extends Remote {

    public void updateGameState() throws RemoteException;

    public void updateInitGame() throws RemoteException;

    public SubjectGameRI getSubjectGameRI() throws RemoteException;
    
    public void updateCurrentPlayer() throws RemoteException;

    public Player getCurrentPlayer() throws RemoteException;
    
    public void setSubjectGameRI(SubjectGameRI sub)throws RemoteException;
    
    public void initObserverGame()throws RemoteException;
    
    public boolean checkFireMarkedFieldPressed(Player player, int x, int y) throws RemoteException;
    
    public void updateIsReady()throws RemoteException;
    
    public void endGame()throws RemoteException;
}

