package pieces;


import board.Board;
import board.Square;
import runner.Position;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Brian on 15/12/12.
 */
public class Piece implements Cloneable {
    protected String color;
    protected Position position;
    protected boolean alive;
    protected String image;
    protected Board board;
    protected boolean checkContinue;
    protected int value;

// Constructor
    public Piece(String color, Position position, Board board) {
        this.board = board;
        this.color = color;
        this.position = position;
        this.alive = true;
        this.checkContinue = false;

    }

    public boolean willPutKingInCheck(Square targetSquare) {
        //System.out.println("calling");
        boolean willCheck = false;

        Piece takenPiece;
        Square selectedSquare = Board.grid[position.getRow()][position.getCol()];




        // The setup
        int row = selectedSquare.getPosition().getRow();
        int col = selectedSquare.getPosition().getCol();

        int targetRow = targetSquare.getPosition().getRow();
        int targetCol = targetSquare.getPosition().getCol();


        ArrayList<Piece> tempPieces;
        ArrayList<Piece> kingPieces;
        if(color.equals("white")) {
            tempPieces = Board.blackPieces;
            kingPieces = Board.whitePieces;
        } else {
            tempPieces = Board.whitePieces;
            kingPieces = Board.blackPieces;
        }


        // Make the move
        takenPiece = targetSquare.getPiece();
        this.setPosition(new Position(targetRow, targetCol));
        targetSquare.setPiece(this);
        selectedSquare.setPiece(null);
        if(takenPiece != null) {
            tempPieces.remove(takenPiece);
        }

        Square kingSpot = getKingSpot(kingPieces);


        // get possible attacking positions
        boolean quickOut = false;
        for(Piece p : tempPieces) {
            ArrayList<Square> possible = p.getPossibleAttackingPositions();
            for(Square s : possible) {
                if(s == kingSpot) {
                    willCheck = true;
                    quickOut = true;
                    break;
                }
            }
            if(quickOut) {
                break;
            }
        }

        // put everything back to how it was
        this.setPosition(new Position(row,col));
        selectedSquare.setPiece(this);
        targetSquare.setPiece(takenPiece);
        if(takenPiece != null) {
            tempPieces.add(takenPiece);
        }

        board.refresh();

        return willCheck;

    }



    private Square getKingSpot(ArrayList<Piece> kingPieces) {
        for(Piece p : kingPieces) {
            if(p instanceof King) {
                int row = p.getPosition().getRow();
                int col = p.getPosition().getCol();
                return Board.grid[row][col];
            }
        }
        System.out.println("Error!");
        return null;
    }

    public ArrayList<Square> getPossiblePositions() {
        // Code in children
        return null;
    }

    public ArrayList<Square> getPossibleAttackingPositions() {
        // Code in children
        return getPossiblePositions();
    }


    public boolean isValidEatingMoveEating(Position tempPosition) {
        // Code in children
        return isValidMove(tempPosition);
    }

    public boolean isValidMove(Position tempPosition) {
        if(tempPosition.getRow() >= 0 && tempPosition.getRow() < 8 &&
                tempPosition.getCol() >= 0 && tempPosition.getCol() < 8) {

            if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece() != null) {
                if(Board.grid[tempPosition.getRow()][tempPosition.getCol()].getPiece().getColor().equals(color))
                return false;
            }
            if(willPutKingInCheck(Board.grid[tempPosition.getRow()][tempPosition.getCol()])) {
                return false;
            }
            return true;
        }

        return false;
    }


    public void highlightPossibleSquares(ArrayList<Square> squares) {
        for(Square temp : squares) {
            int row = temp.getPosition().getRow();
            int col = temp.getPosition().getCol();

            temp.setBorder(new LineBorder(new Color(57, 200, 66), 5));
        }
    }

    public void resetPossibleSquares(ArrayList<Square> squares) {
        for(Square temp : squares) {
            int row = temp.getPosition().getRow();
            int col = temp.getPosition().getCol();

            temp.setBorder(new LineBorder(Color.black, 0));
        }
    }

// Getters and Setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // Overrides
    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece tempPiece = (Piece) super.clone();
        tempPiece.position = (Position) position.clone();
        return tempPiece;
    }
}
