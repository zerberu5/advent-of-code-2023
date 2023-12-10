package days;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int multiplyCountOfWinningGames(String input) {
        List<Record> records = parseDocument(input);

        List<Integer> wins = new ArrayList<>();
        for (Record record : records) {
            int count = 0;
            for (int millisec = 0; millisec < record.time; millisec++) {
                int leftoverTime = record.time - millisec;
                if (leftoverTime * millisec > record.distance) {
                    count++;
                }
            }
            wins.add(count);
        }

        return wins.stream().reduce(1, (a, b) -> a * b);
    }

    private static List<Record> parseDocument(String input) {
        String[] split = input.split("\n");
        List<String> times = Arrays.stream(split[0].split(": ")[1].split(" ")).filter(StringUtils::isNumeric).toList();
        List<String> distances = Arrays.stream(split[1].split(": ")[1].split(" ")).filter(StringUtils::isNumeric).toList();

        List<Record> records = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            Record record = new Record();

            record.time = Integer.parseInt(times.get(i));
            record.distance = Integer.parseInt(distances.get(i));
            records.add(record);
        }

        return records;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int calcCountOfWinningGames(String input) {
        long time = Integer.parseInt(input.split("\n")[0].split(": ")[1].replace(" ", ""));
        long distance = Long.parseLong(input.split("\n")[1].split(": ")[1].replace(" ", ""));
        int count = 0;
        for (int millisec = 0; millisec < time; millisec++) {
            long leftoverTime = time - millisec;
            if (leftoverTime * millisec > distance) {
                count++;
            }
        }
        return count;
    }

    static class Record {
        int time;
        int distance;
    }
}
