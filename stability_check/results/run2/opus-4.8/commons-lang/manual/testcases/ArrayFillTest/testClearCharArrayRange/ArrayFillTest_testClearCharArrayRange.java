package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link ArrayFill#clear(char[], int, int)}, which sets every element
 * in the half-open range {@code [fromIndex, toIndex)} to the NUL character
 * {@code '\0'} and returns the same array instance.
 */
public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    @Test
    void testClearCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };

        // Clear indices 1, 2 and 3 (toIndex = 4 is exclusive).
        final char[] result = ArrayFill.clear(array, 1, 4);

        // clear() mutates and returns the original array, not a copy.
        assertSame(array, result);

        // Index 0 is before the cleared range and stays unchanged.
        assertEquals('A', result[0]);

        // Indices 1..3 are within the range and are now NUL.
        assertEquals('\0', result[1]);
        assertEquals('\0', result[2]);
        assertEquals('\0', result[3]);

        // Index 4 is at/after the exclusive upper bound and stays unchanged.
        assertEquals('E', result[4]);
    }
}
