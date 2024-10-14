package levy.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeComponent extends JComponent {

    private final GameOfLife game;

    private final int cellSize = 10;

    public GameOfLifeComponent(GameOfLife game) {
        this.game = game;

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                if (game.isInBounds(row, col)) {
                    game.toggleCellState(row, col);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                if (game.isInBounds(row, col)) {
                    game.toggleCellState(row, col);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                if (game.getCellState(i, j) == 1) {
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }

        g.setColor(Color.GRAY);
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public int getCellSize() {
        return cellSize;
    }
}
