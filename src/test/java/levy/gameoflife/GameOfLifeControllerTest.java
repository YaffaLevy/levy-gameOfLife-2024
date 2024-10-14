package levy.gameoflife;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GameOfLifeControllerTest {

    private static final String GLIDER_RLE = """
                #N Glider
                #O Richard K. Guy
                #C The smallest, most common, and first discovered spaceship. Diagonal, has period 4 and speed c/4.
                #C www.conwaylife.com/wiki/index.php?title=Glider
                x = 3, y = 3, rule = B3/S23
                bob$2bo$3o!
            """.trim().replace("\n", "\r\n") + "\r\n";

    @Test
    void toggleCellOff() {
        GameOfLife model = mock();
        GameOfLifeComponent view = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);

        doReturn(10).when(view).getCellSize();
        doReturn(100).when(model).getWidth();
        doReturn(100).when(model).getHeight();
        doReturn(1).when(model).getCellState(5, 10);

        controller.toggleCell(50, 100);

        verify(model).setCell(5, 10, 0);
        verify(view).repaint();
    }

    @Test
    void toggleCellOn() {
        GameOfLife model = mock();
        GameOfLifeComponent view = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);

        doReturn(10).when(view).getCellSize();
        doReturn(100).when(model).getWidth();
        doReturn(100).when(model).getHeight();
        doReturn(0).when(model).getCellState(5, 10);

        controller.toggleCell(50, 100);

        verify(model).setCell(5, 10, 1);
        verify(view).repaint();
    }

    @Test
    void pasteRle() {
        GameOfLife model = mock();
        GameOfLifeComponent view = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);

        controller.paste(GLIDER_RLE);

        verify(model).loadRle(GLIDER_RLE);
        verify(view).repaint();
    }

    @Test
    void pasteUrl() {
        GameOfLife model = mock();
        GameOfLifeComponent view = mock();
        GameOfLifeController controller = new GameOfLifeController(model, view);

        String rle = "";


        controller.paste(rle);

        verify(model).loadRle("");
        verify(view).repaint();
    }
}
