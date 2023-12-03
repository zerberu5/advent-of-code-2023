package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    String exampleInputPart1;
    String realInputPart1;

    public Day03Test() {
        this.exampleInputPart1 = AocUtils.readFile("src/test/java/resources/day03/Day03_ExampleInput.txt");
        this.realInputPart1 = AocUtils.readFile("src/test/java/resources/day03/Day03_RealInput.txt");
    }

    @Test
    void testSumSymbolNumsExampleInput() {
        assertEquals(4361, Day03.sumPartNumbers(exampleInputPart1));
    }

    @Test
    void testSumSymbolNumsRealInput() {
        assertEquals(4361, Day03.sumPartNumbers(realInputPart1));
    }
}
