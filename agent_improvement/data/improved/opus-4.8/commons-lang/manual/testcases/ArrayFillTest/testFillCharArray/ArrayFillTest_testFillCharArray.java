package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(char[], char)}.
 */
public class ArrayFillTest_testFillCharArray extends AbstractLangTest {

    @Test
    void testFillCharArray() {
        // Arrange: a fresh char array and the value we expect to be written into every slot.
        final char[] arrayToFill = new char[3];
        final char fillValue = 1;

        // Act: fill the array with the given value.
        final char[] returnedArray = ArrayFill.fill(arrayToFill, fillValue);

        // Assert: fill returns the very same array instance it was given (fluent style)...
        assertSame(arrayToFill, returnedArray);
        // ...and every element now holds the fill value.
        for (final char element : returnedArray) {
            assertEquals(fillValue, element);
        }
    }
}
