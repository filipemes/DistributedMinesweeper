package Minesweeper.server;

import Minesweeper.client.ObserverGameRI;
import java.rmi.RemoteException;
import java.util.HashMap;


public interface SubjectGameAdminRI extends SubjectGameRI {
    
    public  HashMap<Player, ObserverGameRI> getAllObservers()throws RemoteException;
    
    public void addObserverPlayer(Player p,ObserverGameRI obs)throws RemoteException;
}
