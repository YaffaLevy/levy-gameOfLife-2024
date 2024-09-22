package levy.gameoflife;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RLEParser {
    int width;
    int height;
    private int[][] grid;

    public int[][] parse(String rleContent) {
        String[] lines = rleContent.split("\n");
        Pattern headerPattern = Pattern.compile("x\\s*=\\s*(\\d+),\\s*y\\s*=\\s*(\\d+)");
        int currentRow = 0, currentCol = 0;
        boolean gridInitialized = false;
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("#")) {
                continue;
            }
            Matcher matcher = headerPattern.matcher(line);
            if (matcher.find() && !gridInitialized) {
                this.width = Integer.parseInt(matcher.group(1));
                this.height = Integer.parseInt(matcher.group(2));
                this.grid = new int[height][width];
                gridInitialized = true;
                continue;
            }

            StringBuilder digitBuilder = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (Character.isDigit(ch)) {
                    digitBuilder.append(ch);
                } else {
                    int runCount = digitBuilder.length() > 0 ? Integer.parseInt(digitBuilder.toString()) : 1;
                    digitBuilder.setLength(0);

                    if (ch == 'o' || ch == 'b') {
                        currentCol = fillGrid(runCount, ch, currentCol, currentRow);
                    } else if (ch == '$') {
                        currentRow++;
                        currentCol = 0;
                    } else if (ch == '!') {
                        return grid;
                    }
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
                grid[row][col++] = 0;
            }
        }
        return col;
    }
}
