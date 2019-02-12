package Minesweeper.client.jgui;

import Minesweeper.server.Player;
import Minesweeper.server.SubjectGameRI;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
/**
 * 
 * @author filipe
 */
public class LobbyGame implements Initializable {

    @FXML
    private ListView<String> listViewLoggedPlayers;

    @FXML
    private ComboBox levelComboBox;

    @FXML
    private TextField numberOfPlayersTextField;

    @FXML
    private TextField messageTextField;

    @FXML
    private TableView<Game> listGamesTable = new TableView<Game>();

    @FXML
    private ListView<String> listViewChat;

    @FXML
    private TableColumn<Game, String> timestampTableColumn = new TableColumn<Game, String>();

    @FXML
    private TableColumn<Game, String> levelTableColumn = new TableColumn<Game, String>();

    @FXML
    private TableColumn<Game, Integer> maxPlayersTableColumn = new TableColumn<Game, Integer>();

    @FXML
    private TableColumn<Game, Integer> currentPlayersTableColumn = new TableColumn<Game, Integer>();

    ObservableList messagesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initLevelComboBox();
            ArrayList<Player> players = GuiManager.getInstance().getMinesweeperClient().getObserverLobbyRI().getLobbySessionRI().getAllLoggedPlayers();;
            updateLoggedPlayers(players);
            ArrayList<SubjectGameRI> allGames = GuiManager.getInstance().getMinesweeperClient().getObserverLobbyRI().getLobbySessionRI().getAllGames();
            initTableView();
            updateNewGames(allGames);
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initLevelComboBox() {
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "JUNIOR",
                        "MEDIUM",
                        "EXPERT"
                );
        levelComboBox.setItems(options);
        levelComboBox.setValue("JUNIOR");
    }

    public void initTableView() {
        timestampTableColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("timestamp"));
        levelTableColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("level"));
        currentPlayersTableColumn.setCellValueFactory(new PropertyValueFactory<Game, Integer>("currentPlayers"));
        maxPlayersTableColumn.setCellValueFactory(new PropertyValueFactory<Game, Integer>("maxPlayers"));
        listGamesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!GuiManager.getInstance().isJoinedAGame()) {
                    int i = listGamesTable.getSelectionModel().getSelectedIndex();
                    ObservableList<Game> items = listGamesTable.getItems();
                    GuiManager.getInstance().selectGame(Timestamp.valueOf(items.get(i).getTimestamp()));
                }
            }
        });

    }

    @FXML
    public void onClickSendMessageButton(MouseEvent event) {
        if (!GuiManager.getInstance().isJoinedAGame()) {
            try {
                GuiManager.getInstance().getMinesweeperClient().getObserverLobbyRI().getLobbySessionRI().sendMessage(GuiManager.getInstance().getMinesweeperClient().getPlayer().getUsername() + " - " + messageTextField.getText());
                messageTextField.setText("");
            } catch (RemoteException ex) {
                Logger.getLogger(LobbyGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void onClickLogOutButton(MouseEvent event) {
        if (!GuiManager.getInstance().isJoinedAGame()) {
            GuiManager.getInstance().logOut();
        }
    }

    @FXML
    public void onClickNewGameButton(MouseEvent event) {
        Window owner = levelComboBox.getScene().getWindow();
        String gameMode = (String) levelComboBox.getValue();
        if (!GuiManager.getInstance().isJoinedAGame()) {
            if (gameMode.length() > 0 && Character.isDigit(numberOfPlayersTextField.getText().toCharArray()[0])) {
                int numberOfPlayers = 0;
                numberOfPlayers = Integer.parseInt(numberOfPlayersTextField.getText());
                if (numberOfPlayers > 1 && numberOfPlayers < 5) {
                    GuiManager.getInstance().newGame(gameMode.toUpperCase(), numberOfPlayers);
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "New Game Form",
                            "The number of players must be greater than 1 and less than 5. Please try again.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "New Game Form",
                        "Please enter the number of people.");

            }
        }
    }

    public void updateLoggedPlayers(ArrayList<Player> players) {
        ObservableList data = FXCollections.observableArrayList();
        for (Player p : players) {
            data.add(p.getUsername().toUpperCase());
        }
        listViewLoggedPlayers.setItems(data);
    }

    public void updateNewGames(ArrayList<SubjectGameRI> games) {
        ObservableList<Game> data = FXCollections.observableArrayList();
        for (SubjectGameRI sub : games) {
            try {
                data.add(new Game(sub.getTimestamp(), sub.getInitialGameMode(), sub.getCurrentNumberOfPlayer(), sub.getMaxPlayersGame()));
            } catch (RemoteException ex) {
                Logger.getLogger(LobbyGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        listGamesTable.setItems(data);
    }

    public void updateNewMessage(String message) {
        messagesList.add(message);
        listViewChat.setItems(messagesList);
    }

}
