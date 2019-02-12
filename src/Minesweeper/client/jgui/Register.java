package Minesweeper.client.jgui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
/**
 * 
 * @author filipe
 */
public class Register {

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void onClickGoBackButton(MouseEvent event) {
        GuiManager.getInstance().displayMainMenuPane();
    }

    @FXML
    public void onClickSubmitRegisterButton(MouseEvent event) {
        Window owner = userNameTextField.getScene().getWindow();
        if (userNameTextField.getText().length() > 0 && passwordTextField.getText().length() > 0) {
            int stateRegister = GuiManager.getInstance().getMinesweeperClient().register(userNameTextField.getText(), passwordTextField.getText());
            switch (stateRegister) {
                case -1:
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Form",
                            "There was an error connecting to the server. Please restart the service.");
                    break;
                case 0:
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Form",
                            "This credential is already associated with a different user. Please try again.");
                    break;
                case 1:
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Form",
                            "Your registration was successful.");
                    break;
            }
        } else if (userNameTextField.getText().length() > 0 && passwordTextField.getText().length() == 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Registration Form",
                    "Please enter your username.");
        } else if (userNameTextField.getText().length() == 0 && passwordTextField.getText().length() > 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Registration Form",
                    "Please enter the password.");
        }
        userNameTextField.setText("");
        passwordTextField.setText("");
    }

}
