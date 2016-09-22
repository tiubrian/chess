package board;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import pieces.Piece;
import runner.Position;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Brian on 15/12/12.
 */
public class Square extends JPanel implements Cloneable {

    private int cellSize;
    private String color;
    private Position position;
    private Piece piece = null;
    private String image = null;
    private Board board;

// Constructor
    public Square(int cellSize, String color, Position position, Board board) {
        this.board = board;
        this.cellSize = cellSize;
        this.color = color;
        this.position = position;

        this.setLayout(new FlowLayout());
        this.setBackground(setColor());

    }

    public void addBorder() {
        this.setBorder(new LineBorder(Color.red, 2));
    }

    public void updateImage() {
        JLabel pic;
        this.removeAll();

        if(piece == null) {
            image = "";
            pic = new JLabel(image);
        } else {
            image = piece.getImage();
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File(image));
            } catch (IOException e) {
                System.out.println(image);
                e.printStackTrace();
            }
            pic = new JLabel(new ImageIcon(myPicture));


        }
        this.add(pic);
    }

    private Color setColor() {
        if (color.equals("dark")) {
            return new Color(64, 94, 176);
        } else {
            return new Color(219, 224, 238);
        }
    }

// Getters and Setters
    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

// Overrides
    @Override
    public Object clone() throws CloneNotSupportedException {
        Square tempSquare = (Square) super.clone();
        tempSquare.piece = (Piece) piece.clone();

        return tempSquare;
    }

}
