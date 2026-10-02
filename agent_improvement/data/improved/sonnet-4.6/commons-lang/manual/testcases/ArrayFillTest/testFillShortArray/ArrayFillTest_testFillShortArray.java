package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillShortArray extends AbstractLangTest {

    @Test
    void testFillShortArray() {
        // Arrange: a 3-element array and the fill value
        final short fillValue = 1;
        final short[] inputArray = new short[3];

        // Act: fill every element with fillValue; method must return the same array instance
        final short[] filledArray = ArrayFill.fill(inputArray, fillValue);

        // Assert: the returned reference is the original array (fluent/in-place contract)
        assertSame(inputArray, filledArray,
                "ArrayFill.fill should return the same array instance, not a copy");

        // Assert: every element equals the fill value
        final short[] expectedArray = {fillValue, fillValue, fillValue};
        assertArrayEquals(expectedArray, filledArray,
                "Every element of the array should equal the fill value " + fillValue);
    }
}
