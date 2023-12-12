package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    String exampleInput;
    String realInput;

    public Day07Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day07/Day07_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day07/Day07_RealInput.txt");
    }


    @Test
    void testCalcTotalWinningsExampleInput() {
        assertEquals(6440, Day07.calcTotalWinnings(exampleInput));
    }

    @Test
    void testCalcTotalWinningsRealInput() {
        assertEquals(251121738, Day07.calcTotalWinnings(realInput));
    }
}
