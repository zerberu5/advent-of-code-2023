package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int sumPossibleGameIds(String input) {

        List<CubeCount> limits = List.of(new CubeCount(12, Color.RED), new CubeCount(13, Color.GREEN),
                new CubeCount(14, Color.BLUE));

        List<Game> games = parseGames(input);

        List<Game> impossibleGames = new ArrayList<>();
        for (Game game : games) {
            boolean isGamePossible = true;
            for (List<CubeCount> set : game.cubeSets) {
                // man kann die sets eigentlich ignorieren
                for (CubeCount cubeCount : set) {
                    for (CubeCount limit : limits) {
                        if (cubeCount.type.equals(limit.type) && cubeCount.count > limit.count && (!impossibleGames.contains(game))) {
                            impossibleGames.add(game);
                        }
                    }
                }
            }
        }

        Integer allGamesIdSum = games.stream().map(game -> game.id).toList().stream().reduce(0, Integer::sum);
        Integer impossibleGamesIdSum = impossibleGames.stream().map(game -> game.id).toList().stream().reduce(0, Integer::sum);

        return allGamesIdSum - impossibleGamesIdSum;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int sumProductsOfHighestCubeCounts(String input) {
        List<Game> games = parseGames(input);

        List<Integer> powers = new ArrayList<>();
        for (Game game : games) {
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;
            for (List<CubeCount> set : game.cubeSets) {
                for (CubeCount cubeCount : set) {
                    if (cubeCount.type.equals(Color.RED) && cubeCount.count > maxRed) {
                        maxRed = cubeCount.count;
                    } else if (cubeCount.type.equals(Color.GREEN) && (cubeCount.count > maxGreen)) {
                        maxGreen = cubeCount.count;
                    } else if (cubeCount.type.equals(Color.BLUE) && (cubeCount.count > maxBlue)) {
                        maxBlue = cubeCount.count;
                    }
                }
            }
            powers.add(maxRed * maxGreen * maxBlue);
        }

        return powers.stream().reduce(0, Integer::sum);
    }


    private static List<Game> parseGames(String input) {
        String[] lines = input.split("\n");

        List<Game> games = new ArrayList<>();

        int idCount = 1;
        for (String line : lines) {
            Game game = new Game();
            game.id = idCount;
            List<List<CubeCount>> cubeSets = new ArrayList<>();

            String cutGame = line.substring(line.indexOf(":") + 1);
            String[] rawSets = cutGame.split(";");
            for (String rawSet : rawSets) {
                List<String> rawCubes = Arrays.stream(rawSet.split(",")).map(String::trim).toList();
                List<CubeCount> cubeSet = new ArrayList<>();
                for (String rawCube : rawCubes) {
                    String[] splitRawCube = rawCube.split(" ");
                    CubeCount cubeCount = new CubeCount(Integer.parseInt(splitRawCube[0]), Color.valueOf(splitRawCube[1].toUpperCase()));
                    cubeSet.add(cubeCount);
                }
                cubeSets.add(cubeSet);
            }
            game.cubeSets = cubeSets;
            games.add(game);
            idCount++;
        }

        return games;
    }

    static class Game {
        int id;
        List<List<CubeCount>> cubeSets;
    }

    static class CubeCount {
        Color type;
        int count;

        public CubeCount(int count, Color type) {
            this.count = count;
            this.type = type;
        }
    }

    enum Color {
        RED, GREEN, BLUE
    }
}
