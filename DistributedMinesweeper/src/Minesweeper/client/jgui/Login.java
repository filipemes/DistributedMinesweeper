package Minesweeper.client.jgui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public class Login {

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void onClickGoBackButton(MouseEvent event) {
        GuiManager.getInstance().displayMainMenuPane();
    }

    @FXML
    public void onClickSubmitLoginButton(MouseEvent event) {
        Window owner = userNameTextField.getScene().getWindow();
        if (userNameTextField.getText().length() > 0 && passwordTextField.getText().length() > 0) {
            int stateLogin = GuiManager.getInstance().getMinesweeperClient().login(userNameTextField.getText(), passwordTextField.getText());
            switch (stateLogin) {
                case -1:
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Form",
                            "There was an error connecting to the server. Please restart the service.");
                    break;
                case 0:
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Form",
                            "User credentials are not valid. Please try again.");
                    break;
                case 1:
                    GuiManager.getInstance().displayGameLobbyPane();
                    break;
            }
        } else if (userNameTextField.getText().length() > 0 && passwordTextField.getText().length() == 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Form",
                    "Please enter your username.");
        } else if (userNameTextField.getText().length() == 0 && passwordTextField.getText().length() > 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Form",
                    "Please enter the password.");
        }
        passwordTextField.setText("");
        userNameTextField.setText("");
    }

}
