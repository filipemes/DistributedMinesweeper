package Minesweeper.client.jgui;
/**
 * 
 * @author filipe
 */
public enum Views {
    MAIN_MENU {
        @Override
        public String getPath() {
            return "Views/MainGuiPane.fxml";
        }
    },
    LOGIN {
        @Override
        public String getPath() {
            return "Views/LoginGuiPane.fxml";
        }
    },
    REGISTER {
        @Override
        public String getPath() {
            return "Views/RegisterGuiPane.fxml";
        }
    },
    GAME_LOBBY {
        @Override
        public String getPath() {
            return "Views/LobbyGuiPane.fxml";
        }
    };
    public abstract String getPath();
}
