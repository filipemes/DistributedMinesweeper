package Minesweeper.timers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Minesweeper.client.jgui.MineSweeperStatusBar;

/**
 * This timer is used to set the system time in the application status bar.
 * <p>
 * @author Sorin ( soriniulus@yahoo.com )
 * At: Apr 13, 2007, 12:59:20 AM
 */
public class SystemTimeUpdater extends Timer {

	private static SystemTimeUpdater INSTANCE = new SystemTimeUpdater();

	private TimerTask task;

	/**
	 * Returns the single of this class.
	 * <p>
	 * 
	 * @return			the single of this class.
	 */
	public static SystemTimeUpdater getInstance() {
		return INSTANCE;
	}

	private SystemTimeUpdater() {
		task = new SystemTimeRefresher();
		this.schedule(task, new Date(), 1000l);
	}

	/**
	 * This task performs the operation of setting the system time in the application's status bar.
	 * <p>
	 * @author Sorin ( soriniulus@yahoo.com )
	 * At: Apr 13, 2007, 1:01:23 AM
	 */
	private class SystemTimeRefresher extends TimerTask {
		public void run() {
			MineSweeperStatusBar.getInstance().setCurrentTime();
		}
	}
}
