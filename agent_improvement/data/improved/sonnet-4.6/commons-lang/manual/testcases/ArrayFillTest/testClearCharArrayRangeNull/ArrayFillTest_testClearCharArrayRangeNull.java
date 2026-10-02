package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearCharArrayRangeNull extends AbstractLangTest {

    /**
     * Verifies that clearing a null char array over any range returns null,
     * confirming the method handles null input gracefully without throwing.
     */
    @Test
    void testClearCharArrayRangeNull() {
        // Pass null as the array; fromIndex=0 and toIndex=0 represent an empty range
        final char[] nullArray = null;
        final char[] result = ArrayFill.clear(nullArray, 0, 0);
        assertNull(result, "Clearing a null char array should return null");
    }
}
