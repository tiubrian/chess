package board;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import ai.ChessAI;
import ai.Move;
import listeners.MenuBarListeners;
import pieces.*;
import runner.Game;
import runner.Position;

/**
 * Created by Brian on 15/12/12.
 */
public class Board extends JFrame implements Cloneable {
    static public boolean directionUp = true;
    private int cellSize;
    private boolean reverseMode = false;
    static public ArrayList<Piece> blackPieces = new ArrayList<>();
    static public ArrayList<Piece> whitePieces = new ArrayList<>();
    static public Square[][] grid = new Square[8][8];
    private String turn = "black";
    private boolean promotion = false;
    private ArrayList<Move> allPossibleWhiteMoves = new ArrayList<>();
    private ArrayList<Move> allPossibleBlackMoves = new ArrayList<>();
    private boolean blackComputer = true;
    private boolean whiteComputer = false;
    private Move compMove;

    private ChessAI whiteAI;
    private ChessAI blackAI;


    JMenuBar theMenu;

    Game theGame;

    // Listener components
    private boolean selected = false;
    private Square selectedSquare = null;

    // Constructors
    public Board() {
        cellSize = 75;
    }

    public Board(int cellSize, Game theGame) {
        if(whiteComputer) {
            whiteAI = new ChessAI(this, "white");
        }
        if(blackComputer) {
            blackAI = new ChessAI(this, "black");
        }

        this.cellSize = cellSize;
        this.theGame = theGame;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setSize(cellSize * 8, cellSize * 8);
        this.setLayout(new GridLayout(8, 8));
        this.setTitle("Chess");

        setColors();
        setMenu();
        setPieces();
        placePieces();
        updateImages();
        addListeners();
        switchTurnNoCheck();

        //System.out.println("value = " + blackAI.evaluateBoardValue());

        this.setVisible(true);
    }

    private void notSelected(int h, int w) {
        // Convert to coordinates
        int height = h - theMenu.getHeight() * 2 - 5;
        int width = w;

        if (height >= 0) {
            height = height / (cellSize - 6);
            width = width / (cellSize);

            selectedSquare = grid[height][width];
            if (selectedSquare.getPiece() != null) {
                if (selectedSquare.getPiece().getColor().equals(turn)) {
                    selected = true;

                    selectedSquare.addBorder();

                    ArrayList<Square> list = selectedSquare.getPiece().getPossiblePositions();
                    selectedSquare.getPiece().highlightPossibleSquares(list);
                }
            }
        }
    }

