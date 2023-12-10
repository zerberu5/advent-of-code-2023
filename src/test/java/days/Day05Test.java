package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day05Test {

    String exampleInput;
    String realInput;

    public Day05Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day05/Day05_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day05/Day05_RealInput.txt");
    }


    @Test
    void testCalcLowestLocationExampleInput() {
        assertEquals(35, Day05.calcLowestLocation(exampleInput));
    }

    @Test
    void testCalcLowestLocationRealInput() {
        assertEquals(551761867, Day05.calcLowestLocation(realInput));
    }

    @Test
    void testCalcLowestLocationByRangeExampleInput() {
        assertEquals(46, Day05.calcLowestLocationByRange(exampleInput));
    }

    @Test // takes 8-10 minutes
    void testCalcLowestLocationByRangeRealInput() {
        assertEquals(57451709, Day05.calcLowestLocationByRange(realInput));
    }
}