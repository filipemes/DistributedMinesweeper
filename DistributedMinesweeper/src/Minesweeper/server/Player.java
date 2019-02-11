package Minesweeper.server;

import java.io.Serializable;

public class Player implements Comparable, Serializable {

    private final String username;

    private final int id;

    private final int gamesWon;

    private final int gamesLost;

    public Player(int id, String username, int gameswon, int gameslost) {
        this.id = id;
        this.username = username;
        this.gamesWon = gameswon;
        this.gamesLost = gameslost;
    }

    public int getTotalGames() {
        return this.gamesLost + this.gamesWon;
    }

    public String getUsername() {
        return this.username;
    }

    public int getRanking() {
        return 0;
    }

    public String toString() {
        return "UserName:" + this.username + " Games Lost:" + this.gamesLost + " Games Won:" + this.gamesWon;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int compareTo(Object t) {
        Player p = (Player) t;
        return this.username.compareTo(p.getUsername());
    }

}
