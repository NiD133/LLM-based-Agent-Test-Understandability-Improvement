package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillCharArrayRangeNull extends AbstractLangTest {

    /**
     * Verifies that filling a null char array over a range returns null.
     * ArrayFill.fill is null-safe: when the input array is null, the method
     * skips the fill operation and returns null unchanged.
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
