package days;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    static String[][] grid;

    public static int sumPartNumbers(String input) {

        grid = loadGrid(input);
        List<IntContainer> intContainers = getNumberCoordinates(input);

        List<Integer> partNumber = new ArrayList<>();
        for (IntContainer intContainer : intContainers) {
            int value = intContainer.value;
            if (touchesUpperLeftSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesUpperRightSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesBottomLeftSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesBottomRightSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesUpperSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesLeftSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesRightSymbol(intContainer)) {
                partNumber.add(value);
            } else if (touchesBottomSymbol(intContainer)) {
                partNumber.add(value);
            }

        }

        return partNumber.stream().reduce(0, Integer::sum);
    }

    private static boolean touchesUpperLeftSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.indices;
        int row = intContainer.row - 1;
        int column = indices.get(0) - 1;
        if (row >= 0 && column >= 0) {
            return isSymbol(grid[row][column]);
        }
        return false;
    }

    private static boolean touchesUpperRightSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.indices;
        int row = intContainer.row - 1;
        int column = indices.get(indices.size() - 1) + 1;
        if (row >= 0 && column < grid.length) {
            return isSymbol(grid[row][column]);
        }
        return false;
    }

    private static boolean touchesBottomLeftSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.indices;
        int row = intContainer.row + 1;
        int column = indices.get(0) - 1;
        if (row < grid.length && column >= 0) {
            return isSymbol(grid[row][column]);
        }
        return false;
    }

    private static boolean touchesBottomRightSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.indices;
        int row = intContainer.row + 1;
        int column = indices.get(indices.size() - 1) + 1;
        if (row < grid.length && column < grid.length) {
            return isSymbol(grid[row][column]);
        }

        return false;
    }

    private static boolean touchesUpperSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.indices;
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
        int column = intContainer.indices.get(0) - 1;
        if (column >= 0) {
            return isSymbol(grid[intContainer.row][column]);
        }
        return false;
    }

    private static boolean touchesRightSymbol(IntContainer intContainer) {
        int column = intContainer.indices.get(intContainer.indices.size() - 1) + 1;
        if (column < grid.length) {
            return isSymbol(grid[intContainer.row][column]);
        }
        return false;
    }

    private static boolean touchesBottomSymbol(IntContainer intContainer) {
        List<Integer> indices = intContainer.indices;
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
                    intContainer.indices = indices;
                    intContainers.add(intContainer);
                }
            }
        }
        return intContainers;
    }


    private static int sumAllInts(String input) {
        List<Integer> intList = findIntegers(input);
        return intList.stream().reduce(0, Integer::sum);
    }

    // baeldung
    private static List<Integer> findIntegers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<String> integerList = new ArrayList<>();
        while (matcher.find()) {
            integerList.add(matcher.group());
        }

        return integerList.stream().map(Integer::parseInt).toList();
    }

    private static boolean isSymbol(String str) {
        char c = str.charAt(0);
        return !(Character.isDigit(c) || c == '.');
    }

    static class IntContainer {
        int row;
        List<Integer> indices;
        int value;
    }

}
