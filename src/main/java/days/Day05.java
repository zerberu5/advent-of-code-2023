package days;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day05 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static long calcLowestLocation(String input) {
        MappingOverview mappingOverview = parseAlmanac(input);
        long min = Long.MAX_VALUE;
        for (Long seed : mappingOverview.seeds) {
            min = mapSourceToDest(mappingOverview, min, seed);
        }

        return min;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static long calcLowestLocationByRange(String input) {
        MappingOverview mappingOverview = parseAlmanac(input);

        List<Long> ranges = mappingOverview.seeds;
        long min = Long.MAX_VALUE;
        for (int i = 0; i < ranges.size(); i += 2) {
            Long begin = ranges.get(i);
            Long end = ranges.get(i + 1);
            for (Long j = begin; j < begin + end; j++) {
                long seed = j;
                min = mapSourceToDest(mappingOverview, min, seed);
            }
        }

        return min;
    }

    private static long mapSourceToDest(MappingOverview mappingOverview, long min, long seed) {
        for (Schema schema : mappingOverview.schemas) {
            boolean mapped = false;
            int i = 0;
            while (!mapped && i < schema.mappingRules.size()) {
                MappingRule mappingRule = schema.mappingRules.get(i);
                if (seed >= mappingRule.source && seed < mappingRule.source + mappingRule.range) {
                    long diff = calcDiff(mappingRule);
                    if (mappingRule.source < mappingRule.destination) {
                        seed = seed + diff;
                    } else {
                        seed = seed - diff;
                    }
                    mapped = true;
                }
                i++;
            }
        }
        if (seed < min) {
            min = seed;
        }

        return min;
    }

    private static long calcDiff(MappingRule mappingRule) {
        long diff;
        if (mappingRule.source > mappingRule.destination) {
            diff = mappingRule.source - mappingRule.destination;
        } else {
            diff = mappingRule.destination - mappingRule.source;
        }

        return diff;
    }

    private static MappingOverview parseAlmanac(String input) {
        List<Long> seeds = parseSeeds(input);
        List<Schema> schemas = parseSchemas(input);

        MappingOverview mappingOverview = new MappingOverview();
        mappingOverview.seeds = seeds;
        mappingOverview.schemas = schemas;

        return mappingOverview;
    }

    private static List<Schema> parseSchemas(String input) {
        String[] rawInput = input.split("\n\n");

        List<Schema> schemas = new ArrayList<>();
        for (long i = 1; i < rawInput.length; i++) {
            Schema schema = new Schema();
            String rawMap = rawInput[(int) i];
            String[] rawMapLines = rawMap.split("\n");
            schema.name = rawMapLines[0];
            List<MappingRule> mappingRules = new ArrayList<>();
            for (long j = 1; j < rawMapLines.length; j++) {
                String[] rawNums = rawMapLines[(int) j].split(" ");

                MappingRule mappingRule = new MappingRule();
                mappingRule.destination = Long.parseLong(rawNums[0]);
                mappingRule.source = Long.parseLong(rawNums[1]);
                mappingRule.range = Long.parseLong(rawNums[2]);
                mappingRules.add(mappingRule);
            }

            schema.mappingRules = mappingRules;
            schemas.add(schema);
        }

        return schemas;
    }

    private static List<Long> parseSeeds(String input) {
        return Arrays.stream(input.split("\n")[0].split(": ")[1].split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
    }

    static class MappingOverview {
        List<Long> seeds;
        List<Schema> schemas;
    }

    static class Schema {
        String name;
        List<MappingRule> mappingRules;
    }

    static class MappingRule {
        long source;
        long destination;
        long range;
    }
}