    private void alreadySelected(int h, int w) {
        // Erase computer move border
        if(compMove != null) {
            compMove.getBefore().setBorder(new LineBorder(Color.orange, 0));
            compMove.getAfter().setBorder(new LineBorder(Color.orange, 0));

        }


        // Convert to coordinates
        int height = h - theMenu.getHeight() * 2 - 5;
        int width = w;

        height = height / (cellSize - 6);
        width = width / (cellSize);

        if (height >= 0) {
            Square targetSquare = grid[height][width];
            Piece tempPiece = selectedSquare.getPiece();


            ArrayList<Square> list = selectedSquare.getPiece().getPossiblePositions();
            selectedSquare.getPiece().resetPossibleSquares(list);

            selected = false;
            if (isMoveValid(list, targetSquare)) {
                int row = tempPiece.getPosition().getRow();
                int col = tempPiece.getPosition().getCol();

                // If it ate something
                if (targetSquare.getPiece() != null) {
                    removeEatenPiece(targetSquare);
                }

                // If it's a pawn
                if (selectedSquare.getPiece() instanceof Pawn) {
                    ((Pawn) selectedSquare.getPiece()).setMoved(true);
                    if (selectedSquare.getPiece().getColor().equals("white") && targetSquare.getPosition().getRow() == 0 && directionUp ||
                            selectedSquare.getPiece().getColor().equals("white") && targetSquare.getPosition().getRow() == 7 && !directionUp) {
                        promotion = true;
                    } else if (selectedSquare.getPiece().getColor().equals("black") && targetSquare.getPosition().getRow() == 7 && directionUp ||
                            selectedSquare.getPiece().getColor().equals("black") && targetSquare.getPosition().getRow() == 0 && !directionUp) {
                        promotion = true;
                    }
                }

                // If it's a rook
                if (selectedSquare.getPiece() instanceof Rook) {
                    ((Rook) selectedSquare.getPiece()).setMoved(true);
                }
                // If it's the king
                if (selectedSquare.getPiece() instanceof King) {
                    ((King) selectedSquare.getPiece()).setMoved(true);
                    // Castle case
                    kingSideCastle(row, col, targetSquare);
                    queenSideCastle(row, col, targetSquare);

                }


                grid[row][col].removeAll();
                grid[row][col].setBorder(new LineBorder(Color.black, 0));
                grid[row][col].setPiece(null);

                grid[row][col].updateImage();
                grid[row][col].validate();
                grid[row][col].repaint();

                // Update target square
                row = targetSquare.getPosition().getRow();
                col = targetSquare.getPosition().getCol();

                tempPiece.setPosition(new Position(height, col));

                grid[row][col].setPiece(tempPiece);
                grid[row][col].updateImage();

                grid[row][col].validate();
                grid[row][col].repaint();




                if (promotion) {
                    if (targetSquare.getPiece().getColor().equals("white") && targetSquare.getPosition().getRow() == 0 && directionUp ||
                            targetSquare.getPiece().getColor().equals("white") && targetSquare.getPosition().getRow() == 7 && !directionUp) {
                        PromoteFrame frame = new PromoteFrame("white", targetSquare, this);
                        frame.setVisible(true);

                    } else if (targetSquare.getPiece().getColor().equals("black") && targetSquare.getPosition().getRow() == 7 && directionUp ||
                            targetSquare.getPiece().getColor().equals("black") && targetSquare.getPosition().getRow() == 0 && !directionUp) {
                        PromoteFrame frame = new PromoteFrame("black", targetSquare, this);
                        frame.setVisible(true);

                    }
                    promotion = false;
                    this.validate();
                    this.repaint();
                }
                this.validate();
                this.repaint();
                switchTurn();

            } else {
                selected = false;

                int row = tempPiece.getPosition().getRow();
                int col = tempPiece.getPosition().getCol();
                grid[row][col].setBorder(new LineBorder(Color.black, 0));

                selectedSquare.getPiece().resetPossibleSquares(list);
            }



        }


    }

