package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    @Test
    void testClearCharArrayRange() {
        // Clear indices [fromIndex, toIndex) — exclusive upper bound means index 4 is NOT cleared.
        final int fromIndex = 1;
        final int toIndex = 4;

        final char[] array = { 'A', 'B', 'C', 'D', 'E' };
        final char[] actual = ArrayFill.clear(array, fromIndex, toIndex);

        // clear() must return the same array reference (fluent in-place operation).
        assertSame(array, actual);

        // Indices outside [fromIndex, toIndex) are untouched; indices inside are zeroed.
        final char[] expected = { 'A', '\0', '\0', '\0', 'E' };
        assertArrayEquals(expected, actual);
    }
}
