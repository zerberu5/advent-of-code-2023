package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    String exampleInputPart1;
    String realInputPart1;
    String exampleInputPart2;
    String realInputPart2;

    Day01Test() throws IOException {
        this.exampleInputPart1 = AocUtils.readFile("src/test/java/resources/day01/Day01_1ExampleInput.txt");
        this.exampleInputPart2 = AocUtils.readFile("src/test/java/resources/day01/Day01_2ExampleInput.txt");

        this.realInputPart1 = AocUtils.readFile("src/test/java/resources/day01/Day01_1RealInput.txt");
        this.realInputPart2 = AocUtils.readFile("src/test/java/resources/day01/Day01_2RealInput.txt");
    }

    @Test
    void testSumCalibrationValuesExampleInput() {
        assertEquals(142, Day01.sumCalibrationValues(exampleInputPart1));
    }

    @Test
    void testSumCalibrationValuesRealInput() {
        assertEquals(56397, Day01.sumCalibrationValues(realInputPart1));
    }

    @Test
    void testSumCalibrationStringValuesExampleInput() {
        assertEquals(281, Day01.sumCalibrationStringValues(exampleInputPart2));
    }

    @Test
    void testSumCalibrationStringValuesRealInput() {
        assertEquals(55701, Day01.sumCalibrationStringValues(realInputPart2));
    }
}
