package ai;

import board.Board;
import board.Square;
import pieces.*;
import runner.Position;

import java.util.*;

/**
 * Created by Brian on 15/12/25.
 */
public class ChessAI {
    private Board board;
    private String color;

    // Constructor
    public ChessAI(Board board, String color) {
        this.board = board;
        this.color = color;
    }

    public Move a(ArrayList<Move> possibleMoves) {
        double score = -1000;
        ArrayList<Piece> tempPieces;
        if (color.equals("black")) {
            tempPieces = board.whitePieces;
           // Collections.shuffle(board.blackPieces);
        } else {
            tempPieces = board.blackPieces;
            //Collections.shuffle(board.whitePieces);
        }
        Move bestMove = null;

        for(Move m : possibleMoves) {
            // Setup
            Square targetSquare = m.getAfter();
            Square selectedSquare = m.getBefore();
            Piece takenPiece = targetSquare.getPiece();

            int row = selectedSquare.getPosition().getRow();
            int col = selectedSquare.getPosition().getCol();
            int targetRow = targetSquare.getPosition().getRow();
            int targetCol = targetSquare.getPosition().getCol();

            // Make the move
            selectedSquare.getPiece().setPosition(new Position(targetRow, targetCol));
            targetSquare.setPiece(selectedSquare.getPiece());
            selectedSquare.setPiece(null);
            if (takenPiece != null) {
                tempPieces.remove(takenPiece);
            }


            // Evaluate
            double value = min(color, 3);
            if(value > score) {
                score = value;
                bestMove = m;
            }


            // Undo move
            targetSquare.getPiece().setPosition(new Position(row, col));
            selectedSquare.setPiece(targetSquare.getPiece());
            targetSquare.setPiece(takenPiece);
            if (takenPiece != null) {
                tempPieces.add(takenPiece);
            }
        }

        return bestMove;
    }

    public double min(String color, int depth) {
        depth--;
        if(depth == 0) {
            if(color.equals("white")) {
                return evaluateBoardValue("white");
            } else {
                return evaluateBoardValue("black");
            }
        }


        double score = 1000;
        ArrayList<Move> possibleMoves;
        ArrayList<Piece> tempPieces;
        // Switch the turn
        if(color == "white") {
            color = "black";
            possibleMoves = getAllPossibleBlackMoves();
            tempPieces = board.blackPieces;
        } else {
            color = "white";
            possibleMoves = getAllPossibleBlackMoves();
            tempPieces = board.whitePieces;
        }

        if(possibleMoves.size() == 0) {
            if(color.equals("white")) {
                return evaluateBoardValue("white");
            } else {
                return evaluateBoardValue("black");
            }
        }

        for(Move m : possibleMoves) {
            // Setup
            Square targetSquare = m.getAfter();
            Square selectedSquare = m.getBefore();
            Piece takenPiece = targetSquare.getPiece();

            int row = selectedSquare.getPosition().getRow();
            int col = selectedSquare.getPosition().getCol();
            int targetRow = targetSquare.getPosition().getRow();
            int targetCol = targetSquare.getPosition().getCol();

            // Make the move
            selectedSquare.getPiece().setPosition(new Position(targetRow, targetCol));
            targetSquare.setPiece(selectedSquare.getPiece());
            selectedSquare.setPiece(null);
            if (takenPiece != null) {
                tempPieces.remove(takenPiece);
            }


            // Evaluate
            double value = max(color, depth);
            if(value < score) {
                score = value;
                //bestMove = m;
            }


            // Undo move
            targetSquare.getPiece().setPosition(new Position(row, col));
            selectedSquare.setPiece(targetSquare.getPiece());
            targetSquare.setPiece(takenPiece);
            if (takenPiece != null) {
                tempPieces.add(takenPiece);
            }
        }



        return score;
    }