    private void kingSideCastle(int row, int col, Square targetSquare) {
        // Castle case
        if (directionUp) {
            if (selectedSquare.getPiece().getColor().equals("white")) {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 7 && selCol == 4 &&
                        targetRow == 7 && targetCol == 6) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col + 1].setPiece(grid[row][col + 3].getPiece());
                    grid[row][col + 1].getPiece().setPosition(new Position(row, col + 1));
                    grid[row][col + 1].updateImage();
                    grid[row][col + 1].validate();
                    grid[row][col + 1].repaint();


                    grid[row][col + 3].removeAll();
                    grid[row][col + 3].setPiece(null);
                    grid[row][col + 3].updateImage();
                    grid[row][col + 3].validate();
                    grid[row][col + 3].repaint();
                }
            } else {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 0 && selCol == 4 &&
                        targetRow == 0 && targetCol == 6) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col + 1].setPiece(grid[row][col + 3].getPiece());
                    grid[row][col + 1].getPiece().setPosition(new Position(row, col + 1));
                    grid[row][col + 1].updateImage();
                    grid[row][col + 1].validate();
                    grid[row][col + 1].repaint();


                    grid[row][col + 3].removeAll();
                    grid[row][col + 3].setPiece(null);
                    grid[row][col + 3].updateImage();
                    grid[row][col + 3].validate();
                    grid[row][col + 3].repaint();
                }

            }
        } else {


            if (selectedSquare.getPiece().getColor().equals("white")) {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 0 && selCol == 3 &&
                        targetRow == 0 && targetCol == 1) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col - 1].setPiece(grid[row][col - 3].getPiece());
                    grid[row][col - 1].getPiece().setPosition(new Position(row, col - 1));
                    grid[row][col - 1].updateImage();
                    grid[row][col - 1].validate();
                    grid[row][col - 1].repaint();


                    grid[row][col - 3].removeAll();
                    grid[row][col - 3].setPiece(null);
                    grid[row][col - 3].updateImage();
                    grid[row][col - 3].validate();
                    grid[row][col - 3].repaint();
                }
            } else {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 7 && selCol == 3 &&
                        targetRow == 7 && targetCol == 1) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col - 1].setPiece(grid[row][col - 3].getPiece());
                    grid[row][col - 1].getPiece().setPosition(new Position(row, col - 1));
                    grid[row][col - 1].updateImage();
                    grid[row][col - 1].validate();
                    grid[row][col - 1].repaint();


                    grid[row][col - 3].removeAll();
                    grid[row][col - 3].setPiece(null);
                    grid[row][col - 3].updateImage();
                    grid[row][col - 3].validate();
                    grid[row][col - 3].repaint();
                }

            }
        }
        this.validate();
        this.repaint();
    }

    private void queenSideCastle(int row, int col, Square targetSquare) {
        if (directionUp) {
            if (selectedSquare.getPiece().getColor().equals("white")) {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 7 && selCol == 4 &&
                        targetRow == 7 && targetCol == 2) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col - 1].setPiece(grid[row][col - 4].getPiece());
                    grid[row][col - 1].getPiece().setPosition(new Position(row, col - 1));
                    grid[row][col - 1].updateImage();
                    grid[row][col - 1].validate();
                    grid[row][col - 1].repaint();


                    grid[row][col - 4].removeAll();
                    grid[row][col - 4].setPiece(null);
                    grid[row][col - 4].updateImage();
                    grid[row][col - 4].validate();
                    grid[row][col - 4].repaint();
                }
            } else {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 0 && selCol == 4 &&
                        targetRow == 0 && targetCol == 2) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col - 1].setPiece(grid[row][col - 4].getPiece());
                    grid[row][col - 1].getPiece().setPosition(new Position(row, col - 1));
                    grid[row][col - 1].updateImage();
                    grid[row][col - 1].validate();
                    grid[row][col - 1].repaint();


                    grid[row][col - 4].removeAll();
                    grid[row][col - 4].setPiece(null);
                    grid[row][col - 4].updateImage();
                    grid[row][col - 4].validate();
                    grid[row][col - 4].repaint();
                }

            }
        } else {


            if (selectedSquare.getPiece().getColor().equals("white")) {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 0 && selCol == 3 &&
                        targetRow == 0 && targetCol == 5) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col + 1].setPiece(grid[row][col + 4].getPiece());
                    grid[row][col + 1].getPiece().setPosition(new Position(row, col + 1));
                    grid[row][col + 1].updateImage();
                    grid[row][col + 1].validate();
                    grid[row][col + 1].repaint();


                    grid[row][col + 4].removeAll();
                    grid[row][col + 4].setPiece(null);
                    grid[row][col + 4].updateImage();
                    grid[row][col + 4].validate();
                    grid[row][col + 4].repaint();
                }
            } else {
                // King side Castle
                int targetRow = targetSquare.getPosition().getRow();
                int targetCol = targetSquare.getPosition().getCol();
                int selRow = selectedSquare.getPosition().getRow();
                int selCol = selectedSquare.getPosition().getCol();
                if (selRow == 7 && selCol == 3 &&
                        targetRow == 7 && targetCol == 5) {
                    ((King) selectedSquare.getPiece()).setCasteled(true);
                    ((King) selectedSquare.getPiece()).setCastleProcess(false);

                    grid[row][col + 1].setPiece(grid[row][col + 4].getPiece());
                    grid[row][col + 1].getPiece().setPosition(new Position(row, col + 1));
                    grid[row][col + 1].updateImage();
                    grid[row][col + 1].validate();
                    grid[row][col + 1].repaint();


                    grid[row][col + 4].removeAll();
                    grid[row][col + 4].setPiece(null);
                    grid[row][col + 4].updateImage();
                    grid[row][col + 4].validate();
                    grid[row][col + 4].repaint();
                }

            }
        }
    }


    private void removeEatenPiece(Square tempSquare) {
        Piece tempPiece = tempSquare.getPiece();

        if (tempPiece.getColor().equals("white")) {
            whitePieces.remove(tempPiece);
        } else {
            blackPieces.remove(tempPiece);
        }
    }

    public void switchTurn() {
        this.revalidate();
        this.repaint();

        if (turn.equals("white")) {
            turn = "black";
            allPossibleBlackMoves = getAllPossibleBlackMoves();
            if(allPossibleBlackMoves.size() == 0) {
                JOptionPane.showMessageDialog(null, "Checkmate! White wins!");
            }

            if(blackComputer) {
                refresh();
                compMove = blackAI.getBestMove(allPossibleBlackMoves);
                makeMove(compMove);
                highlightCompMove();
                switchTurn();
                System.out.println("value = " + blackAI.evaluateBoardValue());
            }

        } else {
            turn = "white";
            allPossibleWhiteMoves = getAllPossibleWhiteMoves();
            if(allPossibleWhiteMoves.size() == 0) {
                JOptionPane.showMessageDialog(null, "Checkmate! Black wins!");
            }
            if(whiteComputer) {
                refresh();
                makeMove(whiteAI.getBestMove(allPossibleWhiteMoves));
                switchTurn();
            }
        }

        if (reverseMode) {
            reverse();
        }
    }

    private void highlightCompMove() {
        compMove.getBefore().setBorder(new LineBorder(Color.orange, 3));
        compMove.getAfter().setBorder(new LineBorder(Color.orange, 3));
    }


    public void switchTurnNoCheck() {
        if (turn.equals("white")) {
            turn = "black";
            allPossibleBlackMoves = getAllPossibleBlackMoves();

        } else {
            allPossibleWhiteMoves = getAllPossibleWhiteMoves();
            switchTurn();

        }


    }

    public ArrayList<Move> getAllPossibleWhiteMoves() {
        ArrayList<Move> possible = new ArrayList<>();
        for(Piece p : whitePieces) {
            for(Square s : p.getPossiblePositions()) {
                Move newMove = new Move(grid[p.getPosition().getRow()][p.getPosition().getCol()], s);
                possible.add(newMove);
            }
        }


        return possible;
    }

    public ArrayList<Move> getAllPossibleBlackMoves() {
        ArrayList<Move> possible = new ArrayList<>();
        for(Piece p : blackPieces) {
            for(Square s : p.getPossiblePositions()) {
                Move newMove = new Move(grid[p.getPosition().getRow()][p.getPosition().getCol()], s);
                possible.add(newMove);
            }
        }

        return possible;
    }



    private boolean isMoveValid(ArrayList<Square> list, Square targetSquare) {
        for(Square temp : list) {
            if(targetSquare.equals(temp)) {
                return true;
            }
        }

        return false;
    }

    private void addListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getComponent() instanceof Board) {
                    if(!selected) {
                        notSelected(e.getY(), e.getX());
                    } else if(selected) {
                        alreadySelected(e.getY(), e.getX());
                    }
                }
            }
        });
    }

    private void updateImages() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                grid[i][j].updateImage();

            }
        }
        this.validate();
        this.repaint();
    }

    private void placePieces() {
        // Place white pieces
        for(Piece temp : whitePieces) {
            int row = temp.getPosition().getRow();
            int col = temp.getPosition().getCol();

            grid[row][col].setPiece(temp);
        }

        // Place black pieces
        for(Piece temp : blackPieces) {
            int row = temp.getPosition().getRow();
            int col = temp.getPosition().getCol();

            grid[row][col].setPiece(temp);
        }
    }

    private void setPieces() {
        // Set white pieces
        whitePieces.add(new Rook("white", new Position(7,0), this));
        whitePieces.add(new Rook("white", new Position(7,7), this));

        whitePieces.add(new Knight("white", new Position(7,1), this));
        whitePieces.add(new Knight("white", new Position(7,6), this));

        whitePieces.add(new Bishop("white", new Position(7,2), this));
        whitePieces.add(new Bishop("white", new Position(7,5), this));

        whitePieces.add(new Queen("white", new Position(7,3), this));
        whitePieces.add(new King("white", new Position(7,4), this));

        for(int i = 0; i < 8; i++) {
            whitePieces.add(new Pawn("white", new Position(6,i), this));
        }


        // Set black pieces
        blackPieces.add(new Rook("black", new Position(0,0), this));
        blackPieces.add(new Rook("black", new Position(0,7), this));

        blackPieces.add(new Knight("black", new Position(0,1), this));
        blackPieces.add(new Knight("black", new Position(0,6), this));

        blackPieces.add(new Bishop("black", new Position(0,2), this));
        blackPieces.add(new Bishop("black", new Position(0,5), this));

        blackPieces.add(new Queen("black", new Position(0,3), this));
        blackPieces.add(new King("black", new Position(0,4), this));

        for(int i = 0; i < 8; i++) {
            blackPieces.add(new Pawn("black", new Position(1,i), this));
        }

    }

    private void setMenu() {
        theMenu = new JMenuBar();
        this.setJMenuBar(theMenu);

        JMenu file = new JMenu("File");

        // Menu items
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem gameOptions = new JMenuItem("Game options");
        JMenuItem exit = new JMenuItem("Exit");

        // Action listeners
        newGame.addActionListener(new MenuBarListeners(this, "new"));
        gameOptions.addActionListener(new MenuBarListeners(this, "options"));
        exit.addActionListener(new MenuBarListeners(this, "exit"));

        file.add(newGame);
        file.add(gameOptions);
        file.add(exit);

        theMenu.add(file);

        // Options
        JMenu options = new JMenu("Options");
        JMenuItem reverse = new JMenuItem(("Reverse the board"));
        reverse.addActionListener(new MenuBarListeners(this, "reverse"));


        options.add(reverse);

        theMenu.add(options);

    }

    private void setColors() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(i % 2 == 0 && j % 2 == 0 ||
                   i % 2 == 1 && j % 2 == 1) {
                    Square temp = new Square(cellSize, "light", new Position(i,j), this);
                    this.add(temp);
                    grid[i][j] = temp;
                } else {
                    Square temp = new Square(cellSize, "dark", new Position(i,j),this);
                    this.add(temp);
                    grid[i][j] = temp;

                }
            }
        }
    }

