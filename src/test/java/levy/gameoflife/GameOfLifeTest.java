package levy.gameoflife;

import levy.gameoflife.GameOfLife;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameOfLifeTest {

    @Test
    public void blinkerPattern() {

        GameOfLife game = new GameOfLife(5, 5);
        game.grid = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        game.nextGen();
        String actual = game.toString();
        assertEquals(
                "00000\n" +
                        "00100\n" +
                        "00100\n" +
                        "00100\n" +
                        "00000\n",
                actual
        );
    }
}

