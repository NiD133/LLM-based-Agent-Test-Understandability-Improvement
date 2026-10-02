package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(char[], int, int, char)} when the input array is null.
 */
public class ArrayFillTest_testFillCharArrayRangeNull extends AbstractLangTest {

    /**
     * Filling a null char array over a range should be a no-op that simply
     * returns null, rather than throwing a NullPointerException.
     */
    @Test
    void testFillCharArrayRangeNull() {
        final char[] nullArray = null;
        final int fromIndex = 0;
        final int toIndex = 0;
        final char fillValue = 'Z';

        final char[] result = ArrayFill.fill(nullArray, fromIndex, toIndex, fillValue);

        assertNull(result, "Filling a null array should return null");
    }
}