// Action listeners
    // Menu option listeners
    public void newGame() {
        NewGameFrame newGame = new NewGameFrame();
        newGame.setVisible(true);
        this.dispose();
       // theGame.newGame();
    }

    public void options() {
        GameOptionsFrame options = new GameOptionsFrame();
        options.setVisible(true);
    }

    public void exit() {
        this.dispose();
    }

    // Option option listeners
    public void reverse() {
        // Clear all the pieces
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                grid[i][j].removeAll();
                grid[i][j].setPiece(null);
            }
        }

        // Reverse the positions
        for(Piece temp : whitePieces) {
            int row = 7 - temp.getPosition().getRow();
            int col = 7 - temp.getPosition().getCol();

            temp.setPosition(new Position(row, col));
        }
        for(Piece temp : blackPieces) {
            int row = 7 - temp.getPosition().getRow();
            int col = 7 - temp.getPosition().getCol();

            temp.setPosition(new Position(row, col));
        }

        // Put them back on the board
        placePieces();
        updateImages();

        if(directionUp) {
            directionUp = false;
        } else {
            directionUp = true;
        }

    }

    public void refresh() {
        this.validate();
        this.repaint();
    }

    public void makeMove(Move move) {
        if(move == null) {
            JOptionPane.showMessageDialog(null, "You win! The computer resigns!");
        }
        // Remove the piece
        if(move.getAfter().getPiece() != null) {
            if(move.getAfter().getPiece().getColor().equals("white")) {
                whitePieces.remove(move.getAfter().getPiece());
            } else {
                blackPieces.remove(move.getAfter().getPiece());
            }
        }

        if(move.getBefore().getPiece() instanceof King) {
            ((King) move.getBefore().getPiece()).setMoved(true);
        }
        if(move.getBefore().getPiece() instanceof Rook) {
            ((Rook) move.getBefore().getPiece()).setMoved(true);
        }
        if(move.getBefore().getPiece() instanceof Pawn) {
            ((Pawn) move.getBefore().getPiece()).setMoved(true);
        }

        move.getAfter().setPiece(move.getBefore().getPiece());
        move.getBefore().setPiece(null);
        move.getAfter().getPiece().setPosition(new Position(move.getAfter().getPosition().getRow(), move.getAfter().getPosition().getCol()));

        move.getBefore().updateImage();
        move.getAfter().updateImage();

        refresh();
    }


// Getters and Setters
    public Square[][] getGrid() {
        return grid;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }

    public boolean isDirectionUp() {
        return directionUp;
    }

    public void setDirectionUp(boolean directionUp) {
        this.directionUp = directionUp;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

// Overrides
    @Override
    public Object clone() throws CloneNotSupportedException {
        Board tempBoard = (Board) super.clone();

        // Create new Grid
        Square[][] newGrid = new Square[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                newGrid[i][j] = (Square) grid[i][j].clone();
            }
        }
        tempBoard.grid = newGrid;

        // Create new pieces
        ArrayList<Piece> newWhite = new ArrayList<Piece>();
        ArrayList<Piece> newBlack = new ArrayList<Piece>();
        for(Piece p : whitePieces) {
            newWhite.add((Piece) p.clone());
        }
        for(Piece p : blackPieces) {
            newBlack.add((Piece) p.clone());
        }
        tempBoard.whitePieces = newWhite;
        tempBoard.blackPieces = newBlack;

        return tempBoard;
    }
}
