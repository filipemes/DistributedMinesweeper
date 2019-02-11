package Minesweeper.server;

import java.rmi.RemoteException;
import java.sql.Timestamp;

public interface MinesweeperAdminFactoryRI extends MinesweeperFactoryRI {

    public void endGame(Timestamp timestamp) throws RemoteException;

    public void addGame(SubjectGameRI subjectGameRI) throws RemoteException;

}
