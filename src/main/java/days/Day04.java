package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day04 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int calcScratchcardPoints(String input) {
        String[] lines = input.split("\n");

        ScratchCard scratchCard = parseScratchCard(lines);

        return calcOverallCount(scratchCard.allYourNums, scratchCard.allWinningNums);
    }

    private static int calcOverallCount(List<List<Integer>> allYourNums, List<List<Integer>> allWinningNums) {
        int overallCount = 0;
        for (int i = 0; i < allYourNums.size(); i++) {
            int gameCount = 0;
            for (Integer num : allYourNums.get(i)) {
                if (allWinningNums.get(i).contains(num)) {
                    if (gameCount == 0) {
                        gameCount = 1;
                    } else {
                        gameCount *= 2;
                    }
                }
            }
            overallCount += gameCount;
        }
        return overallCount;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int calcCardInstances(String input) {
        String[] lines = input.split("\n");

        ScratchCard scratchCard = parseScratchCard(lines);

        return calcOverallCardInstances(scratchCard.allYourNums(), scratchCard.allWinningNums());
    }

    private static int calcOverallCardInstances(List<List<Integer>> allYourNums, List<List<Integer>> allWinningNums) {
        int overallCount = 0;
        for (int i = 0; i < allYourNums.size(); i++) {
            int gameCount = 0;
            for (Integer num : allYourNums.get(i)) {
                if (allWinningNums.get(i).contains(num)) {
                    gameCount++;
                }
            }
            overallCount += gameCount;
        }
        return overallCount;
    }

    private static ScratchCard parseScratchCard(String[] lines) {
        List<List<Integer>> allWinningNums = new ArrayList<>();
        List<List<Integer>> allYourNums = new ArrayList<>();
        for (String line : lines) {
            String[] game = line.split(":")[1].split("\\|");

            allWinningNums.add(parseNums(game[0]));
            allYourNums.add(parseNums(game[1]));
        }
        return new ScratchCard(allWinningNums, allYourNums);
    }

    private record ScratchCard(List<List<Integer>> allWinningNums, List<List<Integer>> allYourNums) {
    }

    private static List<Integer> parseNums(String nums) {
        return Arrays.stream(nums.trim().split(" ")).filter(element -> !element.isEmpty()).map(Integer::parseInt).toList();
    }
}
