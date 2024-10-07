package levy.gameoflife;

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

    public void loadFromRle(String rleContent) {
        RleParser parser = new RleParser();
        int[][] newPattern = parser.parse(rleContent);

        int patternWidth = parser.getWidth();
        int patternHeight = parser.getHeight();

        this.width = Math.max(this.width, patternWidth);
        this.height = Math.max(this.height, patternHeight);
        this.grid = new int[height][width];

        int startX = (width - patternWidth) / 2;
        int startY = (height - patternHeight) / 2;

        for (int i = 0; i < patternHeight; i++) {
            System.arraycopy(newPattern[i], 0, grid[startY + i], startX, patternWidth);
        }

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