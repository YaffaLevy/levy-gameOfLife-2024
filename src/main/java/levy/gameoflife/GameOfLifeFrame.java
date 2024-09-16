package levy.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLifeFrame extends JFrame {

    private final GameOfLife game = new GameOfLife(50, 40);
    private Timer timer;

    public GameOfLifeFrame() {
        setSize(800, 600);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        game.setCell(20, 24, 1);
        game.setCell(20, 25, 1);
        game.setCell(20, 26, 1);

        setLayout(new BorderLayout());
        GameOfLifeComponent gameComponent = new GameOfLifeComponent(game);
        add(gameComponent, BorderLayout.CENTER);

        JButton playButton = new JButton("\u25B6");
        JButton pauseButton = new JButton("\u23F8");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        add(buttonPanel, BorderLayout.SOUTH);

        playButton.addActionListener(e -> {
            if (timer == null || !timer.isRunning()) {
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.nextGen();
                        repaint();
                    }
                });
                timer.start();
            }
        });

        pauseButton.addActionListener(e -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
        });
    }

}
