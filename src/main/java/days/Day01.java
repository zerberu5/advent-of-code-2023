package days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int sumCalibrationValues(String input) {

        String[] lines = input.split("\n");

        int count = 0;
        for (String line : lines) {
            char firstDigit = getFirstDigit(line);
            char lastDigit = getLastDigit(line);
            StringBuilder sb = new StringBuilder();
            int concatValue = Integer.parseInt(String.valueOf(sb.append(firstDigit).append(lastDigit)));
            count += concatValue;
        }

        return count;
    }

    private static char getLastDigit(String line) {
        for (int i = line.length() - 1; i >= 0; i--) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                return c;
            }
        }
        return ' ';
    }

    private static char getFirstDigit(String line) {
        return line.chars().mapToObj(c -> (Character) (char) c).filter(Character::isDigit).findFirst().orElseThrow();
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int sumCalibrationStringValues(String input) {
        String[] lines = input.split("\n");
        List<BoundaryElements> digitsPerLine = parseDigitsPerLine(lines);

        int count = 0;
        for (BoundaryElements boundaryElements : digitsPerLine) {
            StringBuilder sb = new StringBuilder();
            int concatValue = Integer.parseInt(String.valueOf(sb.append(boundaryElements.first).append(boundaryElements.last)));
            count += concatValue;
        }

        return count;
    }

    private static List<BoundaryElements> parseDigitsPerLine(String[] lines) {

        List<BoundaryElements> numsPerLine = new ArrayList<>();

        Map<String, Integer> wordToNumber = new HashMap<>();
        wordToNumber.put("one", 1);
        wordToNumber.put("two", 2);
        wordToNumber.put("three", 3);
        wordToNumber.put("four", 4);
        wordToNumber.put("five", 5);
        wordToNumber.put("six", 6);
        wordToNumber.put("seven", 7);
        wordToNumber.put("eight", 8);
        wordToNumber.put("nine", 9);
        wordToNumber.put("1", 1);
        wordToNumber.put("2", 2);
        wordToNumber.put("3", 3);
        wordToNumber.put("4", 4);
        wordToNumber.put("5", 5);
        wordToNumber.put("6", 6);
        wordToNumber.put("7", 7);
        wordToNumber.put("8", 8);
        wordToNumber.put("9", 9);


        for (String line : lines) {
            BoundaryElements boundaryElements = parseBoundaryElements(line, wordToNumber);
            numsPerLine.add(boundaryElements);
        }

        return numsPerLine;
    }

    private static BoundaryElements parseBoundaryElements(String line, Map<String, Integer> wordToNumber) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            for (Map.Entry<String, Integer> entry : wordToNumber.entrySet()) {
                if (line.startsWith(entry.getKey(), i)) {
                    nums.add(entry.getValue());
                }
            }
        }

        BoundaryElements boundaryElements = new BoundaryElements();
        boundaryElements.first = nums.get(0);
        boundaryElements.last = nums.get(nums.size() - 1);
        return boundaryElements;
    }

    static class BoundaryElements {
        private int first;
        private int last;
    }
}
