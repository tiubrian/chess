package board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brian on 15/12/13.
 */
public class NewGameFrame extends JDialog {
    // Panels
    JPanel question = new JPanel(new FlowLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout());

    // Buttons
    JButton cancel = new JButton("Cancel");
    JButton yes = new JButton("Yes I'm sure");

    // Label
    JLabel theQuestion = new JLabel("Are you sure you want to start a new game?");

// Constructor
    public NewGameFrame() {
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setSize(300, 100);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.setTitle("New game");

        setQuestionPanel();
        setButtonPanel();
    }

    private void setQuestionPanel() {
        question.add(theQuestion);
        this.add(question);
    }

    private void setButtonPanel() {
        buttonPanel.add(yes);
        buttonPanel.add(cancel);

        // Add action listeners

        this.add(buttonPanel);
    }
}
