package Minesweeper.client.listeners;

import java.util.EventListener;

/**
 * This interface will be implemented for all the bomb buttons who want to
 * register themselves as listeners to the actions of mouse pressed on their
 * neighbours.
 * <p>
 * 
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 8, 2007, 6:01:40 PM
 */
public interface BombButtonPressedListener extends EventListener {

	/**
	 * This method is called for the neighbour buttons of the pressed button.
	 * 
	 */
	public abstract int fireBombButtonPressed();
}
