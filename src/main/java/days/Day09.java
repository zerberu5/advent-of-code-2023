package days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int predictNextValue(String input) {
        List<List<Integer>> rows = parseInput(input);

        int sum = 0;
        for (List<Integer> row : rows) {
            sum += calcRowsNextValue(row);
        }

        return sum;
    }

    private static int calcRowsNextValue(List<Integer> row) {
        List<List<Integer>> derivedRows = buildDerivedRows(row);
        return calcUpwardsForLastElement(derivedRows);
    }

    private static int calcUpwardsForLastElement(List<List<Integer>> derivedRows) {
        // start from the bottom
        Collections.reverse(derivedRows);
        int pivotElement = 0;
        for (int i = 0; i < derivedRows.size() - 1; i++) {
            List<Integer> upperRow = derivedRows.get(i + 1);
            Integer lastElement = upperRow.get(upperRow.size() - 1);
            pivotElement = pivotElement + lastElement;
        }

        return pivotElement;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int predictFirstValue(String input) {
        List<List<Integer>> rows = parseInput(input);

        int sum = 0;
        for (List<Integer> row : rows) {
            sum += calcRowsFirstValue(row);
        }

        return sum;
    }

    private static int calcRowsFirstValue(List<Integer> row) {
        List<List<Integer>> derivedRows = buildDerivedRows(row);
        return calcUpwardsForFirstElement(derivedRows);
    }

    private static List<List<Integer>> buildDerivedRows(List<Integer> row) {
        boolean finished = false;
        List<List<Integer>> derivedRows = new ArrayList<>();
        derivedRows.add(row);
        while (!finished) {
            List<Integer> differences = new ArrayList<>();
            for (int i = 0; i < row.size() - 1; i++) {
                differences.add(row.get(i + 1) - row.get(i));
            }

            if (differences.stream().allMatch(val -> val == 0)) {
                finished = true;
            }
            derivedRows.add(differences);

            row = differences;
        }

        return derivedRows;
    }

    private static int calcUpwardsForFirstElement(List<List<Integer>> derivedRows) {
        // start from the bottom
        Collections.reverse(derivedRows);
        int pivotElement = 0;
        for (int i = 0; i < derivedRows.size() - 1; i++) {
            List<Integer> upperRow = derivedRows.get(i + 1);
            Integer firstElement = upperRow.get(0);
            pivotElement = firstElement - pivotElement;
        }

        return pivotElement;
    }

    private static List<List<Integer>> parseInput(String input) {
        String[] split = input.split("\n");

        List<List<Integer>> rows = new ArrayList<>();
        for (String row : split) {
            List<Integer> vals = new ArrayList<>();
            String[] valsRaw = row.split(" ");
            for (String valRaw : valsRaw) {
                vals.add(Integer.parseInt(valRaw));
            }
            rows.add(vals);
        }

        return rows;
    }
}
