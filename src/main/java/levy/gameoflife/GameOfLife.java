package levy.gameoflife;

public class GameOfLife {
    private int width;
    private int height;
    int[][] grid;

    public GameOfLife(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new int[height][width];
    }

    public int[][] getGrid() {
        return grid;
    }

    //next gen method
    public void nextGen() {
        int[][] newGrid = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                // rules
                if (grid[i][j] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[i][j] = 0;
                    } else {
                        newGrid[i][j] = 1;
                    }
                } else if (liveNeighbors == 3) {
                    newGrid[i][j] = 1;
                }
            }
        }

        //new gen
        grid = newGrid;
    }

    // count live neighbors
    private int countLiveNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newX = x + i;
                int newY = y + j;
                if (isInBounds(newX, newY) && grid[newX][newY] == 1) {
                    count++;
                }
            }
        }

        return count;
    }

    // checks bounds
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
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

