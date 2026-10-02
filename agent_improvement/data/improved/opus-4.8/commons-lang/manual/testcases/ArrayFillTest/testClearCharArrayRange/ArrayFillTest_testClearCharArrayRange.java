package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#clear(char[], int, int)}, which resets a sub-range of a
 * char array to the null character {@code '\0'} (the NUL char, not the digit '0').
 */
public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    /** The null character that {@code clear} writes into the targeted range. */
    private static final char NUL = '\0';

    @Test
    void testClearCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };

        // Clear the elements from index 1 (inclusive) to index 4 (exclusive),
        // i.e. positions 1, 2 and 3.
        final char[] result = ArrayFill.clear(array, 1, 4);

        // clear() fills in place and returns the very same array instance.
        assertSame(array, result);

        // Elements outside the [1, 4) range stay untouched...
        assertEquals('A', result[0]);
        assertEquals('E', result[4]);

        // ...while every element inside the range is reset to '\0'.
        assertEquals(NUL, result[1]);
        assertEquals(NUL, result[2]);
        assertEquals(NUL, result[3]);
    }
}
