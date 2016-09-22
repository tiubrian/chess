package pieces;

import board.Board;
import board.Square;
import runner.Position;

import java.util.ArrayList;

/**
 * Created by Brian on 15/12/12.
 */
public class Rook extends Piece {
    private boolean done = false;
    private boolean moved = false;
// Constructor
    public Rook(String color, Position position, Board board) {
        super(color, position, board);
        value = 5;

        if(color.equals("white")) {
            image = "chessImages/WhiteRook.png";
        } else {
            image = "chessImages/BlackRook.png";
        }
    }



    @Override
    public ArrayList<Square> getPossiblePositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();

        // Left
        int tempCol = col;
        tempCol--;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(row, tempCol)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[row][tempCol]);
            }
            checkContinue = false;
            tempCol--;
            if(done) {
                done = false;
                break;
            }
        }

        // Right
        tempCol = col;
        tempCol++;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(row, tempCol)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[row][tempCol]);
            }
            checkContinue = false;
            tempCol++;
            if(done) {
                done = false;
                break;
            }

        }

        // Up
        int tempRow = row;
        tempRow--;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(tempRow, col)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[tempRow][col]);
            }
            checkContinue = false;
            tempRow--;
            if(done) {
                done = false;
                break;
            }

        }

        // Down
        tempRow = row;
        tempRow++;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(tempRow, col)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[tempRow][col]);
            }
            checkContinue = false;
            tempRow++;
            if(done) {
                done = false;
                break;
            }
        }

        return list;
    }

    @Override
    public ArrayList<Square> getPossibleAttackingPositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();

        // Left
        int tempCol = col;
        tempCol--;
        done = false;
        while(isValidEatingMoveEating(new Position(row, tempCol))) {
            list.add(Board.grid[row][tempCol]);
            tempCol--;
            if(done) {
                done = false;
                break;
            }
        }

        // Right
        tempCol = col;
        tempCol++;
        done = false;
        while(isValidEatingMoveEating(new Position(row, tempCol))) {
            list.add(Board.grid[row][tempCol]);
            tempCol++;
            if(done) {
                done = false;
                break;
            }

        }

        // Up
        int tempRow = row;
        tempRow--;
        done = false;
        while(isValidEatingMoveEating(new Position(tempRow, col))) {
            list.add(Board.grid[tempRow][col]);
            tempRow--;
            if(done) {
                done = false;
                break;
            }

        }

        // Down
        tempRow = row;
        tempRow++;
        done = false;
        while(isValidEatingMoveEating(new Position(tempRow, col))) {
            list.add(Board.grid[tempRow][col]);
            tempRow++;
            if(done) {
                done = false;
                break;
            }
        }

        return list;
    }


    @Override
    public boolean isValidMove(Position tempPosition) {
        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {


            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
                done = true;
                if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color))
                    return false;
            }
            if(willPutKingInCheck(Board.grid[tempPosition.getRow()][tempPosition.getCol()])) {
                checkContinue = true;
                return  false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidEatingMoveEating(Position tempPosition) {
        // The only difference



        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {
            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() instanceof King && !Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color)) {
                return true;
            }
            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
                done = true;
                if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color)) {
                    done = true;
                    return true;
                }
            }
            return true;
        }
        return false;
    }

// Getters and Setters
    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}
