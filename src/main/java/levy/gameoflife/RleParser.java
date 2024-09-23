package levy.gameoflife;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RleParser {
    private int width;
    private int height;
    private int[][] grid;

    public int[][] parse(String rleContent) {
        String[] lines = rleContent.split("\n");
        Pattern headerPattern = Pattern.compile("x\\s*=\\s*(\\d+),\\s*y\\s*=\\s*(\\d+)");
        int currentRow = 0;
        int currentCol = 0;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("#")) {
                continue;
            }
            Matcher matcher = headerPattern.matcher(line);
            if (matcher.find() && grid == null) {
                this.width = Integer.parseInt(matcher.group(1));
                this.height = Integer.parseInt(matcher.group(2));
                this.grid = new int[height][width];
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
                    currentCol = fillGrid(runCount, tag, currentCol, currentRow);
                } else if (ch == 'o' || ch == 'b') {
                    currentCol = fillGrid(1, ch, currentCol, currentRow);
                } else if (ch == '$') {
                    currentRow++;
                    currentCol = 0;
                } else if (ch == '!') {
                    return grid;
                }
            }
        }
        return grid;
    }

    private int fillGrid(int runCount, char tag, int col, int row) {
        for (int i = 0; i < runCount; i++) {
            if (tag == 'o') {
                grid[row][col++] = 1;
            } else if (tag == 'b') {
                col++;
            }
        }
        return col;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
