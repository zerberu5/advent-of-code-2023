package days;

import org.junit.jupiter.api.Test;
import utils.AocUtils;

import static org.junit.jupiter.api.Assertions.*;

class Day02Test {

    String exampleInputPart1;
    String realInputPart1;

    public Day02Test() {
        this.exampleInputPart1 = AocUtils.readFile("src/test/java/resources/day02/Day02_ExampleInput.txt");
        this.realInputPart1 = AocUtils.readFile("src/test/java/resources/day02/Day02_RealInput.txt");
    }

    @Test
    void testSumPossibleGameIdsExampleInput() {
        assertEquals(8, Day02.sumPossibleGameIds(exampleInputPart1));
    }

    @Test
    void testSumPossibleGameIdsRealInput() {
        assertEquals(2149, Day02.sumPossibleGameIds(realInputPart1));
    }

    @Test
    void testSumProductsOfHighestCubeCountsExampleInput() {
        assertEquals(2286, Day02.sumProductsOfHighestCubeCounts(exampleInputPart1));
    }

    @Test
    void testSumProductsOfHighestCubeCountsRealInput() {
        assertEquals(71274, Day02.sumProductsOfHighestCubeCounts(realInputPart1));
    }
}
