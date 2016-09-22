package pieces;

import board.Board;
import board.Square;
import runner.Position;

import java.util.ArrayList;

/**
 * Created by Brian on 15/12/12.
 */
public class Knight extends Piece {

// Constructor
    public Knight(String color, Position position, Board board) {
        super(color, position, board);
        value = 3;
        if(color.equals("white")) {
            image = "chessImages/WhiteKnight.png";
        } else {
            image = "chessImages/BlackKnight.png";
        }
    }


    @Override
    public boolean isValidEatingMoveEating(Position tempPosition) {
        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {

            // The only difference is it doesn't check if it's in check

            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
                if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() instanceof King) {
                    return true;
                }


                if(!Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color))
                    return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public ArrayList<Square> getPossibleAttackingPositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();


        // Try all 8 combinations
        if(isValidEatingMoveEating(new Position(row - 2, col - 1))) {
            list.add(Board.grid[row-2][col-1]);
        }
        if(isValidEatingMoveEating(new Position(row - 2, col + 1))) {
            list.add(Board.grid[row-2][col+1]);
        }
        if(isValidEatingMoveEating(new Position(row + 2, col - 1))) {
            list.add(Board.grid[row+2][col-1]);
        }
        if(isValidEatingMoveEating(new Position(row + 2, col + 1))) {
            list.add(Board.grid[row+2][col+1]);
        }
        if(isValidEatingMoveEating(new Position(row - 1, col - 2))) {
            list.add(Board.grid[row-1][col-2]);
        }
        if(isValidEatingMoveEating(new Position(row + 1, col - 2))) {
            list.add(Board.grid[row+1][col-2]);
        }
        if(isValidEatingMoveEating(new Position(row - 1, col + 2))) {
            list.add(Board.grid[row-1][col+2]);
        }
        if(isValidEatingMoveEating(new Position(row + 1, col + 2))) {
            list.add(Board.grid[row+1][col+2]);
        }

        return list;
    }

    @Override
    public ArrayList<Square> getPossiblePositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();


        // Try all 8 combinations
        if(isValidMove(new Position(row - 2, col - 1))) {
            list.add(Board.grid[row-2][col-1]);
        }
        if(isValidMove(new Position(row - 2, col + 1))) {
            list.add(Board.grid[row-2][col+1]);
        }
        if(isValidMove(new Position(row + 2, col - 1))) {
            list.add(Board.grid[row+2][col-1]);
        }
        if(isValidMove(new Position(row + 2, col + 1))) {
            list.add(Board.grid[row+2][col+1]);
        }
        if(isValidMove(new Position(row - 1, col - 2))) {
            list.add(Board.grid[row-1][col-2]);
        }
        if(isValidMove(new Position(row + 1, col - 2))) {
            list.add(Board.grid[row+1][col-2]);
        }
        if(isValidMove(new Position(row - 1, col + 2))) {
            list.add(Board.grid[row-1][col+2]);
        }
        if(isValidMove(new Position(row + 1, col + 2))) {
            list.add(Board.grid[row+1][col+2]);
        }

        return list;
    }





}
