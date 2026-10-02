package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#clear(char[], int, int)} when the input array is {@code null}.
 */
public class ArrayFillTest_testClearCharArrayRangeNull extends AbstractLangTest {

    /**
     * Clearing a {@code null} char array over a range should be a no-op that
     * simply returns {@code null}, rather than throwing an exception.
     */
    @Test
    void testClearCharArrayRangeNull() {
        final char[] nullArray = null;
        final int fromIndex = 0;
        final int toIndex = 0;

        final char[] result = ArrayFill.clear(nullArray, fromIndex, toIndex);

        assertNull(result, "Clearing a null array should return null");
    }
}
