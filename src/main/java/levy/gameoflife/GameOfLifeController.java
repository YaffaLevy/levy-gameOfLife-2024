package levy.gameoflife;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GameOfLifeController {
    private GameOfLife model;
    private GameOfLifeComponent view;
    private Timer timer;

    public GameOfLifeController(GameOfLife model, GameOfLifeComponent view) {
        this.model = model;
        this.view = view;
    }
    public void startTimer() {
        if (timer == null || !timer.isRunning()) {
            timer = new Timer(1000, e -> {
                model.nextGen();
                view.repaint();
            });
            timer.start();
        }
    }
    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
    public void paste(String clipboardContents) {
        try {
            if (clipboardContents.startsWith("http")) {
                InputStream in = new URL(clipboardContents).openStream();
                String rleContents = IOUtils.toString(in, StandardCharsets.UTF_8);
                model.loadRle(rleContents);
            } else if (new File(clipboardContents).exists()) {
                FileInputStream fisTargetFile = new FileInputStream(new File(clipboardContents));
                String rleContents = IOUtils.toString(fisTargetFile, StandardCharsets.UTF_8);
                model.loadRle(rleContents);
            } else {
                model.loadRle(clipboardContents);
            }

            view.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void toggleCell(int screenX, int screenY) {
        int x = screenX / view.getCellSize();
        int y = screenY / view.getCellSize();

        if (x < model.getWidth() && y < model.getHeight()) {
            int currentState = model.getCellState(x, y);
            model.setCell(x, y, currentState == 1 ? 0 : 1);
            view.repaint();
        }
    }
}

