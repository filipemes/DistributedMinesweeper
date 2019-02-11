package Minesweeper.client.jgui;

import Minesweeper.client.MinesweeperClient;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Minesweeper.models.GameModeManager;
import Minesweeper.server.Move;
import Minesweeper.server.InitialState;
import Minesweeper.server.Player;
import Minesweeper.server.SubjectGameRI;
import Minesweeper.timers.ClockTimer;
import Minesweeper.timers.SystemTimeUpdater;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

/**
 * This class represents the main frame of the application.
 * <p>
 * The main frame contains the mine field area ( displayed in the center ), the
 * toolbar ( displayed in the <code>BorderLayout.NORTH</code> side ) and the
 * status bar ( displayed in the <code>BorderLayout.SOUTH</code> side).
 * <p>
 * The main frame registers itself as a listener to the game mode changes. When
 * the game mode is changed, a new game is started.
 * <p>
 * The toolbar offers informations about the number of mines left undiscovered,
 * the total amount of time ellapsed since a new game has started, and a button
 * that allows the user to start a new game.
 * <p>
 * The status bar shows the current system time.
 * <p>
 * The menu bar of the application allows the user to change the game mode,
 * start a new game, exit the game, or providing informations about how the game
 * is played ( from the <code>Help</code> menu).
 *
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 8, 2007, 8:04:01 PM
 */
public final class GuiManager {

    private static final long serialVersionUID = 1L;

    private static GuiManager INSTANCE = new GuiManager();

    private MineFieldPanel mineFieldPanel;

    private boolean gameStarted;

    private int numberOfButtonsRevealed;

    private boolean gameLost;

    private boolean gameWon;

    private boolean mainFrameIconified;

    private LobbyGame lobbyGame;

    private MinesweeperClient minesweeperClient;

    private boolean isReady;

    private WindowListener lobbyWindowListener;

    private WindowListener gameWindowListener;

    public JFrame frameLobby;

    private JFrame frameGame;

    private boolean isJoinedAGame;

