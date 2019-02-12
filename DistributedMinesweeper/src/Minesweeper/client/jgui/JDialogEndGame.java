package Minesweeper.client.jgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JDialogEndGame extends JDialog {

    public JDialogEndGame(JFrame j, boolean isGameWon) {
        super(j, true);
        this.setLayout(new FlowLayout());
        JButton b = new JButton("OK");
        b.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                GuiManager.getInstance().clearGamePane();
            }
        });
        JLabel label = null;
        if (isGameWon) {
            label = new JLabel("You won the game.");
        } else {
            label = new JLabel("You lost the game.");
        }
        this.add(label);
        this.add(b);
        this.setSize(300, 300);
        this.setVisible(true);
    }

}
