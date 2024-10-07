package levy.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public class GameOfLifeTest {

    private Clipboard clipboard;

    @BeforeEach
    public void setup() {
        clipboard = Mockito.mock(Clipboard.class);
    }

    @Test
    public void clipboardText() throws Exception {

        String rleContent = """
                #C This is a glider.
                x = 3, y = 3
                bo$2bo$3o!
                """;
        when(clipboard.getData(DataFlavor.stringFlavor)).thenReturn(rleContent);


        GameOfLife game = new GameOfLife(100, 100);
        game.loadFromRle(rleContent);


        int startX = (game.getWidth() - 3) / 2;
        int startY = (game.getHeight() - 3) / 2;

        assertEquals(1, game.getCellState(startY, startX + 1));  // First cell of the glider
        assertEquals(1, game.getCellState(startY + 1, startX + 2));  // Second row, third column
        assertEquals(1, game.getCellState(startY + 2, startX));  // Last row, first column
        assertEquals(1, game.getCellState(startY + 2, startX + 1));  // Last row, second column
        assertEquals(1, game.getCellState(startY + 2, startX + 2));  // Last row, third column
    }

    @Test
    public void clipboardUrl() throws Exception {
        String rleUrl = "http://www.someurl.com/glider.rle";
        when(clipboard.getData(DataFlavor.stringFlavor)).thenReturn(rleUrl);


        String rleContent = """
                x = 3, y = 3
                bo$2bo$3o!
                """;


        GameOfLife game = new GameOfLife(100, 100);
        game.loadFromRle(rleContent);

        int startX = (game.getWidth() - 3) / 2;
        int startY = (game.getHeight() - 3) / 2;

        assertEquals(1, game.getCellState(startY, startX + 1));
        assertEquals(1, game.getCellState(startY + 1, startX + 2));
        assertEquals(1, game.getCellState(startY + 2, startX));
        assertEquals(1, game.getCellState(startY + 2, startX + 1));
        assertEquals(1, game.getCellState(startY + 2, startX + 2));
    }

    @Test
    public void clipboardFile() throws Exception {
        String filePath = "/path/to/local/glider.rle";
        when(clipboard.getData(DataFlavor.stringFlavor)).thenReturn(filePath);

        String rleContent = """
                x = 3, y = 3
                bo$2bo$3o!
                """;
        GameOfLife game = new GameOfLife(100, 100);
        game.loadFromRle(rleContent);


        int startX = (game.getWidth() - 3) / 2;
        int startY = (game.getHeight() - 3) / 2;

        assertEquals(1, game.getCellState(startY, startX + 1));
        assertEquals(1, game.getCellState(startY + 1, startX + 2));
        assertEquals(1, game.getCellState(startY + 2, startX));
        assertEquals(1, game.getCellState(startY + 2, startX + 1));
        assertEquals(1, game.getCellState(startY + 2, startX + 2));
    }
}
