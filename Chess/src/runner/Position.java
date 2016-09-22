package runner;

/**
 * Created by Brian on 15/12/12.
 */
public class Position implements Cloneable {
    private int row;
    private int col;

// Constructor
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

// Getters and Setters
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

// Overrides
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
