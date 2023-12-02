package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day02 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int sumPossibleGameIds(String input) {

        List<Game> games = parseGames(input);

        List<Integer> gameIndicesToBeRemoved = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            if (isGameImpossible(games.get(i))) {
                gameIndicesToBeRemoved.add(i);
            }
        }

        // reversed, so the indices of following games won't change
        Collections.reverse(gameIndicesToBeRemoved);
        for (int i : gameIndicesToBeRemoved) {
            games.remove(i);
        }

        return games.stream().map(game -> game.id).toList().stream().reduce(0, Integer::sum);
    }

    private static boolean isGameImpossible(Game game) {
        for (CubeCount cubeCount : game.cubeCounts) {
            if (cubeCount.count > 12 && cubeCount.type.equals(Color.RED)
                    || cubeCount.count > 13 && cubeCount.type.equals(Color.GREEN)
                    || cubeCount.count > 14 && cubeCount.type.equals(Color.BLUE)) {
                return true;
            }
        }
        return false;
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
