package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    // NUL character ('\0') — the value that clear() writes into each element of the specified range
    private static final char NUL = '\0';

    @Test
    void testClearCharArrayRange() {
        // Original array with five characters; only the middle three (indices 1–3) will be cleared
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };

        // clear(array, fromIndex=1, toIndex=4) zeroes elements at indices 1, 2, and 3 in-place
        final char[] actual = ArrayFill.clear(array, 1, 4);

        // The method must return the same array instance (modification is in-place)
        assertSame(array, actual);

        // Boundary elements ('A' at index 0, 'E' at index 4) are untouched;
        // elements inside the range are replaced with NUL
        assertArrayEquals(new char[]{ 'A', NUL, NUL, NUL, 'E' }, actual);
    }
}
