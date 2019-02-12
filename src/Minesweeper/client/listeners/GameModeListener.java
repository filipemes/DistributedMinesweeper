package Minesweeper.client.listeners;

import java.util.EventListener;

/**
 * This interface represents the listener for the game mode changes events.
 * <p>
 * 
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 12, 2007, 9:07:37 PM
 */
public interface GameModeListener extends EventListener {

	/**
	 * The game mode has changed. That means that the application window must
	 * revalidate itself.
	 * 
	 */
	public abstract void gameModeChanged();
        public abstract void createNewGame();
}
