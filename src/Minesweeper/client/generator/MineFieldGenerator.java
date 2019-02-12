package Minesweeper.client.generator;

import java.io.Serializable;
import java.util.Random;

/**
 * This class is a generator for a minefield configuration. The minefield is a
 * matrix ( width x height ) randomly generated.
 * <p>
 *
 * @author Sorin ( soriniulus@yahoo.com ) At: Apr 5, 2007, 7:42:27 PM
 */
public class MineFieldGenerator implements Serializable {

    private int width;

    private int height;

    private int bombsNumber;

    private int[][] mineField;

    /**
     * This constructor generates a minefield configuration for a given width
     * and height
     * <p>
     *
     * @param width the width of the minefield
     * @param height the height of the minefield
     * @param bombsNumber the number of bombs for this minefield
     */
    public MineFieldGenerator(int width, int height, int bombsNumber) {
        this.width = width;
        this.height = height;
        this.bombsNumber = bombsNumber;
        //
        generateMinefield();
    }

    /**
     * Generate the minefield matrix.
     *
     */
    private void generateMinefield() {
        mineField = new int[height][width];
        int[] bombsCoordinates = new int[bombsNumber];
        boolean alreadyGenerated = false;
        //
        for (int i = 0; i < bombsNumber; i++) {
            do {
                alreadyGenerated = false;
                bombsCoordinates[i] = Math.abs(new Random().nextInt()
                        % (width * height));
                for (int j = 0; j < i; j++) {
                    if (bombsCoordinates[i] == bombsCoordinates[j]) {
                        alreadyGenerated = true;
                        break;
                    }
                }
            } while (alreadyGenerated);

        }
        //
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mineField[i][j] = 0;
            }
        }
        //
        for (int i = 0; i < bombsNumber; i++) {
            mineField[bombsCoordinates[i] / width][bombsCoordinates[i] % width] = -1;
        }
        //
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (mineField[i][j] != -1) {
                    mineField[i][j] = getNumberOfBombsForCell(i, j);
                }
            }
        }
    }

    /**
     * Returns the number of neighbour bombs for this cell
     * <p>
     *
     * @param row the row index
     * @param column the column index
     * @return the number of neighbour bombs for this cell
     */
    private int getNumberOfBombsForCell(int row, int column) {
        int n = 0;
        if (row == 0) {
            if (column == 0) {
                if (mineField[row][column + 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column] == -1) {
                    n++;
                }
                if (mineField[row + 1][column + 1] == -1) {
                    n++;
                }
            } else if (column == width - 1) {
                if (mineField[row][column - 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column] == -1) {
                    n++;
                }
            } else {
                if (mineField[row][column - 1] == -1) {
                    n++;
                }
                if (mineField[row][column + 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column] == -1) {
                    n++;
                }
                if (mineField[row + 1][column + 1] == -1) {
                    n++;
                }
            }
        } else if (row == height - 1) {
            if (column == 0) {
                if (mineField[row][column + 1] == -1) {
                    n++;
                }
                if (mineField[row - 1][column] == -1) {
                    n++;
                }
                if (mineField[row - 1][column + 1] == -1) {
                    n++;
                }
            } else if (column == width - 1) {
                if (mineField[row][column - 1] == -1) {
                    n++;
                }
                if (mineField[row - 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row - 1][column] == -1) {
                    n++;
                }
            } else {
                if (mineField[row][column - 1] == -1) {
                    n++;
                }
                if (mineField[row][column + 1] == -1) {
                    n++;
                }
                if (mineField[row - 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row - 1][column] == -1) {
                    n++;
                }
                if (mineField[row - 1][column + 1] == -1) {
                    n++;
                }
            }
        } else {
            if (column == 0) {
                if (mineField[row - 1][column] == -1) {
                    n++;
                }
                if (mineField[row - 1][column + 1] == -1) {
                    n++;
                }
                if (mineField[row][column + 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column + 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column] == -1) {
                    n++;
                }
            } else if (column == width - 1) {
                if (mineField[row - 1][column] == -1) {
                    n++;
                }
                if (mineField[row - 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row][column - 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column] == -1) {
                    n++;
                }
            } else {
                if (mineField[row - 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row - 1][column] == -1) {
                    n++;
                }
                if (mineField[row - 1][column + 1] == -1) {
                    n++;
                }
                if (mineField[row][column - 1] == -1) {
                    n++;
                }
                if (mineField[row][column + 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column - 1] == -1) {
                    n++;
                }
                if (mineField[row + 1][column] == -1) {
                    n++;
                }
                if (mineField[row + 1][column + 1] == -1) {
                    n++;
                }
            }
        }
        return n;
    }

    /**
     * Rturn the generated minefield configuration.
     * <p>
     *
     * @return the generated minefield configuration.
     */
    public int[][] getGeneratedMinefield() {
        return mineField;
    }

}
