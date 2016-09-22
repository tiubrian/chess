package pieces;

import board.Board;
import board.Square;
import runner.Position;

import java.io.PipedWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * Created by Brian on 15/12/12.
 */
public class King extends Piece {
    private boolean casteled = false;
    private boolean moved = false;
    private boolean castleProcess = false;
// Constructor
    public King(String color, Position position, Board board) {
        super(color, position, board);
        value = 500;
        if(color.equals("white")) {
            image = "chessImages/WhiteKing.png";
        } else {
            image = "chessImages/BlackKing.png";
        }
    }

    private boolean whiteChecking(Position tempPosition) {

        for(Piece tempPiece : Board.whitePieces) {
            ArrayList<Square> possible;

            // Special cases
            if(!(tempPiece instanceof King) &&
                    !(tempPiece instanceof Pawn)) {
                possible = tempPiece.getPossibleAttackingPositions();
            } else {
                possible = (tempPiece).getPossibleAttackingPositions();
            }



            for(Square tempSquare : possible) {
                if(tempPosition.getRow() == tempSquare.getPosition().getRow() &&
                        tempPosition.getCol() == tempSquare.getPosition().getCol()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean blackChecking(Position tempPosition) {
        for(Piece tempPiece : Board.blackPieces) {
            if(tempPiece.getColor().equals("white")) System.out.println("misplacedpiece");
            ArrayList<Square> possible;

            // Special cases
            if(!(tempPiece instanceof King) &&
                    !(tempPiece instanceof Pawn)) {
                possible = tempPiece.getPossibleAttackingPositions();
            } else {
                possible = (tempPiece).getPossibleAttackingPositions();
            }




            for(Square tempSquare : possible) {
                if(tempPosition.getRow() == tempSquare.getPosition().getRow() &&
                        tempPosition.getCol() == tempSquare.getPosition().getCol()) {
                    return true;
                }
            }
        }

        return false;    }

    private boolean inCheck(Position tempPosition) {
        if(color.equals("white")) {
            return blackChecking(tempPosition);
        } else {
            return whiteChecking(tempPosition);

        }
    }

    @Override
    public boolean isValidMove(Position tempPosition) {
        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {


            // Check if it is in check
            if (inCheck(tempPosition)) {
                return false;
            }


            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
                if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color))
                    return false;
            }


            return true;
        }

        return false;
    }

    private boolean inCheck(Square s) {
        if(color.equals("white")) {
            for(Piece p : Board.blackPieces) {
                for(Square possible : p.getPossibleAttackingPositions()) {
                    if(s == possible) {
                        return true;
                    }
                }
            }
        } else {
            for(Piece p : Board.whitePieces) {
                for(Square possible : p.getPossibleAttackingPositions()) {
                    if(s == possible) {
                        return true;
                    }
                }
            }
        }

        return false;
    }



    @Override
    public ArrayList<Square> getPossiblePositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();

        // Check all 8 possibilities
        if(isValidMove(new Position(row, col+1)) ){
            list.add(Board.grid[row][col+1]);
        }
        if(isValidMove(new Position(row, col-1)) ){
            list.add(Board.grid[row][col-1]);
        }
        if(isValidMove(new Position(row-1, col)) ){
            list.add(Board.grid[row-1][col]);
        }
        if(isValidMove(new Position(row-1, col-1)) ){
            list.add(Board.grid[row-1][col-1]);
        }
        if(isValidMove(new Position(row-1, col+1)) ){
            list.add(Board.grid[row-1][col+1]);
        }
        if(isValidMove(new Position(row+1, col)) ){
            list.add(Board.grid[row+1][col]);
        }
        if(isValidMove(new Position(row+1, col-1)) ){
            list.add(Board.grid[row+1][col-1]);
        }
        if(isValidMove(new Position(row+1, col+1)) ){
            list.add(Board.grid[row+1][col+1]);
        }

        if(!casteled) {
            // King side castle
            if (Board.directionUp) {
                Piece tempPiece;
                if (color.equals("white")) {
                    tempPiece = Board.grid[7][7].getPiece();
                } else {
                    tempPiece = Board.grid[0][7].getPiece();
                }
                // Has to match all these conditions in order to castle
                // Check if the rook hasn't moved
                if (tempPiece instanceof Rook) {
                    if (!((Rook) tempPiece).isMoved()) {
                        if (!this.isMoved()) {
                            if (Board.grid[row][col + 1].getPiece() == null && Board.grid[row][col + 2].getPiece() == null) {
                                if (!inCheck(Board.grid[row][col]) && !inCheck(Board.grid[row][col + 1]) && !inCheck(Board.grid[row][col + 2])) {
                                    list.add(Board.grid[row][col + 2]);
                                    castleProcess = true;
                                }
                            }
                        }
                    }
                }

                // Queen Side castle
                if (color.equals("white")) {
                    tempPiece = Board.grid[7][0].getPiece();
                } else {
                    tempPiece = Board.grid[0][0].getPiece();
                }
                // Has to match all these conditions in order to castle
                // Check if the rook hasn't moved
                if (tempPiece instanceof Rook) {
                    if (!((Rook) tempPiece).isMoved()) {
                        if (!this.isMoved()) {
                            if (Board.grid[row][col - 1].getPiece() == null && Board.grid[row][col - 2].getPiece() == null &&
                                    Board.grid[row][col - 3].getPiece() == null) {
                                if (!inCheck(Board.grid[row][col]) && !inCheck(Board.grid[row][col - 1]) && !inCheck(Board.grid[row][col - 2]) &&
                                        !inCheck(Board.grid[row][col - 2])) {
                                    list.add(Board.grid[row][col - 2]);
                                    castleProcess = true;
                                }
                            }
                        }
                    }
                }

            } else {

                // King side
                Piece tempPiece;
                if (color.equals("white")) {
                    tempPiece = Board.grid[0][0].getPiece();
                } else {
                    tempPiece = Board.grid[7][0].getPiece();
                }
                // Has to match all these conditions in order to castle
                // Check if the rook hasn't moved
                if (tempPiece instanceof Rook) {
                    if (!((Rook) tempPiece).isMoved()) {
                        if (!this.isMoved()) {
                            if (Board.grid[row][col - 1].getPiece() == null && Board.grid[row][col - 2].getPiece() == null) {
                                if (!inCheck(Board.grid[row][col]) && !inCheck(Board.grid[row][col - 1]) && !inCheck(Board.grid[row][col - 2])) {
                                    list.add(Board.grid[row][col - 2]);
                                    castleProcess = true;
                                }
                            }
                        }
                    }
                }

                // Queen Side castle
                if (color.equals("white")) {
                    tempPiece = Board.grid[0][7].getPiece();
                } else {
                    tempPiece = Board.grid[7][7].getPiece();
                }
                // Has to match all these conditions in order to castle
                // Check if the rook hasn't moved
                if (tempPiece instanceof Rook) {
                    if (!((Rook) tempPiece).isMoved()) {
                        if (!this.isMoved()) {
                            if (Board.grid[row][col + 1].getPiece() == null && Board.grid[row][col + 2].getPiece() == null &&
                                    Board.grid[row][col + 3].getPiece() == null) {
                                if (!inCheck(Board.grid[row][col]) && !inCheck(Board.grid[row][col + 1]) && !inCheck(Board.grid[row][col + 2]) &&
                                        !inCheck(Board.grid[row][col + 2])) {
                                    list.add(Board.grid[row][col + 2]);
                                    castleProcess = true;
                                }
                            }
                        }
                    }
                }


            }
        }

        return list;
    }

