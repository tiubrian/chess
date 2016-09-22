package board;

import listeners.PromotionListener;
import pieces.Bishop;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;
import runner.Position;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brian on 15/12/21.
 */
public class PromoteFrame extends JDialog {
    String color;
    Square square;
    JButton queen = new JButton();
    JButton rook = new JButton();
    JButton knight = new JButton();
    JButton bishop = new JButton();
    Board board;

// Constructor
    public PromoteFrame(String color, Square square, Board board) {
        this.board = board;
        this.square = square;
        this.color = color;
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("Promotion");
        this.setResizable(false);
        this.setModal(true);
        this.setLayout(new FlowLayout());
        this.setSize(90*4,100);

        if(color.equals("black")) {
            addBlackButtons();
        } else {
            addWhiteButtons();
        }

        rook.setSize(70,70);
        knight.setSize(70,70);
        bishop.setSize(70,70);
        queen.setSize(70,70);

        queen.addActionListener(new PromotionListener(this, "queen"));
        knight.addActionListener(new PromotionListener(this, "knight"));
        bishop.addActionListener(new PromotionListener(this, "bishop"));
        rook.addActionListener(new PromotionListener(this, "rook"));

        this.add(queen);
        this.add(bishop);
        this.add(knight);
        this.add(rook);
    }

    private void addBlackButtons() {
        rook.setIcon(new ImageIcon("chessImages/BlackRook.png"));
        knight.setIcon(new ImageIcon("chessImages/BlackKnight.png"));
        bishop.setIcon(new ImageIcon("chessImages/BlackBishop.png"));
        queen.setIcon(new ImageIcon("chessImages/BlackQueen.png"));
    }

    private void addWhiteButtons() {
        rook.setIcon(new ImageIcon("chessImages/WhiteRook.png"));
        knight.setIcon(new ImageIcon("chessImages/WhiteKnight.png"));
        bishop.setIcon(new ImageIcon("chessImages/WhiteBishop.png"));
        queen.setIcon(new ImageIcon("chessImages/WhiteQueen.png"));
    }

// Action Listeners
    public void changeQueen() {

        if(color.equals("white")) {
            Board.whitePieces.remove(square.getPiece());

        } else {
            Board.blackPieces.remove(square.getPiece());
        }


        square.removeAll();

        int row = square.getPosition().getRow();
        int col = square.getPosition().getCol();


        square.setPiece(new Queen(color, new Position(row,col),board));
        square.updateImage();

        if(color.equals("white")) {
            Board.whitePieces.add(square.getPiece());
        } else {
            Board.blackPieces.add(square.getPiece());
        }


        this.dispose();
    }

    public void changeBishop() {
        if(color.equals("white")) {
            Board.whitePieces.remove(square.getPiece());
        } else {
            Board.blackPieces.remove(square.getPiece());
        }
        square.removeAll();

        int row = square.getPosition().getRow();
        int col = square.getPosition().getCol();

        square.setPiece(new Bishop(color, new Position(row,col), board));
        square.updateImage();
        if(color.equals("white")) {
            Board.whitePieces.add(square.getPiece());
        } else {
            Board.blackPieces.add(square.getPiece());
        }
        this.dispose();

    }

    public void changeKnight() {
        if(color.equals("white")) {
            Board.whitePieces.remove(square.getPiece());
        } else {
            Board.blackPieces.remove(square.getPiece());
        }
        square.removeAll();

        int row = square.getPosition().getRow();
        int col = square.getPosition().getCol();

        square.setPiece(new Knight(color, new Position(row,col),board));
        square.updateImage();
        if(color.equals("white")) {
            Board.whitePieces.add(square.getPiece());
        } else {
            Board.blackPieces.add(square.getPiece());
        }
        this.dispose();

    }

    public void changeRook() {
        if(color.equals("white")) {
            Board.whitePieces.remove(square.getPiece());
        } else {
            Board.blackPieces.remove(square.getPiece());
        }
        square.removeAll();

        int row = square.getPosition().getRow();
        int col = square.getPosition().getCol();

        square.setPiece(new Rook(color, new Position(row,col),board));
        square.updateImage();
        if(color.equals("white")) {
            Board.whitePieces.add(square.getPiece());
        } else {
            Board.blackPieces.add(square.getPiece());
        }
        this.dispose();

    }




}
