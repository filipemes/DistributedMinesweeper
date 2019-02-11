package Minesweeper.server;

import java.io.Serializable;
import java.sql.Timestamp;


public class Move implements Serializable,Comparable{
    
    private int row;
    private int column;
    private Player currentPlayer;
    private final String typeOfClick;
    private final int numberOfButtonsRevealed;
    private final Timestamp timeStamp;
    public final static String RIGHT_CLICK="RIGHT";
    public final static String LEFT_CLICK="LEFT";

    public Move(int row, int column, int numberOfButtonsRevealed, Player currentPlayer, String typeOfClick) {
        this.row = row;
        this.column = column;
        this.numberOfButtonsRevealed = numberOfButtonsRevealed;
        this.currentPlayer = currentPlayer;
        this.typeOfClick = typeOfClick;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * @return the row
     */
    public int getRow() {
        return this.row;
    }

    public int getNumberOfButtonsRevealed() {
        return this.numberOfButtonsRevealed;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return this.column;
    }
    /**
     * 
     * @return Player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    /**
     * 
     * @return 
     */
    public String getTypeOfClick() {
        return this.typeOfClick;
    }
    
    @Override
    public String toString() {
        return "GameState{" + "row=" + row + ", column=" + column + ", currentPlayer=" + currentPlayer + ", typeOfClick=" + typeOfClick + ", numberOfButtonsRevealed=" + numberOfButtonsRevealed + '}';
    }

    @Override
    public int compareTo(Object t) {
        if (((Move) t).timeStamp.equals(this.timeStamp)) {
            return 0;
        }
        return -1;
    }
}
