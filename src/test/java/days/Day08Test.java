package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

    String example1Input;
    String example2Input;
    String realInput;
    String folmingInput;

    public Day08Test() {
        this.example1Input = AocUtils.readFile("src/test/java/resources/day08/Day08_Example_1_Input.txt");
        this.example2Input = AocUtils.readFile("src/test/java/resources/day08/Day08_Example_2_Input.txt");
        this.realInput = AocUtils.readFile("src/test/java/resources/day08/Day08_RealInput.txt");
        this.folmingInput = AocUtils.readFile("src/test/java/resources/day08/Day08_FolmingInput.txt");
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
        assertEquals(251121738, Day08.countStepsToReachEnd(realInput));
    }

    @Test
    void testCountStepsToReachEndFolmingInput() {
        assertEquals(18113, Day08.countStepsToReachEnd(folmingInput));
    }
}
