package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRange extends AbstractLangTest {

    @Test
    void testClearCharArrayRange() {
        final char[] array = { 'A', 'B', 'C', 'D', 'E' };
        final int fromIndex = 1;
        final int toIndex = 4; // exclusive upper bound

        final char[] actual = ArrayFill.clear(array, fromIndex, toIndex);

        assertSame(array, actual, "clear should return the same array instance (fluent API)");
        // Indices [fromIndex, toIndex) are zeroed; elements outside the range are unchanged
        final char[] expected = { 'A', '\0', '\0', '\0', 'E' };
        assertArrayEquals(expected, actual);
    }
}