    private GuiManager() {
        this.frameLobby = new JFrame();
        this.frameGame = new JFrame();
        this.frameLobby.setResizable(false);
        this.isJoinedAGame = false;
        this.frameLobby.setIconImage(new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/Icon.gif")).getImage());
        this.frameLobby.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        displayMainMenuPane();
    }

    public MinesweeperClient getMinesweeperClient() {
        return this.minesweeperClient;
    }

    public void setMinesweeperClient(MinesweeperClient minesweeperClient) {
        this.minesweeperClient = minesweeperClient;
    }

    public Player getPlayer() {
        return this.minesweeperClient.getPlayer();
    }

    public boolean checkEndGame() {
        return this.gameWon || this.gameLost;
    }

    public void notifyEndGame() {
        try {
            this.minesweeperClient.getObserverGameRI().getSubjectGameRI().endGame();
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkPlayer() {
        try {
            if (this.minesweeperClient.getPlayer().compareTo(this.minesweeperClient.getObserverGameRI().getCurrentPlayer()) == 0) {
                return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkFireMarkedFieldPressed(int x, int y) {
        try {
            return this.minesweeperClient.getObserverGameRI().getSubjectGameRI().
                    checkFireMarkedFieldPressed(this.minesweeperClient.getPlayer(), x, y);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Returns the single instance of this class.
     * <p>
     * @return	the single instance of this class
     */
    public static GuiManager getInstance() {
        return INSTANCE;
    }

    public InitialState getInitialGameState() {
        return new InitialState(this.mineFieldPanel.getMineFieldGenerator(), this.mineFieldPanel.getGameMode());
    }

    public MineFieldPanel getMineFieldPanel() {
        return this.mineFieldPanel;
    }

    public void setIsJoinedAGame(boolean isJoinedAGame) {
        this.isJoinedAGame = isJoinedAGame;
    }

    public void endGameLost() {
        setGameLost(true);
        setIsJoinedAGame(false);
        gameStarted(false);
    }

    public void endGame() {
        JDialogEndGame n = new JDialogEndGame(this.frameLobby);
        frameGame = new JFrame();
    }

    public void setGameState(Move g) {
        try {
            SubjectGameRI subjectGameRI = this.minesweeperClient.getObserverGameRI().getSubjectGameRI();
            subjectGameRI.setGameState(g);
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isReady() {
        return this.isReady;
    }

    public void startNewGame() {
        gameStarted(false);
        setGameLost(false);
        setGameWon(false);
        try {
            if (this.minesweeperClient.getObserverGameRI().getSubjectGameRI().getInitialState() == null) {
                GameModeManager.getInstance().setGameModeNew(this.minesweeperClient.getObserverGameRI().getSubjectGameRI().getInitialGameMode());
                mineFieldPanel = new MineFieldPanel(GameModeManager.getInstance()
                        .getGameMode());
                this.minesweeperClient.getObserverGameRI().getSubjectGameRI().setInitialState(this.getInitialGameState());
            } else {
                GameModeManager.getInstance().setGameModeNew(this.minesweeperClient.getObserverGameRI().getSubjectGameRI().getInitialState().getGameMode());
                mineFieldPanel = new MineFieldPanel(this.minesweeperClient.getObserverGameRI().getSubjectGameRI().getInitialState().getGameMode(), this.minesweeperClient.getObserverGameRI().getSubjectGameRI().getInitialState().getMineFieldGenerator());

            }
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        this.frameGame.getContentPane().add(mineFieldPanel, BorderLayout.CENTER);
        this.frameGame.getContentPane().add(MineSweeperToolbar.getInstance(),
                BorderLayout.NORTH);
        this.frameGame.getContentPane().add(MineSweeperStatusBar.getInstance(),
                BorderLayout.SOUTH);
        MineSweeperToolbar.getInstance().initClock();
    }

    /**
     * Increase the number of buttons correctly revealed. When all the buttons
     * are correctly revealed, inform the user that he won.
     *
     */
    public void increaseNumberOfButtonsCorrectlyRevealed() {
        numberOfButtonsRevealed++;
        if (numberOfButtonsRevealed == GameModeManager.getInstance()
                .getMineFieldHeight()
                * GameModeManager.getInstance().getMineFieldWidth()
                - GameModeManager.getInstance().getTotalNumberOfBombs()) {

            int secondsHighScore = MineSweeperToolbar.getInstance()
                    .getTimeEllapsed();
            gameStarted(false);

            for (int i = 0; i < mineFieldPanel.getBombButtons().length; i++) {
                for (int j = 0; j < mineFieldPanel.getBombButtons()[i].length; j++) {
                    if (mineFieldPanel.getBombButtons()[i][j].getIcon() == null) {
                        mineFieldPanel.getBombButtons()[i][j]
                                .setIcon(ImageIconResourcer.getInstance()
                                        .getIconMark());
                    }
                }
            }
            setIsJoinedAGame(false);
            setGameWon(true);
            /*if (GameModeManager.getInstance().getGameMode() == GameMode.expertMode) {
                Preferences pref = Preferences.userRoot();
                int time = 999;
                time = pref.node("/jminesweeper").getInt("experttime", 999);
                if (time > secondsHighScore) {
                new HighScoresDialog(MainFrame.getInstance(),
                "High scores", true,
                HighScoresDialogMode.EXPERT_UPDATE_MODE,
                secondsHighScore).setVisible(true);
                }
                }
                if (GameModeManager.getInstance().getGameMode() == GameMode.expertMode) {
                Preferences pref = Preferences.userRoot();
                int time = 999;
                time = pref.node("/jminesweeper").getInt("experttime", 999);
                if (time > secondsHighScore) {
                new HighScoresDialog(MainFrame.getInstance(),
                "High scores", true,
                HighScoresDialogMode.EXPERT_UPDATE_MODE,
                secondsHighScore);
                }
                }
                if (GameModeManager.getInstance().getGameMode() == GameMode.mediumMode) {
                Preferences pref = Preferences.userRoot();
                int time = 999;
                time = pref.node("/jminesweeper").getInt("mediumtime", 999);
                if (time > secondsHighScore) {
                new HighScoresDialog(MainFrame.getInstance(),
                "High scores", true,
                HighScoresDialogMode.MEDIUM_UPDATE_MODE,
                secondsHighScore);
                }
                }

                if (GameModeManager.getInstance().getGameMode().compareTo(GameMode.juniorMode) == 0) {
                Preferences pref = Preferences.userRoot();
                int time = 999;
                time = pref.node("/jminesweeper").getInt("juniortime", 999);
                if (time > secondsHighScore) {
                new HighScoresDialog(MainFrame.getInstance(),
                "High scores", true,
                HighScoresDialogMode.JUNIOR_UPDATE_MODE,
                secondsHighScore);
                }
                }*/
        }
    }

    public void logOutJOption() {
        int answer = JOptionPane.showConfirmDialog(this.frameLobby,
                "Are you sure you want to log out?", "Log Out?",
                JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            this.frameLobby.dispose();
            logOut();
            System.exit(0);
        }
    }

    public void gameIsReady(boolean b) {
        this.isReady = b;

    }

    /**
     * Init the clock timer when the game starts, and cancel it when the game is
     * lost.
     * <p>
     * @param b	whether the game is started or over.
     */
    public void gameStarted(boolean b) {
        gameStarted = b;
        if (b) {
            ClockTimer.newInstance();
            numberOfButtonsRevealed = 0;
        } else {
            if (ClockTimer.getInstance() != null) {
                ClockTimer.getInstance().cancel();
            }

        }
    }

    public void setJoinedAGame(boolean joined) {
        this.isJoinedAGame = joined;
    }

    public boolean isJoinedAGame() {
        return this.isJoinedAGame;
    }

    /**
     * Returns whether the user started the game or not. The game is consider to
     * be started when the first button in the minefield is pressed.
     * <p>
     * @return	whether the user started the game or not.
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * The player lost the game.
     *
     */
    /*
    public void gameLost(int row, int column) throws RemoteException {
        gameStarted(false);
        setGameLost(true);
        getContentPane().removeAll();
        getContentPane().add(mineFieldPanel.getGameLostMineFieldPanel(),
                BorderLayout.CENTER);

        for (int i = 0; i < mineFieldPanel.getBombButtons().length; i++) {
            for (int j = 0; j < mineFieldPanel.getBombButtons()[i].length; j++) {
                if (mineFieldPanel.getBombButtons()[i][j].getIcon() == ImageIconResourcer
                        .getInstance().getIconMark()
                        && mineFieldPanel.getBombButtons()[i][j].getState() != -1) {
                    mineFieldPanel.getGameLostBombButtons()[i][j]
                            .setIcon(ImageIconResourcer.getInstance()
                                    .getIconBombWrong());
                }
                if (mineFieldPanel.getBombButtons()[i][j].getIcon() == null
                        && mineFieldPanel.getBombButtons()[i][j].getState() == -1) {
                    mineFieldPanel.getGameLostBombButtons()[i][j]
                            .setIcon(ImageIconResourcer.getInstance()
                                    .getIconBombUnfind());
                }
            }
        }
        mineFieldPanel.getGameLostBombButtons()[row][column]
                .setIcon(ImageIconResourcer.getInstance().getIcon_1());

        getContentPane().add(MineSweeperToolbar.getInstance(),
                BorderLayout.NORTH);
        getContentPane().add(MineSweeperStatusBar.getInstance(),
                BorderLayout.SOUTH);
        pack();
    }*/
    /**
     * Returns true if the game is lost. This helps when the user wants to
     * continue clicking on buttons, but the game is already lost.
     * <p>
     * @return	true if the game is lost.
     */
    public boolean isGameLost() {
        return gameLost;
    }

    /**
     * Set the status of the game: lost or not.
     * <p>
     * @param b	whether the game is lost or not.
     */
    private void setGameLost(boolean b) {
        gameLost = b;
    }

    private void setGameWon(boolean b) {
        gameWon = b;
    }

    /**
     * Returns true if the user won the game.
     * <p>
     *
     * @return	true if the user won the game.
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Returns true if the main frame is iconified.
     * <p>
     *
     * @return	true if the main frame is iconified.
     */
    public boolean isMainFrameIconified() {
        return mainFrameIconified;
    }

    public void displayMainMenuPane() {
        this.frameLobby.getContentPane().removeAll();
        this.frameLobby.setSize(815, 640);
        this.frameLobby.getContentPane().setLayout(new BorderLayout());
        JFXPanel fxPanel = new JFXPanel();
        this.frameLobby.getContentPane().add(fxPanel);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.MAIN_MENU.getPath()));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 800, 600);
                    fxPanel.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void displayLoginPane() {
        this.frameLobby.getContentPane().removeAll();
        this.frameLobby.setSize(815, 640);
        this.frameLobby.getContentPane().setLayout(new BorderLayout());
        JFXPanel fxPanel = new JFXPanel();
        this.frameLobby.getContentPane().add(fxPanel);
        this.frameLobby.getContentPane().setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.LOGIN.getPath()));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 800, 600);
                    fxPanel.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public WindowListener getLobbyWindowListener() {
        if (lobbyWindowListener == null) {
            lobbyWindowListener = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    logOutJOption();
                }
            };
        }
        return lobbyWindowListener;
    }

    public WindowListener getGameWindowListener() {
        if (gameWindowListener == null) {
            gameWindowListener = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frameLobby.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }

                public void windowIconified(WindowEvent e) {
                    mainFrameIconified = true;
                }

                public void windowDeiconified(WindowEvent e) {
                    mainFrameIconified = false;
                }
            };
        }
        return gameWindowListener;
    }

    public void displayGameLobbyPane() {
        this.frameLobby.getContentPane().removeAll();
        this.frameLobby.getContentPane().setVisible(true);
        this.frameLobby.setSize(815, 640);
        this.frameLobby.addWindowListener(this.getLobbyWindowListener());
        this.frameLobby.getContentPane().setLayout(new BorderLayout());
        JFXPanel fxPanel = new JFXPanel();
        this.frameLobby.getContentPane().add(fxPanel);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.GAME_LOBBY.getPath()));
                try {
                    Parent root = loader.load();
                    root.setVisible(true);
                    lobbyGame = (LobbyGame) loader.getController();
                    Scene scene = new Scene(root, 800, 600);
                    fxPanel.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logOut() {
        try {
            minesweeperClient.getLobbySessionRI().logout();
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayLoginPane();
        lobbyGame = null;
        this.frameLobby.removeWindowListener(this.lobbyWindowListener);
    }

    public void displayRegisterPane() {
        this.frameLobby.getContentPane().removeAll();
        this.frameLobby.setSize(815, 640);
        this.frameLobby.getContentPane().setLayout(new BorderLayout());
        JFXPanel fxPanel = new JFXPanel();
        this.frameLobby.getContentPane().add(fxPanel);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.REGISTER.getPath()));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 800, 600);
                    fxPanel.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void displayGamePane() {
        this.frameGame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frameGame.setTitle("Distributed Minesweeper");
        this.frameGame.getContentPane().setLayout(new BorderLayout());
        this.frameGame.setResizable(false);
        this.frameGame.getContentPane().setVisible(true);
        this.frameGame.setTitle("Distributed Minesweeper");
        this.frameGame.addWindowListener(this.getGameWindowListener());
        startNewGame();
        this.frameGame.setLocationRelativeTo(null);
        SystemTimeUpdater.getInstance();
        this.frameGame.setVisible(true);
        this.frameGame.pack();
    }

    public void displayLoggedPlayers(ArrayList<Player> players) {
        if (lobbyGame != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lobbyGame.updateLoggedPlayers(players);
                }
            });
        }
    }

    public void displayNewMessage(String message) {
        if (lobbyGame != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lobbyGame.updateNewMessage(message);
                }
            });
        }
    }

    public void displayNewGames(ArrayList<SubjectGameRI> sub) {
        if (lobbyGame != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lobbyGame.updateNewGames(sub);
                }
            });
        }

    }

    public void clearGamePane() {
        this.frameGame.dispose();
    }

    public void pushButton(int row, int column, String type) {
        this.mineFieldPanel.pushButton(row, column, type);
    }

    public void newGame(String initialGameMode, int numberOfPlayers) {
        gameIsReady(false);
        this.isJoinedAGame = true;
        try {
            this.minesweeperClient.getLobbySessionRI().newGame(numberOfPlayers, initialGameMode, this.minesweeperClient.getObserverGameRI());
        } catch (RemoteException ex) {
            Logger.getLogger(MinesweeperClient.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        displayGamePane();
    }

    public void selectGame(Timestamp timestamp) {
        gameIsReady(false);
        try {
            if (this.minesweeperClient.getLobbySessionRI().selectGame(timestamp, this.minesweeperClient.getObserverGameRI()) != null) {
                this.isJoinedAGame = true;
                displayGamePane();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MinesweeperClient.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
