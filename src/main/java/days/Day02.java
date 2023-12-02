package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int sumPossibleGameIds(String input) {

        List<CubeCount> limits = List.of(new CubeCount(12, Color.RED), new CubeCount(13, Color.GREEN), new CubeCount(14, Color.BLUE));

        List<Game> games = parseGames(input);

        List<Game> impossibleGames = new ArrayList<>();
        for (Game game : games) {
            boolean isGamePossible = true;
            for (CubeCount cubeCount : game.cubeCounts) {
                for (CubeCount limit : limits) {
                    if (cubeCount.type.equals(limit.type) && cubeCount.count > limit.count && (!impossibleGames.contains(game))) {
                        impossibleGames.add(game);
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
            for (CubeCount cubeCount : game.cubeCounts) {
                if (cubeCount.type.equals(Color.RED) && cubeCount.count > maxRed) {
                    maxRed = cubeCount.count;
                } else if (cubeCount.type.equals(Color.GREEN) && (cubeCount.count > maxGreen)) {
                    maxGreen = cubeCount.count;
                } else if (cubeCount.type.equals(Color.BLUE) && (cubeCount.count > maxBlue)) {
                    maxBlue = cubeCount.count;
                }
            }
            powers.add(maxRed * maxGreen * maxBlue);
        }

        return powers.stream().reduce(0, Integer::sum);
    }


    private static List<Game> parseGames(String input) {
        // replace ; because it doesn't have any significance for both problems lol
        String[] lines = input.replace(";", ",").split("\n");

        List<Game> games = new ArrayList<>();

        int idCount = 1;
        for (String line : lines) {
            Game game = new Game();
            game.id = idCount;
            List<CubeCount> cubeCounts = new ArrayList<>();

            String cutGame = line.substring(line.indexOf(":") + 1);

            List<String> rawCubes = Arrays.stream(cutGame.split(",")).map(String::trim).toList();
            for (String rawCube : rawCubes) {
                String[] splitRawCube = rawCube.split(" ");
                CubeCount cubeCount = new CubeCount(Integer.parseInt(splitRawCube[0]), Color.valueOf(splitRawCube[1].toUpperCase()));
                cubeCounts.add(cubeCount);
            }

            game.cubeCounts = cubeCounts;
            games.add(game);
            idCount++;
        }

        return games;
    }

    static class Game {
        int id;
        List<CubeCount> cubeCounts;
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
