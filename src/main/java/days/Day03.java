package days;

import java.util.ArrayList;
import java.util.List;

public class Day03 {

    static String[][] grid;

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int sumPartNumbers(String input) {
        grid = loadGrid(input);
        List<IntContainer> intContainers = getNumberCoordinates(input);

        List<Integer> partNumber = new ArrayList<>();
        for (IntContainer intContainer : intContainers) {
            int value = intContainer.value;
            if (touchesUpperLeftSymbol(intContainer) || touchesUpperRightSymbol(intContainer) ||
                    touchesBottomLeftSymbol(intContainer) || touchesBottomRightSymbol(intContainer) ||
                    touchesUpperSymbol(intContainer) || touchesLeftSymbol(intContainer) ||
                    touchesRightSymbol(intContainer) || touchesBottomSymbol(intContainer)) {
                partNumber.add(value);
            }

        }

        return partNumber.stream().reduce(0, Integer::sum);
    }

    private static boolean touchesUpperLeftSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row - 1;
        int column = indices.get(0) - 1;
        if (row >= 0 && column >= 0) {
            return isSymbol(grid[row][column]);
        }
        return false;
    }

    private static boolean touchesUpperRightSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row - 1;
        int column = indices.get(indices.size() - 1) + 1;
        if (row >= 0 && column < grid.length) {
            return isSymbol(grid[row][column]);
        }
        return false;
    }

    private static boolean touchesBottomLeftSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row + 1;
        int column = indices.get(0) - 1;
        if (row < grid.length && column >= 0) {
            return isSymbol(grid[row][column]);
        }
        return false;
    }

    private static boolean touchesBottomRightSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row + 1;
        int column = indices.get(indices.size() - 1) + 1;
        if (row < grid.length && column < grid.length) {
            return isSymbol(grid[row][column]);
        }

        return false;
    }

    private static boolean touchesUpperSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row - 1;
        if (row >= 0) {
            for (Integer column : indices) {
                if (isSymbol(grid[row][column])) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean touchesLeftSymbol(IntContainer intContainer) {
        int column = intContainer.valueIndices.get(0) - 1;
        if (column >= 0) {
            return isSymbol(grid[intContainer.row][column]);
        }
        return false;
    }

    private static boolean touchesRightSymbol(IntContainer intContainer) {
        int column = intContainer.valueIndices.get(intContainer.valueIndices.size() - 1) + 1;
        if (column < grid.length) {
            return isSymbol(grid[intContainer.row][column]);
        }
        return false;
    }

    private static boolean touchesBottomSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row + 1;
        if (row < grid.length) {
            for (Integer column : indices) {
                if (isSymbol(grid[row][column])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String[][] loadGrid(String input) {
        String[] split = input.split("\n");
        String[][] tempGrid = new String[split.length][split[0].length()];

        for (int i = 0; i < split.length; i++) {
            String[] row = new String[split[0].length()];
            for (int j = 0; j < split[i].length(); j++) {
                row[j] = String.valueOf(split[i].charAt(j));
            }

            tempGrid[i] = row;
        }
        return tempGrid;
    }

    private static List<IntContainer> getNumberCoordinates(String input) {
        String[] split = input.split("\n");

        List<IntContainer> intContainers = new ArrayList<>();

        for (int h = 0; h < split.length; h++) {
            char[] charArray = split[h].toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                if (Character.isDigit(charArray[i])) {
                    IntContainer intContainer = new IntContainer();
                    List<Integer> indices = new ArrayList<>();
                    intContainer.row = h;
                    String valueStr = "";
                    while (i < charArray.length && Character.isDigit(charArray[i])) {
                        valueStr += charArray[i];
                        indices.add(i);
                        i++;
                    }
                    intContainer.value = Integer.parseInt(valueStr);
                    intContainer.valueIndices = indices;
                    intContainers.add(intContainer);
                }
            }
        }
        return intContainers;
    }

    private static boolean isSymbol(String str) {
        char c = str.charAt(0);
        return !(Character.isDigit(c) || c == '.');
    }

    static class IntContainer {
        int row;
        List<Integer> valueIndices;
        int value;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int sumUpGearRatios(String input) {
        grid = loadGrid(input);
        List<IntContainer> intContainers = getNumberCoordinates(input);
        List<Star> stars = getStarCoordinates(grid);

        for (Star star : stars) {
            for (IntContainer intContainer : intContainers) {
                if (starTouchesIntContainer(star, intContainer)) {
                    star.touchingValues.add(intContainer.value);
                }
            }
        }

        List<Star> starWithTwoTouches = stars.stream().filter(star -> star.touchingValues.size() == 2).toList();

        int sum = 0;

        for (Star star : starWithTwoTouches) {
            int ratio = star.touchingValues.get(0) * star.touchingValues.get(1);
            sum += ratio;
        }

        return sum;
    }

    private static boolean starTouchesIntContainer(Star star, IntContainer intContainer) {
        return touchesUpperLeftStar(star, intContainer) || touchesUpperRightStar(star, intContainer)
                || touchesBottomLeftStar(star, intContainer) || touchesUpperStar(star, intContainer)
                || touchesBottomRightStar(star, intContainer) || touchesLeftStar(star, intContainer)
                || touchesBottomStar(star, intContainer) || touchesRightStar(star, intContainer);
    }

    private static boolean touchesUpperLeftStar(Star star, IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row - 1;
        int column = indices.get(0) - 1;
        if (row >= 0 && column >= 0) {
            return isStar(grid[row][column]) && (row == star.row && column == star.column);
        }
        return false;
    }

    private static boolean touchesUpperRightStar(Star star, IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row - 1;
        int column = indices.get(indices.size() - 1) + 1;
        if (row >= 0 && column < grid.length) {
            return isStar(grid[row][column]) && (row == star.row && column == star.column);
        }
        return false;
    }

    private static boolean touchesBottomLeftStar(Star star, IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row + 1;
        int column = indices.get(0) - 1;
        if (row < grid.length && column >= 0) {
            return isStar(grid[row][column]) && (row == star.row && column == star.column);
        }
        return false;
    }

    private static boolean touchesBottomRightStar(Star star, IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row + 1;
        int column = indices.get(indices.size() - 1) + 1;
        if (row < grid.length && column < grid.length) {
            return isStar(grid[row][column]) && (row == star.row && column == star.column);
        }

        return false;
    }

    private static boolean touchesUpperStar(Star star, IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row - 1;
        if (row >= 0) {
            for (Integer column : indices) {
                if (isStar(grid[row][column]) && (row == star.row && column == star.column)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean touchesLeftStar(Star star, IntContainer intContainer) {
        int column = intContainer.valueIndices.get(0) - 1;
        if (column >= 0) {
            return isStar(grid[intContainer.row][column]) && (intContainer.row == star.row && column == star.column);
        }
        return false;
    }

    private static boolean touchesRightStar(Star star, IntContainer intContainer) {
        int column = intContainer.valueIndices.get(intContainer.valueIndices.size() - 1) + 1;
        if (column < grid.length) {
            return isStar(grid[intContainer.row][column]) && (intContainer.row == star.row && column == star.column);
        }
        return false;
    }

    private static boolean touchesBottomStar(Star star, IntContainer intContainer) {
        List<Integer> indices = intContainer.valueIndices;
        int row = intContainer.row + 1;
        if (row < grid.length) {
            for (Integer column : indices) {
                if (isStar(grid[row][column]) && (row == star.row && column == star.column)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static List<Star> getStarCoordinates(String[][] grid) {
        List<Star> stars = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].equals("*")) {
                    Star star = new Star();
                    star.row = i;
                    star.column = j;
                    star.touchingValues = new ArrayList<>();
                    stars.add(star);
                }
            }
        }
        return stars;
    }

    static boolean isStar(String str) {
        char c = str.charAt(0);
        return c == '*';
    }

    static class Star {
        int row;
        int column;
        List<Integer> touchingValues;
    }
}
