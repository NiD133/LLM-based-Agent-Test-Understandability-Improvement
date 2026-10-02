package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillDoubleArrayNull extends AbstractLangTest {

    /**
     * Verifies that filling a null double array is a no-op:
     * the method must return the same null reference it received,
     * leaving the caller's variable unchanged.
     */
    @Test
    @DisplayName("fill(null double[], value) returns the same null reference without throwing")
    void testFillDoubleArrayNull() {
        final double[] nullArray = null;
        final double fillValue = 1.0;

        final double[] result = ArrayFill.fill(nullArray, fillValue);

        // The contract: a null input is returned as-is (identity, not just equality)
        assertSame(nullArray, result);
    }
}
