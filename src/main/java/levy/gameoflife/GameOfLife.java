package levy.gameoflife;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameOfLife {
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int width;
    private int height;
    private int[][] grid;

    public GameOfLife(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[height][width];
    }

    public void setCell(int x, int y, int state) {
        if (isInBounds(x, y)) {
            grid[x][y] = state;
        }
    }

    public void setGrid(int[][] newGrid) {
        this.grid = newGrid;
    }

    public int getCellState(int x, int y) {
        return grid[x][y];
    }

    public void toggleCellState(int x, int y) {
        grid[x][y] = (grid[x][y] == 1) ? 0 : 1;
    }

    public void nextGen() {
        int[][] newGrid = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                if (grid[i][j] == 1) {
                    newGrid[i][j] = (liveNeighbors == 2 || liveNeighbors == 3) ? 1 : 0;
                } else {
                    newGrid[i][j] = (liveNeighbors == 3) ? 1 : 0;
                }
            }
        }
        grid = newGrid;
    }

    private int countLiveNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int newX = x + i;
                int newY = y + j;
                if (isInBounds(newX, newY) && grid[newX][newY] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    public void loadRle(String rleContent) {
        String[] lines = rleContent.split("\n");
        Pattern headerPattern = Pattern.compile("x\\s*=\\s*(\\d+),\\s*y\\s*=\\s*(\\d+)");
        int currentRow = 0;
        int currentCol = 0;

        int patternWidth = 0;
        int patternHeight = 0;
        int[][] parsedGrid = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("#")) {
                continue;
            }

            Matcher matcher = headerPattern.matcher(line);
            if (matcher.find() && parsedGrid == null) {
                patternWidth = Integer.parseInt(matcher.group(1));
                patternHeight = Integer.parseInt(matcher.group(2));
                parsedGrid = new int[patternHeight][patternWidth];
                continue;
            }

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (Character.isDigit(ch)) {
                    int runCount = ch - '0';
                    while (i + 1 < line.length() && Character.isDigit(line.charAt(i + 1))) {
                        runCount = runCount * 10 + (line.charAt(++i) - '0');
                    }
                    char tag = line.charAt(++i);
                    currentCol = fillGrid(parsedGrid, runCount, tag, currentCol, currentRow);
                } else if (ch == 'o' || ch == 'b') {
                    currentCol = fillGrid(parsedGrid, 1, ch, currentCol, currentRow);
                } else if (ch == '$') {
                    currentRow++;
                    currentCol = 0;
                } else if (ch == '!') {
                    break;
                }
            }
        }

        int offsetX = (this.width - patternWidth) / 2;
        int offsetY = (this.height - patternHeight) / 2;

        for (int i = 0; i < patternHeight; i++) {
            for (int j = 0; j < patternWidth; j++) {
                grid[offsetY + i][offsetX + j] = parsedGrid[i][j];
            }
        }
    }
    public void loadRleFromString(String rleContent) {
        loadRle(rleContent);
    }


    private int fillGrid(int[][] grid, int runCount, char tag, int col, int row) {
        for (int i = 0; i < runCount; i++) {
            if (tag == 'o') {
                grid[row][col++] = 1;
            } else if (tag == 'b') {
                col++;
            }
        }
        return col;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