    private boolean isValidMoveCastle() {
        return false;
    }


@Override
public boolean isValidEatingMoveEating(Position tempPosition) {
    if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
            tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {

        // The only difference is it doesn't check if it's in check

        if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
            if(!Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color))
                return false;
        }

        return true;
    }

    return false;
}

    public ArrayList<Square> getPossibleAttackingPositions() {
        ArrayList<Square> list = new ArrayList<>();

        int row = position.getRow();
        int col = position.getCol();

        // Check all 8 possibilities
        if(isValidEatingMoveEating(new Position(row, col+1)) ){
            list.add(Board.grid[row][col+1]);
        }
        if(isValidEatingMoveEating(new Position(row, col-1)) ){
            list.add(Board.grid[row][col-1]);
        }
        if(isValidEatingMoveEating(new Position(row-1, col)) ){
            list.add(Board.grid[row-1][col]);
        }
        if(isValidEatingMoveEating(new Position(row-1, col-1)) ){
            list.add(Board.grid[row-1][col-1]);
        }
        if(isValidEatingMoveEating(new Position(row-1, col+1)) ){
            list.add(Board.grid[row-1][col+1]);
        }
        if(isValidEatingMoveEating(new Position(row+1, col)) ){
            list.add(Board.grid[row+1][col]);
        }
        if(isValidEatingMoveEating(new Position(row+1, col-1)) ){
            list.add(Board.grid[row+1][col-1]);
        }
        if(isValidEatingMoveEating(new Position(row+1, col+1)) ){
            list.add(Board.grid[row+1][col+1]);
        }

        return list;
    }


// Getters and Setters
    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isCasteled() {
        return casteled;
    }

    public void setCasteled(boolean casteled) {
        this.casteled = casteled;
    }

    public boolean isCastleProcess() {
        return castleProcess;
    }

    public void setCastleProcess(boolean castleProcess) {
        this.castleProcess = castleProcess;
    }


}
