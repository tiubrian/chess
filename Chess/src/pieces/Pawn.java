package pieces;

import board.Board;
import board.Square;
import runner.Position;

import java.util.ArrayList;

/**
 * Created by Brian on 15/12/12.
 */
public class Pawn extends Piece {
    private boolean moved = false;

// Constructor
    public Pawn(String color, Position position, Board board) {
        super(color, position, board);
        value = 1;
        if(color.equals("white")) {
            image = "chessImages/WhitePawn.png";
        } else {
            image = "chessImages/BlackPawn.png";
        }
    }

    public boolean isValidMove(Position tempPosition) {
        int row = tempPosition.getRow();
        int col = tempPosition.getCol();


        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {


                if(Board.grid[row][col].getPiece() == null) {
                    if(willPutKingInCheck(Board.grid[tempPosition.getRow()][tempPosition.getCol()])) {
                        return  false;
                    }
                    return true;
                } else {
                    return false;
                }


        }
        return false;
    }

    private boolean isValidMoveEating(Position tempPosition) {
        int row = tempPosition.getRow();
        int col = tempPosition.getCol();


        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {
            if(Board.grid[row][col].getPiece() == null) {
                return false;
            } else if(Board.grid[row][col].getPiece().getColor().equals(color)) {
                return false;
            } else {
                if(willPutKingInCheck(Board.grid[tempPosition.getRow()][tempPosition.getCol()])) {
                    return  false;
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public ArrayList<Square> getPossiblePositions() {
        int row = position.getRow();
        int col = position.getCol();
        ArrayList<Square> list = new ArrayList<Square>();

        // Normal position
        if(Board.directionUp && color.equals("white") ||
                !Board.directionUp && color.equals("black")) {
            if(isValidMove(new Position(row-1,col))) {
                list.add(Board.grid[row-1][col]);
            }

            if(!moved) {
                if(isValidMove(new Position(row-2,col)) &&
                        Board.grid[row-1][col].getPiece() == null) {
                    list.add(Board.grid[row-2][col]);
                }
            }

            // Eating
            if(isValidMoveEating(new Position(row-1,col+1))) {
                list.add(Board.grid[row-1][col+1]);
            }
            if(isValidMoveEating(new Position(row-1,col-1))) {
                list.add(Board.grid[row-1][col-1]);
            }

        }

        // Reversed position
        else {
            if(isValidMove(new Position(row+1,col))) {
                list.add(Board.grid[row+1][col]);
            }

            if(!moved) {
                if(isValidMove(new Position(row+2,col)) &&
                        Board.grid[row+1][col].getPiece() == null) {
                    list.add(Board.grid[row+2][col]);
                }
            }

            // Eating
            if(isValidMoveEating(new Position(row+1,col+1))) {
                list.add(Board.grid[row+1][col+1]);
            }
            if(isValidMoveEating(new Position(row+1,col-1))) {
                list.add(Board.grid[row+1][col-1]);
            }
        }

        return list;
    }

    @Override
    public ArrayList<Square> getPossibleAttackingPositions() {
        int row = position.getRow();
        int col = position.getCol();
        ArrayList<Square> list = new ArrayList<Square>();


        // Normal position
        if(Board.directionUp && color.equals("white") ||
                !Board.directionUp && color.equals("black")) {


            // Eating
            if(isValidEatingMoveEating(new Position(row-1,col+1))) {
                list.add(Board.grid[row-1][col+1]);
            }
            if(isValidEatingMoveEating(new Position(row-1,col-1))) {
                list.add(Board.grid[row-1][col-1]);
            }

        }

        // Reversed position
        else {

            // Eating
            if(isValidEatingMoveEating(new Position(row+1,col+1))) {
                list.add(Board.grid[row+1][col+1]);
            }
            if(isValidEatingMoveEating(new Position(row+1,col-1))) {
                list.add(Board.grid[row+1][col-1]);
            }
        }

        return list;
    }

    @Override
    public boolean isValidEatingMoveEating(Position tempPosition) {
        int row = tempPosition.getRow();
        int col = tempPosition.getCol();


        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {
             return true;
        }

        return false;
    }
// Getter and Setter
public boolean isMoved() {
    return moved;
}

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}
