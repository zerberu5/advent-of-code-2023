package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    String exampleInput;
    String realInput;
    String folmingInput;

    public Day07Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day07/Day07_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day07/Day07_RealInput.txt");
        this.folmingInput = AocUtils.readFile("src/test/java/resources/day07/Day07_FolmingInput.txt");
    }


    @Test
    void testCalcTotalWinningsExampleInput() {
        assertEquals(6440, Day07.calcTotalWinnings(exampleInput));
    }

    @Test
    void testCalcTotalWinningsRealInput() {
        assertEquals(251121738, Day07.calcTotalWinnings(realInput));
    }

    @Test
    void testCalcTotalWinningsWithJokerExampleInput() {
        assertEquals(5905, Day07Part2.calcTotalWinningsWithJoker(exampleInput));
    }

    @Test
    void testCalcTotalWinningsWithJokerRealInput() {
        assertEquals(251421071, Day07Part2.calcTotalWinningsWithJoker(realInput));
    }

    @Test
    void testCalcTotalWinningsWithJokerFolmingInput() {
        assertEquals(247885995, Day07Part2.calcTotalWinningsWithJoker(folmingInput));
    }
}
