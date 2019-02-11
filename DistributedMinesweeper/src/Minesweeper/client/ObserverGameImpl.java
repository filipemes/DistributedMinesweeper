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

public class ObserverGameImpl extends UnicastRemoteObject implements ObserverGameRI {

    private SubjectGameRI subjectGameRI;
    private InitialState initalState;
    private Move gameStates;
    private Player currentPlayer;
    private GuiManager guiInstance;
    private MinesweeperClient minesweeperClient;
    
    ObserverGameImpl(GuiManager guiInstance,MinesweeperClient minesweeperClient) throws RemoteException, InterruptedException {
        super();
        this.guiInstance=guiInstance;
        this.minesweeperClient=minesweeperClient;
        gameStates = null;
        initalState = null;
    }

    /**
     * Retorna o SubjectGameRI
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public SubjectGameRI getSubjectGameRI() throws RemoteException {
        return this.subjectGameRI;
    }

    /**
     * Esta função atualiza o estado do jogo, faz isto ao verificar se o estado
     * do jogo(gamesStates), nao esta "vazia" e compara se é o jogo em questão
     * baseado no timeStamp e no estado do jogo. Se não tiver nada na variável
     * gamesStates, é atribuido o estado do Subject(proxy) válido Finalmente, é
     * efetuada a jogado no MainFrame, passando as coordenadas da jogada e o
     * tipode click, i.e. esquerdo se for jogada normal e direito se for uma
     * bandeira
     *
     * @throws RemoteException
     */
    @Override
    public void updateGameState() throws RemoteException {
        this.gameStates = this.subjectGameRI.getGameState();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiManager.getInstance().pushButton(gameStates.getRow(), gameStates.getColumn(),gameStates.getTypeOfClick());
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

    /**
     * Retorna se é possivel seleccionar ou remover uma bandeira pelo jogador
     * correto
     *
     * @param player
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
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

    /**
     * Adiciona um Subject(proxy)
     *
     * @param sub
     * @throws RemoteException
     */
    @Override
    public void setSubjectGameRI(SubjectGameRI sub) throws RemoteException {
        this.subjectGameRI = sub;
    }

    /**
     * Termina um jogo, ao "limpar" o estado inicial, o estado atual(gameStates)
     * e o seu Sujbject
     *
     * @throws RemoteException
     */
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
        initObserverGame();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //notify for remove the current game from table games
                    minesweeperClient.getObserverLobbyRI().updateLobbyGame();
                    //show dialog
                    guiInstance.endGame();
                } catch (RemoteException ex) {
                    Logger.getLogger(ObserverGameImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
