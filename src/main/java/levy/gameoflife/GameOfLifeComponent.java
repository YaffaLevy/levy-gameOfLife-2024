package levy.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOfLifeComponent extends JComponent {

    private final GameOfLife game;

    public GameOfLifeComponent(GameOfLife game) {
        this.game = game;


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = e.getY() / 10;
                int col = e.getX() / 10;

                if (game.isInBounds(row, col)) {
                    game.toggleCellState(row, col);
                    repaint();
                }
            }
        });


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int row = e.getY() / 10;
                int col = e.getX() / 10;

                if (game.isInBounds(row, col)) {
                    game.toggleCellState(row, col);
                    repaint();
                }
            }
        });

        Timer timer = new Timer(1000, e -> {
            game.nextGen();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                if (game.getCellState(i, j) == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * 10, i * 10, 10, 10);
                g.setColor(Color.GRAY);
                g.drawRect(j * 10, i * 10, 10, 10);
            }
        }
    }
}
