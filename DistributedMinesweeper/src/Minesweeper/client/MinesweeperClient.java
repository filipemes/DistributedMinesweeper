package Minesweeper.client;

import Minesweeper.client.jgui.GuiManager;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Minesweeper.server.MinesweeperClientFactoryRI;
import Minesweeper.server.LobbySessionRI;
import Minesweeper.server.Player;
import javax.swing.SwingUtilities;

public class MinesweeperClient {

    public static final String absPathToResourceIcons = "/resources/icons"; //Path to Resource Icons
    private ObserverGameRI observerGameRI;
    private ObserverLobbyRI observerLobbyRI;
    private LobbySessionRI lobbySessionRI;
    private MinesweeperClientFactoryRI MinesweeperClientFactoryRI;
    private Player player;
    private GuiManager guiManager;

    public MinesweeperClient() {
        try {
            this.observerLobbyRI = (ObserverLobbyRI) new ObserverLobbyImpl(GuiManager.getInstance(), this);
            this.observerGameRI = (ObserverGameRI) new ObserverGameImpl(GuiManager.getInstance(), this);
        } catch (RemoteException ex) {
            Logger.getLogger(MinesweeperClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MinesweeperClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public LobbySessionRI getLobbySessionRI() {
        return this.lobbySessionRI;
    }

    public ObserverGameRI getObserverGameRI() {
        return this.observerGameRI;
    }

    public ObserverLobbyRI getObserverLobbyRI() {
        return this.observerLobbyRI;
    }

    public int login(String username, String password) {
        if (this.MinesweeperClientFactoryRI == null) {
            return -1;
        }
        try {
            if ((this.lobbySessionRI = this.MinesweeperClientFactoryRI.login(username, password, this.observerLobbyRI)) != null) {
                this.player = this.lobbySessionRI.getPlayer();
                return 1;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MinesweeperClient.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int register(String username, String password) {
        if (this.MinesweeperClientFactoryRI == null) {
            return -1;
        }
        try {
            if (this.MinesweeperClientFactoryRI.register(username, password) == true) {
                return 1;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MinesweeperClient.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static void main(String args[]) throws RemoteException {
        MinesweeperClient minesweeperClient = new MinesweeperClient();
        minesweeperClient.guiManager = GuiManager.getInstance();
        ClientConnectionConfigs serviceClass = new ClientConnectionConfigs(args);
        minesweeperClient.MinesweeperClientFactoryRI = (MinesweeperClientFactoryRI) serviceClass.getClientFactoryRI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                minesweeperClient.guiManager.setMinesweeperClient(minesweeperClient);
                minesweeperClient.guiManager.frameLobby.setVisible(true);
            }
        });

    }

}
