package levy.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeComponent extends JComponent {

    private final GameOfLife game;

    public GameOfLifeComponent(GameOfLife game) {
        this.game = game;

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int row = e.getY() / 10;
                int col = e.getX() / 10;

                if (game.isInBounds(row, col)) {
                    game.toggleCellState(row, col);
                    repaint();
                }
            }
        });

        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int row = e.getY() / 10;
                int col = e.getX() / 10;

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
                    g.fillRect(j * 10, i * 10, 10, 10);
                }
            }
        }

        g.setColor(Color.GRAY);
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                g.drawRect(j * 10, i * 10, 10, 10);
            }
        }
    }
}
