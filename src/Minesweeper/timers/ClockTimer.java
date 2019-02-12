package Minesweeper.timers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Minesweeper.client.jgui.MineSweeperToolbar;

/**
 * Updates the time ellapsed since the game start.
 * <p>
 * @author Sorin ( soriniulus@yahoo.com )
 * At: Apr 11, 2007, 1:21:44 PM
 */
public class ClockTimer extends Timer {

	private static ClockTimer INSTANCE;
	
	private TimerTask task;
	
	/**
	 * Returns a new instance of this class.
	 * <p>
	 * 
	 * @return			a new instance of this class.
	 */
	public static ClockTimer newInstance() {
		INSTANCE = new ClockTimer();
		return INSTANCE;
	}
	
	/**
	 * Returns the current instance of this class.
	 * <p>
	 * 
	 * @return			the current instance of this class.
	 */
	public static ClockTimer getInstance() {
		return INSTANCE;
	}
	
	private ClockTimer() {
		task = new ClockUpdater();
		this.schedule( task, new Date(), 1000l );
	}
	
	/**
	 * Updates the clock.
	 * <p>
	 * @author Sorin ( soriniulus@yahoo.com )
	 * At: Apr 11, 2007, 1:23:45 PM
	 */
	private class ClockUpdater extends TimerTask {
		public void run() {
			MineSweeperToolbar.getInstance().ticTac();
		}
	}
}
