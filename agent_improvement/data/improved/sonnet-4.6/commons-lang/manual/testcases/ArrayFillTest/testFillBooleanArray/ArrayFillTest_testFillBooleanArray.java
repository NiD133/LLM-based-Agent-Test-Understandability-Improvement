package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillBooleanArray extends AbstractLangTest {

    @Test
    void testFillBooleanArray() {
        // Arrange: a 3-element array and the fill value
        final boolean[] inputArray = new boolean[3];
        final boolean fillValue = true;

        // Act: fill every element with the fill value
        final boolean[] filledArray = ArrayFill.fill(inputArray, fillValue);

        // Assert: the method returns the exact same array instance (fluent/in-place contract)
        assertSame(inputArray, filledArray, "fill() must return the same array instance it received");

        // Assert: every element equals the fill value
        final boolean[] expectedArray = {true, true, true};
        assertArrayEquals(expectedArray, filledArray, "all elements must be set to the fill value");
    }
}
