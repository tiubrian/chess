package listeners;

import board.PromoteFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brian on 15/12/22.
 */
public class PromotionListener implements ActionListener {
    PromoteFrame frame;
    String action;

// Constructor
    public PromotionListener(PromoteFrame frame, String action) {
        this.frame = frame;
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (action) {
            case "queen": frame.changeQueen(); break;
            case "bishop": frame.changeBishop(); break;
            case "knight": frame.changeKnight(); break;
            case "rook": frame.changeRook(); break;
            default: System.out.println("Error!");
        }
    }
}
