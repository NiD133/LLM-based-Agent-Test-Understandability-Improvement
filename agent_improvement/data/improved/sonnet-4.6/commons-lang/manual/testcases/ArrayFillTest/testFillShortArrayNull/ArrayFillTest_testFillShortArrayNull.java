package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillShortArrayNull extends AbstractLangTest {

    /**
     * Verifies that ArrayFill.fill returns null unchanged when the input short array is null.
     * The fill value is irrelevant here; the contract is that a null array is returned as-is
     * without throwing a NullPointerException.
     */
    @Test
    @DisplayName("fill(short[], short) returns null when given a null array")
    void testFillShortArrayNull() {
        final short[] nullArray = null;
        final short fillValue = 1;

        final short[] result = ArrayFill.fill(nullArray, fillValue);

        // The same null reference must be returned — no exception, no new array
        assertSame(nullArray, result);
    }
}
