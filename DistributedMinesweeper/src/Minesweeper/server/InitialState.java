package Minesweeper.server;

import Minesweeper.client.generator.MineFieldGenerator;
import java.io.Serializable;


public class InitialState implements Serializable{

    private MineFieldGenerator mineFieldGenerator;
    private String gameMode;

    public InitialState(MineFieldGenerator mineFieldGenerator, String gameMode) {
        this.mineFieldGenerator = mineFieldGenerator;
        this.gameMode = gameMode;
    }

    public MineFieldGenerator getMineFieldGenerator() {
        return this.mineFieldGenerator;
    }

    public String getGameMode() {
        return this.gameMode;
    }
}
