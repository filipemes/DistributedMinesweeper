package Minesweeper.client;

import Minesweeper.client.jgui.GuiManager;
import Minesweeper.server.LobbySessionRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * 
 * @author filipe
 */
public class ObserverLobbyImpl extends UnicastRemoteObject implements ObserverLobbyRI {

    private LobbySessionRI lobbySessionRI;
    private GuiManager guiInstance;
    
    public ObserverLobbyImpl(GuiManager guiInstance) throws RemoteException {
        super();
        this.guiInstance=guiInstance;
    }

    @Override
    public void updateLobbyPlayer() throws RemoteException {
        GuiManager.getInstance().displayLoggedPlayers(this.lobbySessionRI.getAllLoggedPlayers());
    }

    @Override
    public LobbySessionRI getLobbySessionRI() throws RemoteException {
        return this.lobbySessionRI;
    }

    @Override
    public void updateLobbyGame() throws RemoteException {
         this.guiInstance.displayNewGames(this.lobbySessionRI.getAllGames());
    }

    @Override
    public void updateLobbyMesssage() throws RemoteException {
         this.guiInstance.displayNewMessage(this.lobbySessionRI.getMessage());
    }

    @Override
    public void setLobbySessionRI(LobbySessionRI sub) throws RemoteException {
       this.lobbySessionRI=sub;
    }

}
