package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

    String example1Input;
    String example2Input;
    String exampleInputPart2;
    String realInput;
    String folmingInput;

    public Day08Test() {
        this.example1Input = AocUtils.readFile("src/test/java/resources/day08/Day08_Example_1_Input.txt");
        this.example2Input = AocUtils.readFile("src/test/java/resources/day08/Day08_Example_2_Input.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day08/Day08_RealInput.txt");
        this.folmingInput = AocUtils.readFile("src/test/java/resources/day08/Day08_FolmingInput.txt");
        this.exampleInputPart2 = AocUtils.readFile("src/test/java/resources/day08/Day08_Example_Input_Part2.txt");
    }

    @Test
    void testCountStepsToReachEndExample1Input() {
        assertEquals(2, Day08.countStepsToReachEnd(example1Input));
    }

    @Test
    void testCountStepsToReachEndExample2Input() {
        assertEquals(6, Day08.countStepsToReachEnd(example2Input));
    }

    @Test
    void testCountStepsToReachEndRealInput() {
        assertEquals(   15517, Day08.countStepsToReachEnd(realInput));
    }

    @Test
    void testCountStepsToReachEndFolmingInput() {
        assertEquals(18113, Day08.countStepsToReachEnd(folmingInput));
    }

    @Test
    void testCountStepsToReachEndFromAllAsExampleInput() {
        assertEquals(6, Day08.countStepsToReachEndFromAllAs(exampleInputPart2));
    }

    @Test
    void testCountStepsToReachEndFromAllAsRealInput() {
        assertEquals(14935034899483L, Day08.countStepsToReachEndFromAllAs(realInput));
    }

    @Test
    void testCountStepsToReachEndFromAllAsFolmingInput() {
        assertEquals(12_315_788_159_977L, Day08.countStepsToReachEndFromAllAs(folmingInput));
    }
}
