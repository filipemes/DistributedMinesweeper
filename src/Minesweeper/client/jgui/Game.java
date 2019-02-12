package Minesweeper.client.jgui;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * 
 * @author filipe
 */
public class Game {

    private final StringProperty timestamp;
    private final StringProperty level;
    private final IntegerProperty currentPlayers;
    private final IntegerProperty maxPlayers;

    public Game(Timestamp timestamp, String level, int currentPlayers, int maxPlayers) {
        this.timestamp = new SimpleStringProperty(timestamp.toString());
        this.level = new SimpleStringProperty(level);
        this.currentPlayers = new SimpleIntegerProperty(currentPlayers);
        this.maxPlayers = new SimpleIntegerProperty(maxPlayers);

    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public String getLevel() {
        return level.get();
    }

    public Integer getCurrentPlayers() {
        return currentPlayers.get();
    }

    public Integer getMaxPlayers() {
        return maxPlayers.get();

    }

    @Override
    public String toString() {
        return "Game{" + "timestamp=" + timestamp + ", level=" + level + ", currentPlayers=" + currentPlayers + ", maxPlayers=" + maxPlayers + '}';
    }

    
}