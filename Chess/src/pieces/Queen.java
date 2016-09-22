package pieces;

import board.Board;
import board.Square;
import runner.Position;

import java.util.ArrayList;

/**
 * Created by Brian on 15/12/12.
 */
public class Queen extends Piece {
    private boolean done = false;

// Constructor
    public Queen(String color, Position position, Board board) {
        super(color, position, board);
        value = 9;

        if(color.equals("white")) {
            image = "chessImages/WhiteQueen.png";
        } else {
            image = "chessImages/BlackQueen.png";
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

        // Left
        tempCol = col;
        tempCol--;
        done = false;
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
        tempRow = row;
        tempRow--;
        done = false;
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

        // Left up
        int tempCol = col;
        int tempRow = row;
        tempCol--;
        tempRow--;
        done = false;
        checkContinue = false;
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
        checkContinue = false;
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
        checkContinue = false;
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
        checkContinue = false;
        while(isValidEatingMoveEating(new Position(tempRow,tempCol))) {
            list.add(Board.grid[tempRow][tempCol]);
            tempCol++;
            tempRow++;
            if(done) {
                done = false;
                break;
            }

        }

        // Left
        tempCol = col;
        tempCol--;
        done = false;
        checkContinue = false;
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
        checkContinue = false;
        while(isValidEatingMoveEating(new Position(row, tempCol))) {
            list.add(Board.grid[row][tempCol]);
            tempCol++;
            if(done) {
                done = false;
                break;
            }

        }

        // Up
        tempRow = row;
        tempRow--;
        done = false;
        checkContinue = false;
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
        checkContinue = false;
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



        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {

            // The only difference
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
}
