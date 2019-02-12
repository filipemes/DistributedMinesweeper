package Minesweeper.models;

/**
 * This interface holds informations about the game modes.
 * <p>
 * 
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 11, 2007, 11:58:44 AM
 */
public interface GameMode {

	/**
	 * The number of mines for the expert game mode.
	 */
	public static final int expertBombsNumber = 130;

	/**
	 * The number of mines for the medium game mode.
	 */
	public static final int mediumBombsNumber = 60;

	/**
	 * The number of mines for the junior game mode.
	 */
	public static final int juniorBombsNumber = 15;

	/**
	 * The minefield witdh for the expert mode.
	 */
	public static final int expertWidth = 30;

	/**
	 * The minefield height for the expert mode.
	 */
	public static final int expertHeight = 20;

	/**
	 * The minefield width for the medium mode.
	 */
	public static final int mediumWidth = 20;

	/**
	 * The minefield height for the medium mode.
	 */
	public static final int mediumHeight = 15;

	/**
	 * The minefield width for the junior mode.
	 */
	public static final int juniorWidth = 15;

	/**
	 * The minefield height for the junior mode.
	 */
	public static final int juniorHeight = 10;

	/**
	 * Expert game mode
	 */
	public static final String expertMode = "EXPERT";

	/**
	 * Medium game mode
	 */
	public static final String mediumMode = "MEDIUM";

	/**
	 * Junior game mode
	 */
	public static final String juniorMode = "JUNIOR";
}
