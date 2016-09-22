package board;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brian on 15/12/13.
 */
public class GameOptionsFrame extends JDialog {
    JPanel playingMode = new JPanel(new FlowLayout());
    JPanel color = new JPanel(new FlowLayout());

    JButton okay = new JButton("Apply");
    JButton cancel = new JButton("Cancel");

// Constructor
    public GameOptionsFrame() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setResizable(false);

        this.setTitle("Game options");

        this.setSize(300, 200);

        setPlayingModePanel();
        setColorPanel();
        setButtonPanel();

    }

    private void setPlayingModePanel() {

    }
    private void setColorPanel() {

    }
    private void setButtonPanel() {

    }
}
