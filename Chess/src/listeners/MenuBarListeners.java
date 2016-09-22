package listeners;

import board.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brian on 15/12/12.
 */
public class MenuBarListeners implements ActionListener {
    Board theBoard;
    String command;

// Constructor
    public MenuBarListeners(Board theBoard, String command) {
        this.theBoard = theBoard;
        this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(command) {
            // Under file option
            case "new": theBoard.newGame(); break;
            case "options": theBoard.options(); break;
            case "exit": theBoard.exit(); break;

            // Under option option
            case "reverse": theBoard.reverse(); break;

            default: System.out.println("Error!");
        }
    }
}
