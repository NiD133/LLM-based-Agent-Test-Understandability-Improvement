package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillIntArray extends AbstractLangTest {

    /**
     * Verifies that {@link ArrayFill#fill(int[], int)} fills every element of the
     * given array with the supplied value and returns the very same array instance
     * (fluent style), rather than a copy.
     */
    @Test
    void testFillIntArray() {
        // Arrange: an array whose default contents (all zeros) differ from the fill value.
        final int[] arrayToFill = new int[3];
        final int fillValue = 1;

        // Act: fill the array in place.
        final int[] returnedArray = ArrayFill.fill(arrayToFill, fillValue);

        // Assert: the method returns the same array instance it was given...
        assertSame(arrayToFill, returnedArray);

        // ...and every element now holds the fill value.
        for (final int element : returnedArray) {
            assertEquals(fillValue, element);
        }
    }
}
