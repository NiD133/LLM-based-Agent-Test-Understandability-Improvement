package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillIntArray extends AbstractLangTest {

    @Test
    void testFillIntArray() {
        // Arrange: a 3-element int array and the value to fill it with
        final int[] inputArray = new int[3];
        final int fillValue = 1;

        // Act: fill every element with fillValue; the method must return the same array instance
        final int[] filledArray = ArrayFill.fill(inputArray, fillValue);

        // Assert: the returned reference is the original array (fluent / in-place contract)
        assertSame(inputArray, filledArray, "ArrayFill.fill should return the same array instance");

        // Assert: every element equals the fill value
        assertArrayEquals(new int[]{fillValue, fillValue, fillValue}, filledArray,
                "Every element of the filled array should equal the fill value");
    }
}
