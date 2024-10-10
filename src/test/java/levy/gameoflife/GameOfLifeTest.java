package levy.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameOfLifeTest {

    private static final String GLIDER_RLE = """
        #C This is a glider.
        x = 3, y = 3
        bo$2bo$3o!
    """;

    @Test
    public void loadFromRle() {
        GameOfLife game = new GameOfLife(100, 100);

        game.loadRle(GLIDER_RLE);

        int startX = (game.getWidth() - 3) / 2;
        int startY = (game.getHeight() - 3) / 2;

        assertEquals(1, game.getCellState(startY, startX + 1));
        assertEquals(1, game.getCellState(startY + 1, startX + 2));
        assertEquals(1, game.getCellState(startY + 2, startX));
        assertEquals(1, game.getCellState(startY + 2, startX + 1));
        assertEquals(1, game.getCellState(startY + 2, startX + 2));
    }

    @Test
    public void nextGen() {
        GameOfLife game = new GameOfLife(5, 5);
        game.setGrid(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        });

        game.nextGen();
        String actual = game.toString();

        assertEquals(
                "00000\n"
                        + "00100\n"
                        + "00100\n"
                        + "00100\n"
                        + "00000\n",
                actual
        );
    }
}
