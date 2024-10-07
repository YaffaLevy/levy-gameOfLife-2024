package levy.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class GameOfLifeFrame extends JFrame {

    private final GameOfLife game = new GameOfLife(100, 100);
    private Timer timer;

    public GameOfLifeFrame() {
        setSize(1000, 1000); // Made the frame larger for better visibility
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        GameOfLifeComponent gameComponent = new GameOfLifeComponent(game);
        add(gameComponent, BorderLayout.CENTER);

        JButton playButton = new JButton("►");
        JButton pauseButton = new JButton("⏸");
        JButton pasteButton = new JButton("Paste");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(pasteButton);
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

        pasteButton.addActionListener(e -> {
            try {
                String clipboardContent = (String) Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .getData(DataFlavor.stringFlavor);
                System.out.println("Clipboard Content: " + clipboardContent);

                if (isUrl(clipboardContent)) {
                    InputStream in = new URL(clipboardContent).openStream();
                    String rleContents = IOUtils.toString(in, "UTF-8");
                    game.loadFromRle(rleContents);
                } else if (isFilePath(clipboardContent)) {
                    FileInputStream fisTargetFile = new FileInputStream(new File(clipboardContent));
                    String rleContents = IOUtils.toString(fisTargetFile, "UTF-8");
                    game.loadFromRle(rleContents);
                } else {

                    game.loadFromRle(clipboardContent);
                }

                repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error reading clipboard content: " + ex.getMessage());
            }
        });
    }

    private boolean isUrl(String content) {
        return content.startsWith("http://") || content.startsWith("https://");
    }

    private boolean isFilePath(String content) {
        return new File(content).exists();
    }
}