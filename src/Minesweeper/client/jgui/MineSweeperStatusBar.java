package Minesweeper.client.jgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 * This class represents the status bar of the application.
 * <p>
 * 
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 13, 2007, 12:30:44 AM
 */
public class MineSweeperStatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static MineSweeperStatusBar INSTANCE = new MineSweeperStatusBar();

	private JTextField mailAddressTextField;

	private JLabel currentTimeLabel;

	private Calendar calendar;

	/**
	 * Returns the single instance of this class.
	 * <p>
	 * 
	 * @return the single instance of this class.
	 */
	public static MineSweeperStatusBar getInstance() {
		return INSTANCE;
	}

	private MineSweeperStatusBar() {
		calendar = Calendar.getInstance();
		setStatusBarLayout();
		initGUIComponents();
		visualSetup();
		placeGUIComponents();
	}

	private void setStatusBarLayout() {
		setLayout(new GridBagLayout());
		((GridBagLayout) getLayout()).columnWidths = new int[] { 0, 0, 0, 0,
				10, 0 };
		((GridBagLayout) getLayout()).rowHeights = new int[] { 20, 0 };
		((GridBagLayout) getLayout()).columnWeights = new double[] { 1.0, 0.0,
				1.0, 0.0, 0.0, 1.0E-4 };
		((GridBagLayout) getLayout()).rowWeights = new double[] { 0.0, 1.0E-4 };
	}

	private void initGUIComponents() {
		mailAddressTextField = new JTextField();
		currentTimeLabel = new JLabel();
	}

	private void visualSetup() {
		setBackground(new Color(204, 204, 204));
		setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(0, 0, 204),
				new Color(204, 204, 255)));

		// ---- mailAddressTextField ----
		mailAddressTextField.setText("soriniulus@yahoo.com");
		mailAddressTextField.setEditable(false);
		mailAddressTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
		mailAddressTextField.setBackground(new Color(204, 204, 204));
		mailAddressTextField.setDisabledTextColor(new Color(0, 0, 102));
		mailAddressTextField.setBorder(null);

		// ---- currentTimeLabel ----
		currentTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		currentTimeLabel.setForeground(new Color(204, 0, 0));
	}

	private void placeGUIComponents() {
		add(mailAddressTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 5), 0, 0));

		add(currentTimeLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 5), 0, 0));
	}

	/**
	 * Set the current system time.
	 *
	 */
	public void setCurrentTime() {
		calendar.setTimeInMillis(System.currentTimeMillis());
		String hour = calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
				+ calendar.get(Calendar.HOUR_OF_DAY) : ""
				+ calendar.get(Calendar.HOUR_OF_DAY);
		String minute = calendar.get(Calendar.MINUTE) < 10 ? "0"
				+ calendar.get(Calendar.MINUTE) : ""
				+ calendar.get(Calendar.MINUTE);
		String second = calendar.get(Calendar.SECOND) < 10 ? "0"
				+ calendar.get(Calendar.SECOND) : ""
				+ calendar.get(Calendar.SECOND);
		//
		currentTimeLabel.setText(hour + ":" + minute + ":" + second);
	}
}
