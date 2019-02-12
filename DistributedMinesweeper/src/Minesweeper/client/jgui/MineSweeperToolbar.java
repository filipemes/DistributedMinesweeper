package Minesweeper.client.jgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import Minesweeper.client.MinesweeperClient;

/**
 * This class represents the toolbar of the application. The toolbar provides
 * informations about the number of bombs left, and the time ellapsed from since
 * the game started.
 * <p>
 *
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 11, 2007, 10:15:33 AM
 */
public class MineSweeperToolbar extends JToolBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static MineSweeperToolbar INSTANCE = new MineSweeperToolbar();

    private JLabel bombLabel;

    private JLabel bombsLeftLabel;

    private JButton newGameButton;

    private JLabel watchLabel;

    private JLabel timeEllapsedLabel;

    private int timeEllapsed;

    /**
     * Returns the single instance of this class.
     * <p>
     *
     * @return the single instance of this class.
     */
    public static MineSweeperToolbar getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor. This class cannot be instanciated more than once.
     *
     */
    private MineSweeperToolbar() {
        init();
    }

    private void init() {
        setFloatable(false);
        setToolbarLayout();
        initGUIComponents();
        visualSetup();
        placeGUIComponents();
        //addListeners();
    }

    private void setToolbarLayout() {
        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{10, 45, 0, 0,
            45, 0, 45, 0, 5, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{10, 45, 5, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 0.0,
            0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0,
            0.0, 1.0E-4};

    }

    private void initGUIComponents() {
        bombLabel = new JLabel();
        bombsLeftLabel = new JLabel();
        newGameButton = new JButton();
        watchLabel = new JLabel();
        timeEllapsedLabel = new JLabel();
    }

    private void visualSetup() {

        Font font = new Font("Tahoma", Font.PLAIN, 30);

        // ---- bombLabel ----
        bombLabel.setIcon(new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/grenade.png")));

        //---- bombsLeftLabel ----
        bombsLeftLabel.setForeground(Color.red);
        bombsLeftLabel.setFont(font);

        /*//---- newGameButton ----
//		newGameButton.setPreferredSize( new Dimension( 40, 40 ) );
        newGameButton.setContentAreaFilled(false);
        newGameButton.setIcon(new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/new_game.png")));
        newGameButton.setBorder(null);*/
        //---- watchLabel ----
        watchLabel.setIcon(new ImageIcon(getClass().getResource(MinesweeperClient.absPathToResourceIcons + "/clock.png")));

        //---- timeEllapsedLabel ----
        timeEllapsedLabel.setFont(font);
        timeEllapsedLabel.setForeground(Color.red);
    }

    private void placeGUIComponents() {
        //---- bombLabel ----
        add(bombLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- bombsLeftLabel ----
        add(bombsLeftLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 5), 0, 0));

        /*//---- newGameButton ----
        add(newGameButton, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 5), 0, 0));*/
        //---- watchLabel ----
        add(watchLabel, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- timeEllapsedLabel ----
        add(timeEllapsedLabel, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 5), 0, 0));
    }

    /**
     * Sets the number of mines left undiscovered in the minefield.
     * <p>
     * @param minesLeft	the number of mines left undiscovered in the minefield.
     */
    public void setMinesLeft(int minesLeft) {
        String strMinesLeft = String.valueOf(minesLeft);
        if (minesLeft >= 0) {
            while (strMinesLeft.length() < 3) {
                strMinesLeft = "0" + strMinesLeft;
            }
        }
        bombsLeftLabel.setText(strMinesLeft);
    }

    /**
     * Initialize the clock when a new game is started.
     *
     */
    public void initClock() {
        timeEllapsedLabel.setText("000");
        timeEllapsed = 0;
    }

    /**
     * Update the time ellapsed since the game started. If the application is
     * iconified, then the time will "stay still".
     *
     */
    public void ticTac() {
        if (GuiManager.getInstance().isMainFrameIconified()) {
            return;
        }
        timeEllapsed++;
        String str = String.valueOf(timeEllapsed);
        while (str.length() < 3) {
            str = "0" + str;
        }
        timeEllapsedLabel.setText(str);
    }

    /**
     * Returns the time ellapsed since the game started.
     * <p>
     *
     * @return	the time ellapsed since the game started.
     */
    public int getTimeEllapsed() {
        return timeEllapsed;
    }

}
