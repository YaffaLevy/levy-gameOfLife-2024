package levy.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameOfLifeTest {

    @Test
    public void rleParser() {
        GameOfLife game = new GameOfLife(5, 5);
        String rleContent = """
            #C This is a glider.
            x = 3, y = 3
            bo$2bo$3o!
        """;


        game.loadFromRle(rleContent);


        String actual = game.toString();


        assertEquals(
                "010\n"
                        + "001\n"
                        + "111\n",
                actual
        );
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