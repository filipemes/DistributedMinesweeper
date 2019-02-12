package Minesweeper.server;

import Minesweeper.client.ObserverGameRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * @author filipe
 */
public final class SubjectGameImpl extends UnicastRemoteObject implements SubjectGameRI {

    private InitialState initState = null;
    private HashMap<Player, ObserverGameRI> observers;
    final private DoublyLinkedListPlayers turns;
    final private ArrayList<Move> gamesStates;
    private Move currentGameState;
    private int maxNumberPlayers;
    private String initialGameMode;
    private boolean isReady;
    private boolean gameWon;
    private Timestamp timestamp;
    private MinesweeperFactoryImpl minesweeperFactoryImpl;

    public SubjectGameImpl(MinesweeperFactoryImpl minesweeperEntryPointImpl, Timestamp timestamp, ObserverGameRI observerGameRI, Player p, String initialGameMode, int maxNumberPlayers) throws RemoteException {
        super();
        isReady = false;
        gameWon=false;
        observers = new HashMap<>();
        turns = new DoublyLinkedListPlayers();
        gamesStates = new ArrayList<>();
        this.timestamp = timestamp;
        this.maxNumberPlayers = maxNumberPlayers;
        this.initialGameMode = initialGameMode;
        this.minesweeperFactoryImpl = minesweeperEntryPointImpl;
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

    public Node getNode(Player p) {
        return this.turns.getNode(p);
    }

    @Override
    public Move getGameState() throws RemoteException {
        return this.currentGameState;
    }

    @Override
    public void setGameState(Move s) throws RemoteException {
        this.currentGameState = s;
        if (checkMarkedFlagField(s) == false) {
            this.gamesStates.add(s);
        }
        notifyAllObserversForNewMove();
        notifyAllObserversForCurrentPlayer();
    }


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

    @Override
    public InitialState getInitialState() throws RemoteException {
        return this.initState;
    }

 
    @Override
    public void setInitialState(InitialState s) throws RemoteException {
        this.initState = s;
    }

    @Override
    public int getMaxPlayersGame() throws RemoteException {
        return this.maxNumberPlayers;
    }

    @Override
    public String getInitialGameMode() throws RemoteException {
        return this.initialGameMode;
    }

    @Override
    public Player getCurrentPlayer() throws RemoteException {
        return this.turns.getCurrentPlayer().getPlayer();
    }

    @Override
    public Timestamp getTimestamp() throws RemoteException {
        return this.timestamp;
    }

    
    @Override
    public int getCurrentNumberOfPlayer() throws RemoteException {
        return this.observers.size();
    }
    
    @Override
    public boolean getIsReady() throws RemoteException {
        return this.isReady;
    }

    @Override
    public void endGame(boolean gameWon) throws RemoteException {
        this.gameWon=gameWon;
        this.minesweeperFactoryImpl.endGame(timestamp);
        notifyAllObserversForEndGame();
    }

    @Override
    public boolean isGameWon() throws RemoteException {
        return this.gameWon;
    }
}
