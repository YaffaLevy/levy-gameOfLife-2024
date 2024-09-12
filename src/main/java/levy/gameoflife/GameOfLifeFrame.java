package levy.gameoflife;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class GameOfLifeFrame extends JFrame {

    private final GameOfLife game = new GameOfLife(50, 40);

    public GameOfLifeFrame() {
        setSize(800, 600);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        int[][] initialGrid = new int[40][50];
        initialGrid[20][24] = 1;
        initialGrid[20][25] = 1;
        initialGrid[20][26] = 1;
        game.setGrid(initialGrid);

        setLayout(new BorderLayout());

        GameOfLifeComponent gameComponent = new GameOfLifeComponent(game);
        add(gameComponent, BorderLayout.CENTER);
    }
}
