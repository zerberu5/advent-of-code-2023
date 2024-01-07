package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day09Test {

    String exampleInput;
    String realInput;

    public Day09Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day09/Day09_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day09/Day09_RealInput.txt");
    }

    @Test
    void testPredictNextValueExampleInput() {
        assertEquals(114, Day09.predictNextValue(exampleInput));
    }

    @Test
    void testPredictNextValueRealInput() {
        assertEquals(1819125966, Day09.predictNextValue(realInput));
    }

    @Test
    void testPredictFirstValueExampleInput() {
        assertEquals(2, Day09.predictFirstValue(exampleInput));
    }

    @Test
    void testPredictFirstValueRealInput() {
        assertEquals(1140, Day09.predictFirstValue(realInput));
    }
}