package Minesweeper.server;

import Minesweeper.client.ObserverGameRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public final class SubjectGameImpl extends UnicastRemoteObject implements SubjectGameRI, SubjectGameAdminRI {

    private InitialState initState = null;
    private HashMap<Player, ObserverGameRI> observers;
    final private DoublyLinkedListPlayers turns;
    final private ArrayList<Move> gamesStates;
    private Move currentGameState;
    private int maxNumberPlayers;
    private String initialGameMode;
    private boolean isReady = false;
    private Timestamp timestamp;
    private MinesweeperAdminFactoryRI minesweeperAdminFactoryRI;

    public SubjectGameImpl(MinesweeperAdminFactoryRI minesweeperAdminFactoryRI, Timestamp timestamp, ObserverGameRI observerGameRI, Player p, String initialGameMode, int maxNumberPlayers) throws RemoteException {
        super();
        observers = new HashMap<>();
        turns = new DoublyLinkedListPlayers();
        gamesStates = new ArrayList<>();
        this.timestamp = timestamp;
        this.maxNumberPlayers = maxNumberPlayers;
        this.initialGameMode = initialGameMode;
        this.minesweeperAdminFactoryRI = minesweeperAdminFactoryRI;
        attach(observerGameRI, p);
    }

    public SubjectGameImpl(InitialState initState,
            DoublyLinkedListPlayers turns, ArrayList<Move> gamesStates,
            Move currentGameState, int maxNumberPlayers, String initialGameMode,
            boolean isReady, boolean isEndOfGame, Timestamp timestamp) throws RemoteException {
        super();
        this.initState = initState;
        this.turns = turns;
        this.gamesStates = gamesStates;
        this.currentGameState = currentGameState;
        this.maxNumberPlayers = maxNumberPlayers;
        this.initialGameMode = initialGameMode;
        this.isReady = isReady;
        this.timestamp = timestamp;
    }

    /**
     * Permite inserir um jogador no jogo
     *
     * @param o
     * @param p
     * @throws RemoteException
     */
    @Override
    public synchronized boolean attach(ObserverGameRI o, Player p) throws RemoteException {
        if (this.getMaxPlayersGame() >= (this.getCurrentNumberOfPlayer() + 1)) {
            this.observers.put(p, o);
            o.setSubjectGameRI(this);
            if (this.getCurrentNumberOfPlayer() == 1) {
                this.turns.attach(new Node(p, true));
            } else {
                this.turns.attach(new Node(p, false));
            }
            if (this.getCurrentNumberOfPlayer() == this.getMaxPlayersGame()) {
                this.isReady = true;
                notifyAllObserversToIsReadyState();
                notifyAllObserversForCurrentPlayer();
            }
            return true;
        }
        return false;
    }

    /**
     * Permite retornar o jogador corrente
     *
     * @param p
     * @return
     */
    public Node getNode(Player p) {
        return this.turns.getNode(p);
    }

    /**
     * Permite retornar o estado corrente
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Move getGameState() throws RemoteException {
        return this.currentGameState;
    }

    /**
     * Permite alterar o estado
     *
     * @param s
     * @throws RemoteException
     */
    @Override
    public void setGameState(Move s) throws RemoteException {
        this.currentGameState = s;
        if (checkMarkedFlagField(s) == false) {
            this.gamesStates.add(s);
        }
        notifyAllObserversForNewMove();
        notifyAllObserversForCurrentPlayer();
    }

    /**
     * O objectivo deste método é verificar e remover a bandeira quando
     * desselecionada, garantido assim que apenas o jogador que selecionou pode
     * remover.
     *
     * @param s
     * @return
     */
    public boolean checkMarkedFlagField(Move s) {
        for (int i = 0; i < this.gamesStates.size(); i++) {
            if (this.gamesStates.get(i).getTypeOfClick().compareTo("Right") == 0 && s.getTypeOfClick().compareTo("Right") == 0
                    && this.gamesStates.get(i).getCurrentPlayer().compareTo(s.getCurrentPlayer()) == 0
                    && this.gamesStates.get(i).getRow() == s.getRow() && this.gamesStates.get(i).getColumn() == s.getColumn()) {
                this.gamesStates.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Verificar se o jogador pode selecionar/desselecionar uma bandeira, isto
     * só é possível se não existir nenhum gameState com a bandeira selecionada
     * ou se o gameState foi selecionado pelo mesmo jogador que a quer
     * desselecionar.
     *
     * @param player
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean checkFireMarkedFieldPressed(Player player, int x, int y) throws RemoteException {
        for (int i = 0; i < this.gamesStates.size(); i++) {
            if (this.gamesStates.get(i).getRow() == x && this.gamesStates.get(i).getColumn() == y) {
                if (player.compareTo(this.gamesStates.get(i).getCurrentPlayer()) == 0) {
                    return true;
                }
                return false;
            }
        }

        return true;
    }

    /**
     * Permite notificar o estado corrente a todos os jogadores excepto o
     * próprio que jogou. Este método permite notificar todos os Observers com o
     * estado atual.
     *
     * @throws RemoteException
     */
    public void notifyAllObserversForNewMove() throws RemoteException {
        for (Player p : this.observers.keySet()) {
            if (p.compareTo(this.getGameState().getCurrentPlayer()) != 0) {
                this.observers.get(p).updateGameState();
            }
        }
        this.turns.passingTheTurnToAnotherPlayer();
    }

    public void notifyAllObserversToIsReadyState() throws RemoteException {
        for (Player p : this.observers.keySet()) {
            this.observers.get(p).updateIsReady();
        }
    }

    public void notifyAllObserversForCurrentPlayer() throws RemoteException {
        for (Player p : this.observers.keySet()) {
            this.observers.get(p).updateCurrentPlayer();
        }
    }

    public void notifyAllObserversForEndGame() throws RemoteException {
        for (Player p : this.observers.keySet()) {
            this.observers.get(p).endGame();
        }
    }

    /**
     * Retorna o estado inicial do Jogo.
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public InitialState getInitialState() throws RemoteException {
        return this.initState;
    }

    /**
     * Permite alterar o estado inicial do jogo.
     *
     * @param s
     * @throws RemoteException
     */
    @Override
    public void setInitialState(InitialState s) throws RemoteException {
        this.initState = s;
        //notifyAllObserversToInitGame();
    }

    /**
     * Retorna o número máximos de um jogo.
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public int getMaxPlayersGame() throws RemoteException {
        return this.maxNumberPlayers;
    }

    /**
     * Retorna o estado inicial do Jogo
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getInitialGameMode() throws RemoteException {
        return this.initialGameMode;
    }

    /**
     * Permite retornar o jogador corrente.
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Player getCurrentPlayer() throws RemoteException {
        return this.turns.getCurrentPlayer().getPlayer();
    }

    /**
     * Permite retornar o timestamp deste subject
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Timestamp getTimestamp() throws RemoteException {
        return this.timestamp;
    }

    /**
     * Retorna o número corrente de jogadores.
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public int getCurrentNumberOfPlayer() throws RemoteException {
        return this.observers.size();
    }

    @Override
    public HashMap<Player, ObserverGameRI> getAllObservers() throws RemoteException {
        return this.observers;
    }

    @Override
    public void addObserverPlayer(Player p, ObserverGameRI obs) throws RemoteException {
        this.observers.put(p, obs);
    }

    @Override
    public boolean getIsReady() throws RemoteException {
        return this.isReady;
    }

    @Override
    public void endGame() throws RemoteException {
        this.minesweeperAdminFactoryRI.endGame(timestamp);
        notifyAllObserversForEndGame();
    }
}
