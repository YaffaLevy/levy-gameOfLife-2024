package levy.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class GameOfLifeFrame extends JFrame {
    private final GameOfLife game = new GameOfLife(100, 100);
    private final GameOfLifeComponent gameComponent = new GameOfLifeComponent(game);
    private final GameOfLifeController controller = new GameOfLifeController(game, gameComponent);

    public GameOfLifeFrame() {
        setSize(1000, 1000);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(gameComponent, BorderLayout.CENTER);

        JButton playButton = new JButton("►");
        JButton pauseButton = new JButton("⏸");
        JButton pasteButton = new JButton("Paste");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(pasteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        playButton.addActionListener((ActionEvent e) -> {
            controller.startTimer();
        });

        pasteButton.addActionListener((ActionEvent e) -> {
            controller.startTimer();
        });


        pasteButton.addActionListener((ActionEvent e) -> {
            try {
                    String clipboardContents = (String) Toolkit.getDefaultToolkit()
                            .getSystemClipboard()
                            .getData(DataFlavor.stringFlavor);
                    controller.paste(clipboardContents);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        });

    }
}