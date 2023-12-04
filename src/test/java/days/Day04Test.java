package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day04Test {

    String exampleInput;
    String realInput;

    public Day04Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day04/Day04_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day04/Day04_RealInput.txt");
    }

    @Test
    void testCalcScratchcardPointsExampleInput() {
        assertEquals(13, Day04.calcScratchcardPoints(exampleInput));
    }

    @Test
    void testCalcScratchcardPointsRealInput() {
        assertEquals(26218, Day04.calcScratchcardPoints(realInput));
    }

    @Test
    void testCalcCardInstancesExampleInput() {
        assertEquals(30, Day04.calcCardInstances(exampleInput));
    }
}
