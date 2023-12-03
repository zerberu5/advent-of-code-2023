package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    String exampleInput;
    String realInput;

    public Day03Test() {
        this.exampleInput = AocUtils.readFile("src/test/java/resources/day03/Day03_ExampleInput.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day03/Day03_RealInput.txt");
    }

    @Test
    void testSumSymbolNumsExampleInput() {
        assertEquals(4361, Day03.sumPartNumbers(exampleInput));
    }

    @Test
    void testSumSymbolNumsRealInput() {
        assertEquals(535078, Day03.sumPartNumbers(realInput));
    }

    @Test
    void testSumUpGearRatiosExamleInput() {
        assertEquals(467835, Day03.sumUpGearRatios(exampleInput));
    }

    @Test
    void testSumUpGearRatiosRealInput() {
        assertEquals(75312571, Day03.sumUpGearRatios(realInput));
    }
}
