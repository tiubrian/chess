package pieces;

import board.Board;
import board.Square;
import runner.Position;

import java.util.ArrayList;

/**
 * Created by Brian on 15/12/12.
 */
public class Bishop extends Piece {
    private boolean done = false;
// Constructor
    public Bishop(String color, Position position, Board board) {
        super(color, position,board);
        value = 3;

        if(color.equals("white")) {
            image = "chessImages/WhiteBishop.png";
        } else {
            image = "chessImages/BlackBishop.png";
        }
    }

    @Override
    public ArrayList<Square> getPossiblePositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();

        // Left up
        int tempCol = col;
        int tempRow = row;
        tempCol--;
        tempRow--;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(tempRow,tempCol)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[tempRow][tempCol]);
            }
            checkContinue = false;

            tempCol--;
            tempRow--;
            if(done) {
                done = false;
                break;
            }
        }

        // Right up
        tempCol = col;
        tempRow = row;
        tempCol++;
        tempRow--;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(tempRow,tempCol)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[tempRow][tempCol]);
            }
            checkContinue = false;

            tempCol++;
            tempRow--;
            if(done) {
                done = false;
                break;
            }
        }

        // Down left
        tempCol = col;
        tempRow = row;
        tempCol--;
        tempRow++;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(tempRow,tempCol)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[tempRow][tempCol]);
            }
            checkContinue = false;

            tempCol--;
            tempRow++;
            if(done) {
                done = false;
                break;
            }
        }

        // Down right
        tempCol = col;
        tempRow = row;
        tempCol++;
        tempRow++;
        done = false;
        checkContinue = false;
        while(isValidMove(new Position(tempRow,tempCol)) || checkContinue) {
            if(!checkContinue) {
                list.add(Board.grid[tempRow][tempCol]);
            }
            checkContinue = false;

            tempCol++;
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

        // Left up
        int tempCol = col;
        int tempRow = row;
        tempCol--;
        tempRow--;
        done = false;
        while(isValidEatingMoveEating(new Position(tempRow,tempCol))) {
            list.add(Board.grid[tempRow][tempCol]);
            tempCol--;
            tempRow--;
            if(done) {
                done = false;
                break;
            }
        }

        // Right up
        tempCol = col;
        tempRow = row;
        tempCol++;
        tempRow--;
        done = false;
        while(isValidEatingMoveEating(new Position(tempRow,tempCol))) {
            list.add(Board.grid[tempRow][tempCol]);
            tempCol++;
            tempRow--;
            if(done) {
                done = false;
                break;
            }
        }

        // Down left
        tempCol = col;
        tempRow = row;
        tempCol--;
        tempRow++;
        done = false;
        while(isValidEatingMoveEating(new Position(tempRow,tempCol))) {
            list.add(Board.grid[tempRow][tempCol]);
            tempCol--;
            tempRow++;
            if(done) {
                done = false;
                break;
            }
        }

        // Down right
        tempCol = col;
        tempRow = row;
        tempCol++;
        tempRow++;
        done = false;
        while(isValidEatingMoveEating(new Position(tempRow,tempCol))) {
            list.add(Board.grid[tempRow][tempCol]);
            tempCol++;
            tempRow++;
            if(done) {
                done = false;
                break;
            }

        }

        return list;
    }

    public boolean isValidMove(Position tempPosition) {
        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {

            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
                done = true;
                if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color)) {

                    return false;
                }
            }
            if(willPutKingInCheck(Board.grid[tempPosition.getRow()][tempPosition.getCol()])) {

                checkContinue = true;
                return false;
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


}
