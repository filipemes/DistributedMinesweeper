package Minesweeper.client.jgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
/**
 * 
 * @author filipe
 */
public class MainMenu {

    @FXML
    private Button loginButton;

    @FXML
    private void onClickLoginButton(MouseEvent event) {
        GuiManager.getInstance().displayLoginPane();
    }

    @FXML
    private void onClickRegisterButton(MouseEvent event) {
        GuiManager.getInstance().displayRegisterPane();
    }

}
