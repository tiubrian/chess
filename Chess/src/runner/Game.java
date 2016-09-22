package runner;

import board.Board;

/**
 * Created by Brian on 15/12/12.
 */
public class Game {
    private int cellSize;
    private String turn = "white";

    private Board theBoard;

// Constructor
    public Game(int cellSize) {
        this.cellSize = cellSize;
    }

    public void newGame() {
        theBoard = new Board(cellSize, this);
    }







}
