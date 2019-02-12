package Minesweeper.models;

import java.util.Vector;

import Minesweeper.client.jgui.MineSweeperToolbar;
import Minesweeper.client.listeners.GameModeListener;

/**
 * This class manages the game modes.
 * 
 * @author Sorin ( soriniulus@yahoo.com )
 * At: Apr 11, 2007, 12:12:50 PM
 */
public class GameModeManager {

	private static GameModeManager INSTANCE = new GameModeManager();

	private String gameMode;

	private int bombsNumber;

	private int totalNumberOfBombs;

	private Vector listenerList;

	private boolean questionMarksOn;

	/**
	 * Returns the single instance of this class.
	 * <p>
	 * 
	 * @return			the single instance of this class.
	 */
	public static GameModeManager getInstance() {
		return INSTANCE;
	}
        
	private GameModeManager() {
		listenerList = new Vector();
		gameMode = GameMode.expertMode;
		questionMarksOn = false;
	}
        public void setGameModeNew(String gameMode){
            this.gameMode=gameMode;
        }
	/**
	 * Registers a listener who is interested in the change of the game mode.
	 * <p>
	 * @param 			listener			the listener who is interested in the change of the game mode.
	 */
	public void addGameModeListener(GameModeListener listener) {
		listenerList.add(listener);
	}

	/**
	 * Notifies all the registered listeners when the game mode changed.
	 *
	 */
	private void gameModeChanged() {
		Object[] listeners = listenerList.toArray();
		if (listeners.length == 0 || listeners == null)
			return;
                
		for (int i = 0; i < listeners.length; i++) {
			((GameModeListener) listeners[i]).gameModeChanged();
		}
	}

	/**
	 * Reset the game.
	 *
	 */
	public void reset() {
		if (gameMode.equalsIgnoreCase(GameMode.expertMode)) {
			bombsNumber = GameMode.expertBombsNumber;
		}
		if (gameMode.equalsIgnoreCase(GameMode.mediumMode)) {
			bombsNumber = GameMode.mediumBombsNumber;
		}
		if (gameMode.equalsIgnoreCase(GameMode.juniorMode)) {
			bombsNumber = GameMode.juniorBombsNumber;
		}
		totalNumberOfBombs = bombsNumber;
		MineSweeperToolbar.getInstance().setMinesLeft(
				GameModeManager.getInstance().getBombsNumber());
	}

	/**
	 * Returns the bombs left number.
	 * <p>
	 * @return			the bombs left number.
	 */
	public int getBombsNumber() {
		return bombsNumber;
	}

	public void setBombsNumber(int n) {
		bombsNumber = n;
		MineSweeperToolbar.getInstance().setMinesLeft(
				GameModeManager.getInstance().getBombsNumber());
	}

	/**
	 * Sets the game mode.
	 * <p>
	 * @param 			gameMode			the game mode.
	 */
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
		gameModeChanged();
	}

	/**
	 * Returns the game mode.
	 * <p>
	 * @return			the game mode.
	 */
	public String getGameMode() {
		return gameMode;
	}

	/**
	 * Returns the minefield height.
	 * <p>
	 * 
	 * @return			the minefield height.
	 */
	public int getMineFieldHeight() {
		if (gameMode.equalsIgnoreCase(GameMode.expertMode))
			return GameMode.expertHeight;
		if (gameMode.equalsIgnoreCase(GameMode.mediumMode))
			return GameMode.mediumHeight;
		if (gameMode.equalsIgnoreCase(GameMode.juniorMode))
			return GameMode.juniorHeight;
		return 0;
	}

	/**
	 * Returns the minefield width.
	 * <p>
	 * 
	 * @return			the minefield width.
	 */
	public int getMineFieldWidth() {
		if (gameMode.equalsIgnoreCase(GameMode.expertMode))
			return GameMode.expertWidth;
		if (gameMode.equalsIgnoreCase(GameMode.mediumMode))
			return GameMode.mediumWidth;
		if (gameMode.equalsIgnoreCase(GameMode.juniorMode))
			return GameMode.juniorWidth;
		return 0;
	}

	/**
	 * Returns the total number of bombs.
	 * <p>
	 * @return			the total number of bombs.
	 */
	public int getTotalNumberOfBombs() {
		return totalNumberOfBombs;
	}

	/**
	 * Set the Question marks mode.
	 * <p>
	 * 
	 * @param 			b			true for Question marks mode on.
	 */
	public void setQuestionMarksOn(boolean b) {
		questionMarksOn = b;
	}

	/**
	 * Returns the Question marks mode.
	 * <p>
	 * 
	 * @return			the Question marks mode.
	 */
	public boolean isQuestionsMarksOn() {
		return questionMarksOn;
	}
}
