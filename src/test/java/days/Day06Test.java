package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day06Test {

    String exampleInput;
    String realInput;

    public Day06Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day06/Day06_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day06/Day06_RealInput.txt");
    }


    @Test
    void testMultiplyCountOfWinningGamesExampleInput() {
        assertEquals(288, Day06.multiplyCountOfWinningGames(exampleInput));
    }

    @Test
    void testMultiplyCountOfWinningGamesRealInput() {
        assertEquals(2065338, Day06.multiplyCountOfWinningGames(realInput));
    }

    @Test
    void testCalcCountOfWinningGamesExampleInput() {
        assertEquals(71503, Day06.calcCountOfWinningGames(exampleInput));
    }

    @Test
    void testCalcCountOfWinningGamesRealInput() {
        assertEquals(34934171, Day06.calcCountOfWinningGames(realInput));
    }
}