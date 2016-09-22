package ai;

import board.Square;

/**
 * Created by Brian on 15/12/21.
 */
public class Move {
    private Square before;
    private Square after;

// Consturctor
    public Move(Square before, Square after) {
        this.before = before;
        this.after = after;
    }

    public void makeMove(Square[][] boardGrid) {

    }

// Getters and Setters
    public Square getAfter() {
        return after;
    }

    public void setAfter(Square after) {
        this.after = after;
    }

    public Square getBefore() {
        return before;
    }

    public void setBefore(Square before) {
        this.before = before;
    }
}
