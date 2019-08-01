package Minesweeper.client;

import Minesweeper.server.InitialState;
import Minesweeper.server.SubjectGameRI;
import java.rmi.RemoteException;
import Minesweeper.client.jgui.GuiManager;
import Minesweeper.server.Move;
import Minesweeper.server.Player;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author filipe
 */
public class ObserverGameImpl extends UnicastRemoteObject implements ObserverGameRI {

    private SubjectGameRI subjectGameRI;
    private InitialState initalState;
    private Move gameStates;
    private Player currentPlayer;
    private GuiManager guiInstance;
    private MinesweeperClient minesweeperClient;

    ObserverGameImpl(GuiManager guiInstance, MinesweeperClient minesweeperClient) throws RemoteException, InterruptedException {
        super();
        this.guiInstance = guiInstance;
        this.minesweeperClient = minesweeperClient;
        gameStates = null;
        initalState = null;
    }

    @Override
    public SubjectGameRI getSubjectGameRI() throws RemoteException {
        return this.subjectGameRI;
    }

    @Override
    public void updateGameState() throws RemoteException {
        this.gameStates = this.subjectGameRI.getGameState();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiManager.getInstance().pushButton(gameStates.getRow(), gameStates.getColumn(), gameStates.getTypeOfClick());
            }
        });
    }

    /**
     * Coloca o estado Inicial do jogo igual ao estado inicial do Subject(proxy)
     * válido
     *
     * @throws RemoteException
     */
    @Override
    public void updateInitGame() throws RemoteException {
        this.initalState = this.subjectGameRI.getInitialState();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiManager.getInstance().startNewGame();
            }
        });

    }

    @Override
    public boolean checkFireMarkedFieldPressed(Player player, int x, int y) throws RemoteException {
        return this.subjectGameRI.checkFireMarkedFieldPressed(player, x, y);
    }

    /**
     * Retorna a vez do jogador correto para jogar, tendo em conta o servidor
     * válido, para coordenar as jogadas
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Player getCurrentPlayer() throws RemoteException {
        return this.currentPlayer;
    }

    @Override
    public void setSubjectGameRI(SubjectGameRI sub) throws RemoteException {
        this.subjectGameRI = sub;
    }


    @Override
    public void initObserverGame() throws RemoteException {
        this.gameStates = null;
        this.initalState = null;
        this.subjectGameRI = null;
    }

    @Override
    public void updateIsReady() throws RemoteException {
        GuiManager.getInstance().gameIsReady(this.subjectGameRI.getIsReady());
    }

    @Override
    public void updateCurrentPlayer() throws RemoteException {
        this.currentPlayer = this.subjectGameRI.getCurrentPlayer();
    }

    @Override
    public void endGame() throws RemoteException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //notify for remove the current game from table games
                    minesweeperClient.getObserverLobbyRI().updateLobbyGame();
                    //show dialog
                    guiInstance.endGame(subjectGameRI.isGameWon());
                    initObserverGame();
                } catch (RemoteException ex) {
                    Logger.getLogger(ObserverGameImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
