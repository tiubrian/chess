package runner;

/**
 * Created by Brian on 15/12/12.
 */
public class GuiRunner {
    public static void main(String[] args) {
        int cellSize = Integer.parseInt(args[0]);

        Game theGame = new Game(cellSize);
        theGame.newGame();
    }
}