    public double max(String color, int depth) {
        depth--;
        if(depth == 0) {
            if(color.equals("white")) {
                return evaluateBoardValue("white");
            } else {
                return evaluateBoardValue("black");
            }
        }

        double score = -1000;
        ArrayList<Move> possibleMoves;
        ArrayList<Piece> tempPieces;
        // Switch the turn
        if(color == "white") {
            color = "black";
            possibleMoves = getAllPossibleBlackMoves();
            tempPieces = board.blackPieces;
        } else {
            color = "white";
            possibleMoves = getAllPossibleBlackMoves();
            tempPieces = board.whitePieces;
        }

        if(possibleMoves.size() == 0) {
            if(color.equals("white")) {
                return evaluateBoardValue("white");
            } else {
                return evaluateBoardValue("black");
            }
        }
        for(Move m : possibleMoves) {
            // Setup
            Square targetSquare = m.getAfter();
            Square selectedSquare = m.getBefore();
            Piece takenPiece = targetSquare.getPiece();

            int row = selectedSquare.getPosition().getRow();
            int col = selectedSquare.getPosition().getCol();
            int targetRow = targetSquare.getPosition().getRow();
            int targetCol = targetSquare.getPosition().getCol();

            // Make the move
            selectedSquare.getPiece().setPosition(new Position(targetRow, targetCol));
            targetSquare.setPiece(selectedSquare.getPiece());
            selectedSquare.setPiece(null);
            if (takenPiece != null) {
                tempPieces.remove(takenPiece);
            }


            // Evaluate
            double value = min(color, depth);//lksjdflkasdj l;kjdl;kj fl;aksdjf l;k jlkj l;kj
            if(value > score) {
                score = value;
                //bestMove = m;
            }


            // Undo move
            targetSquare.getPiece().setPosition(new Position(row, col));
            selectedSquare.setPiece(targetSquare.getPiece());
            targetSquare.setPiece(takenPiece);
            if (takenPiece != null) {
                tempPieces.add(takenPiece);
            }
        }



        return score;
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~





    public Move getBestMove(ArrayList<Move> possibleMoves) {
        System.out.println("starting calculating");
        Move bestMove = null;// = possibleMoves.get(0);
        double bestMoveValue;// = 100;
        double min = 1000;
        double max = -1000;
        if (color.equals("black")) {
            bestMoveValue = 1000;
        } else {
            bestMoveValue = -1000;
        }

        ArrayList<Piece> tempPieces;
        if (color.equals("black")) {
            tempPieces = board.whitePieces;
            Collections.shuffle(board.blackPieces);
        } else {
            tempPieces = board.blackPieces;
            Collections.shuffle(board.whitePieces);

        }

        Iterator<Move> iter = possibleMoves.iterator();
        while (iter.hasNext()) {
            Move m = iter.next();

            // Setup
            Square targetSquare = m.getAfter();
            Square selectedSquare = m.getBefore();
            Piece takenPiece = targetSquare.getPiece();

            int row = selectedSquare.getPosition().getRow();
            int col = selectedSquare.getPosition().getCol();
            int targetRow = targetSquare.getPosition().getRow();
            int targetCol = targetSquare.getPosition().getCol();

            // Make the move
            selectedSquare.getPiece().setPosition(new Position(targetRow, targetCol));
            targetSquare.setPiece(selectedSquare.getPiece());
            selectedSquare.setPiece(null);
            if (takenPiece != null) {
                tempPieces.remove(takenPiece);
            }


            // Evaluate the position
            if (color.equals("black")) {
                ArrayList<Move> tempPossible = getAllPossibleWhiteMoves();
                double tempValue = recursiveCheck("white", tempPossible, 3, min, max);
                if(tempValue < min) {
                    min = tempValue;
                }

                if (tempValue < bestMoveValue) {
                    bestMove = m;
                    bestMoveValue = tempValue;
                }

            } else {
                double tempValue = recursiveCheck("black", getAllPossibleBlackMoves(), 3, min, max);
                if(tempValue > max) {
                    max = tempValue;
                }
                if (tempValue > bestMoveValue) {
                    bestMove = m;
                    bestMoveValue = tempValue;
                }
            }

            // put everything back to how it was
            targetSquare.getPiece().setPosition(new Position(row, col));
            selectedSquare.setPiece(targetSquare.getPiece());
            targetSquare.setPiece(takenPiece);
            if (takenPiece != null) {
                tempPieces.add(takenPiece);
            }
        }


        //return tempMove;
        return bestMove;
    }

    public double recursiveCheck(String turn, ArrayList<Move> possibleMoves, int counter, double min, double max) {
        // Base case Start ~~~~~~~
        counter--;
        if (counter == 0) {
            return evaluateBoardValue();
        }

        // Base case end ~~~~~~~~~




        double bestMoveValue;
        ;
        if (turn.equals("black")) {
            bestMoveValue = 1000;
        } else {
            bestMoveValue = -1000;
        }

        ArrayList<Piece> tempPieces;
        if (turn.equals("black")) {
            tempPieces = board.whitePieces;
            Collections.shuffle(board.blackPieces);
        } else {
            tempPieces = board.blackPieces;
            Collections.shuffle(board.whitePieces);

        }

        Iterator<Move> iter = possibleMoves.iterator();
        while (iter.hasNext()) {
            Move m = iter.next();

            // Setup
            Square targetSquare = m.getAfter();
            Square selectedSquare = m.getBefore();
            Piece takenPiece = targetSquare.getPiece();

            int row = selectedSquare.getPosition().getRow();
            int col = selectedSquare.getPosition().getCol();
            int targetRow = targetSquare.getPosition().getRow();
            int targetCol = targetSquare.getPosition().getCol();

            // Make the move
            selectedSquare.getPiece().setPosition(new Position(targetRow, targetCol));
            targetSquare.setPiece(selectedSquare.getPiece());
            selectedSquare.setPiece(null);
            if (takenPiece != null) {
                tempPieces.remove(takenPiece);
            }


            // Evaluate the position
            if (turn.equals("black")) {
                ArrayList<Move> tempPossible = board.getAllPossibleWhiteMoves();//getAllPossibleWhiteMoves();
                if(tempPossible.size() == 0) {
                    counter = 0;
                }
                double tempValue;
                if(max < min) {
                    tempValue = recursiveCheck("white", tempPossible, counter, min, max);

                    if (tempValue < min) {
                        min = tempValue;
                    }
                    if (tempValue < bestMoveValue) {
                        bestMoveValue = tempValue;
                    }
                }
            } else {
                ArrayList<Move> tempPossible = board.getAllPossibleBlackMoves();

                double tempValue;
                if(tempPossible.size() == 0) {
                    counter = 0;
                }
                if(max < min) {
                    tempValue = recursiveCheck("black", tempPossible, counter, min, max);
                    if (tempValue > max) {
                        max = tempValue;
                    }
                    if (tempValue > bestMoveValue) {
                        bestMoveValue = tempValue;
                    }
                }
            }

            // put everything back to how it was
            targetSquare.getPiece().setPosition(new Position(row, col));
            selectedSquare.setPiece(targetSquare.getPiece());
            targetSquare.setPiece(takenPiece);
            if (takenPiece != null) {
                tempPieces.add(takenPiece);
            }
        }


        return bestMoveValue;
    }

    public double evaluateBoardValue(String color) {
        double whiteValue = getWhitePieceValue();
        double blackValue = getBlackPieceValue();


        if(color.equals("white")) {
            return whiteValue - blackValue;
        } else {
            return blackValue - whiteValue;
        }
    }

    public double evaluateBoardValue() {
        double whiteValue = getWhitePieceValue();
        double blackValue = getBlackPieceValue();


        return whiteValue - blackValue;
    }

    // Get the value of the pieces on both sides
    private double getWhitePieceValue() {
        double total = 0;
        for (Piece p : Board.whitePieces) {
            total += p.getValue();
            if(p instanceof Knight) {
                total += knightBonus(p);
            } else if(p instanceof Bishop) {
                total += bishopBonus(p);
            } else if(p instanceof Queen) {
                total += queenBonus(p);
            } else if(p instanceof King) {
                total += kingBonus(p);
            } else if(p instanceof Rook) {
                total += rookBonus(p);
            } else if(p instanceof Pawn) {
                total += pawnBonus(p);
            }
        }

        return total;
    }

    private double getBlackPieceValue() {
        double total = 0;
        for (Piece p : Board.blackPieces) {
            total += p.getValue();
            if(p instanceof Knight) {
                total += knightBonus(p);
            } else if(p instanceof Bishop) {
                total += bishopBonus(p);
            } else if(p instanceof Queen) {
                total += queenBonus(p);
            } else if(p instanceof King) {
                total += kingBonus(p);
            } else if(p instanceof Rook) {
                total += rookBonus(p);
            } else if(p instanceof Pawn) {
                total += pawnBonus(p);
            }
        }

        return total;
    }

    private double knightBonus(Piece p) {
        double bonus = 0;

        if(p.getColor().equals("black") && Board.directionUp ||
                p.getColor().equals("white") && !Board.directionUp) {
            // Rows
            switch(p.getPosition().getRow()) {
                case 0: bonus += 0; break;
                case 1: bonus += 0.01; break;
                case 2: bonus += 0.02; break;
                case 3: bonus += 0.03; break;
                case 4: bonus += 0.04; break;
                case 5: bonus += 0.05; break;
                case 6: bonus += 0; break;
                case 7: bonus += 0; break;
                default: System.out.println("Error!");
            }
            // Cols
            switch(p.getPosition().getCol()) {
                case 0:
                case 7: bonus += 0; break;

                case 1:
                case 6: bonus += .01; break;

                case 2:
                case 5: bonus += .02; break;

                case 3:
                case 4: bonus += .03; break;
                default: System.out.println("Error!");

            }
        } else {
            // Rows
            switch(p.getPosition().getRow()) {
                case 0: bonus += 0; break;
                case 1: bonus += 0; break;
                case 2: bonus += 0.05; break;
                case 3: bonus += 0.04; break;
                case 4: bonus += 0.03; break;
                case 5: bonus += 0.02; break;
                case 6: bonus += 0.01; break;
                case 7: bonus += 0; break;
                default: System.out.println("Error!");
            }
            // Cols
            switch(p.getPosition().getCol()) {
                case 0:
                case 7: bonus += 0; break;

                case 1:
                case 6: bonus += .01; break;

                case 2:
                case 5: bonus += .02; break;

                case 3:
                case 4: bonus += .03; break;
                default: System.out.println("Error!");


            }
        }

        return bonus;
    }

    private double bishopBonus(Piece p) {
        double bonus = 0;
        if(p.getColor().equals("white")) {
            switch(getDepthRange(p)) {
                case 0: bonus =  .08;break;
                case 1: bonus = .07; break;
                case 2: bonus = .06;break;
                case 3: bonus =.05; break;
                case 4: bonus = .04; break;
                case 5: bonus = .03; break;
                case 6: bonus = .02; break;
                case 7: bonus =.01; break;
                case -1: bonus = 0; break;
                default: System.out.println("Error!");
            }
        } else {
            switch(getDepthRange(p)) {
                case 0: bonus =  .01;break;
                case 1: bonus = .02; break;
                case 2: bonus = .03;break;
                case 3: bonus =.04; break;
                case 4: bonus = .05; break;
                case 5: bonus = .06; break;
                case 6: bonus = .07; break;
                case 7: bonus =.08; break;
                case -1: bonus = 0; break;
                default: System.out.println("Error!");
            }
        }
        return bonus;
    }

    private double queenBonus(Piece p) {
        return 0;
    }

    private double kingBonus(Piece p) {
        /*
        int kingSafty = 0;
        switch(p.getPosition().getCol()) {
            case 0:
            case 7:

            case 1:
            case 6: kingSafty += 1.5; break;


            case 2:
            case 5: kingSafty += .05;

            case 3:
            case 4: kingSafty += 0; break;

            default: System.out.println("Error!");
        }

        return kingSafty;
        */
        return 0;
    }

    private double rookBonus(Piece p) {
        double bonus = 0;
        if(p.getColor().equals("white")) {
            switch(getDepthRange(p)) {
                case 0: bonus =  .1;break;
                case 1: bonus = .07; break;
                case 2: bonus = .06;break;
                case 3: bonus =.05; break;
                case 4: bonus = .04; break;
                case 5: bonus = .03; break;
                case 6: bonus = .02; break;
                case 7: bonus =.01; break;
                case -1: bonus = 0; break;
                default: System.out.println("Error!");
            }
        } else {
            switch(getDepthRange(p)) {
                case 0: bonus =  .01;break;
                case 1: bonus = .02; break;
                case 2: bonus = .03;break;
                case 3: bonus =.04; break;
                case 4: bonus = .05; break;
                case 5: bonus = .06; break;
                case 6: bonus = .07; break;
                case 7: bonus =.1; break;
                case -1: bonus = 0; break;
                default: System.out.println("Error!");
            }
        }
        return bonus;    }

    private double pawnBonus(Piece p) {
        double bonus = 0;
        if(p.getPosition().getCol() == 3 || p.getPosition().getCol() == 4) {
            if(p.getColor().equals("white")) {
                bonus += (7-p.getPosition().getRow()-1)*.1;
            } else {
                bonus += (p.getPosition().getRow()-1)*.1;
            }
        }
        return bonus;
    }

    private int getDepthRange(Piece p) {
        int depth;
        ArrayList<Square> possible = p.getPossiblePositions();
        if(possible.size() == 0) {
            return -1;
        }

        if(p.getColor().equals("white")) {
            depth = 100;
            for(Square s : possible) {
                if(s.getPosition().getRow() < depth) {
                    depth = s.getPosition().getRow();
                }
            }
        } else {
            depth = -100;
            for(Square s : possible) {
                if(s.getPosition().getRow() > depth) {
                    depth = s.getPosition().getRow();
                }
            }

        }

        return depth;
    }

    public ArrayList<Move> getAllPossibleWhiteMoves() {
        ArrayList<Move> possible = new ArrayList<>();
        Iterator<Piece> iter = Board.whitePieces.iterator();

        while (iter.hasNext()) {
            Piece p = iter.next();
            Iterator<Square> iter2 = p.getPossiblePositions().iterator();
            while (iter2.hasNext()) {
                Square s = iter2.next();
                Move newMove = new Move(Board.grid[p.getPosition().getRow()][p.getPosition().getCol()], s);
                possible.add(newMove);
            }
        }


        return possible;
    }

    public ArrayList<Move> getAllPossibleBlackMoves() {
        ArrayList<Move> possible = new ArrayList<>();
        for (Piece p : Board.blackPieces) {
            for (Square s : p.getPossiblePositions()) {
                Move newMove = new Move(Board.grid[p.getPosition().getRow()][p.getPosition().getCol()], s);
                possible.add(newMove);
            }
        }

        return possible;
    }
}

