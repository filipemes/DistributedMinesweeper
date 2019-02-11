package Minesweeper.client.jgui;

import javax.swing.ImageIcon;
import Minesweeper.client.MinesweeperClient;

/**
 * This class is a sigleton and it's role is to provide the icons for the bomb
 * buttons.
 *
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 8, 2007, 6:50:01 PM
 */
public class ImageIconResourcer {

    private ImageIcon icon_1;

    private ImageIcon icon0;

    private ImageIcon icon1;

    private ImageIcon icon2;

    private ImageIcon icon3;

    private ImageIcon icon4;

    private ImageIcon icon5;

    private ImageIcon icon6;

    private ImageIcon icon7;

    private ImageIcon icon8;

    private ImageIcon iconMark;

    private ImageIcon iconBombUnfind;

    private ImageIcon iconBombWrong;

    private ImageIcon iconQuestionMark;

    private ImageIcon tryToRevealIcon;

    private static ImageIconResourcer INSTANCE = new ImageIconResourcer();

    /**
     * Private constructor: no duplicate instances of this class will be
     * created.
     *
     */
    private ImageIconResourcer() {
        init();
    }

    /**
     * Returns the single instance of this class.
     * <p>
     *
     * @return the single instance of this class.
     */
    public static ImageIconResourcer getInstance() {
        return INSTANCE;
    }

    /**
     * Initialize the icons.
     *
     */
    private void init() {
        System.out.println(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/-1.PNG"));
        icon_1 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/-1.PNG"));
        icon0 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/0.PNG"));
        icon1 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/1.PNG"));
        icon2 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/2.PNG"));
        icon3 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/3.PNG"));
        icon4 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/4.PNG"));
        icon5 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/5.PNG"));
        icon6 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/6.PNG"));
        icon7 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/7.PNG"));
        icon8 = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/8.PNG"));
        iconMark = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/bomb_mark.PNG"));
        iconBombUnfind = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/bomb_unfind.PNG"));
        iconBombWrong = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/bomb_wrong.PNG"));
        iconQuestionMark = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/question_mark.PNG"));
        tryToRevealIcon = new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/try_to_reveal.PNG"));
    }

    /**
     * Returns the bomb icon.
     * <p>
     *
     * @return the bomb icon
     */
    public ImageIcon getIcon_1() {
        return icon_1;
    }

    /**
     * Returns the empty field icon..
     * <p>
     *
     * @return the empty field icon.
     */
    public ImageIcon getIcon0() {
        return icon0;
    }

    /**
     * Returns the 1 bomb icon.
     * <p>
     *
     * @return the 1 bomb icon
     */
    public ImageIcon getIcon1() {
        return icon1;
    }

    /**
     * Returns the 2 bombs icon.
     * <p>
     *
     * @return the 2 bombs icon
     */
    public ImageIcon getIcon2() {
        return icon2;
    }

    /**
     * Returns the 3 bombs icon.
     * <p>
     *
     * @return the 3 bombs icon
     */
    public ImageIcon getIcon3() {
        return icon3;
    }

    /**
     * Returns the 4 bombs icon.
     * <p>
     *
     * @return the 4 bombs icon
     */
    public ImageIcon getIcon4() {
        return icon4;
    }

    /**
     * Returns the 5 bombs icon.
     * <p>
     *
     * @return the 5 bombs icon
     */
    public ImageIcon getIcon5() {
        return icon5;
    }

    /**
     * Returns the 6 bombs icon.
     * <p>
     *
     * @return the 6 bombs icon
     */
    public ImageIcon getIcon6() {
        return icon6;
    }

    /**
     * Returns the 7 bombs icon.
     * <p>
     *
     * @return the 7 bombs icon
     */
    public ImageIcon getIcon7() {
        return icon7;
    }

    /**
     * Returns the 8 bombs icon.
     * <p>
     *
     * @return the 8 bombs icon
     */
    public ImageIcon getIcon8() {
        return icon8;
    }

    /**
     * Returns the bomb mark flag icon.
     * <p>
     *
     * @return the bomb mark flag icon.
     */
    public ImageIcon getIconMark() {
        return iconMark;
    }

    /**
     * Returns the icon for an undiscovered mine.
     * <p>
     *
     * @return the icon for an undiscovered mine.
     */
    public ImageIcon getIconBombUnfind() {
        return iconBombUnfind;
    }

    /**
     * Returns the icon for a wrong placed mine.
     * <p>
     *
     * @return the icon for a wrong placed mine.
     */
    public ImageIcon getIconBombWrong() {
        return iconBombWrong;
    }

    /**
     * Returns the icon for the question mark.
     * <p>
     *
     * @return the icon for the question mark.
     */
    public ImageIcon getIconQuestionMark() {
        return iconQuestionMark;
    }

    /**
     * Returns the icon used to emphasis a potential mine field.
     * <p>
     *
     * @return the icon used to emphasis a potential mine field.
     */
    public ImageIcon getTryToRevealIcon() {
        return tryToRevealIcon;
    }
}
