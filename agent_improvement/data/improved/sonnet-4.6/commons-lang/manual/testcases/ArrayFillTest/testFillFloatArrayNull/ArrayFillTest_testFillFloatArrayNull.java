package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillFloatArrayNull extends AbstractLangTest {

    /**
     * When the input array is null, ArrayFill.fill should return null
     * (i.e. the same null reference) rather than throwing a NullPointerException.
     */
    @Test
    void testFillFloatArrayNull() {
        final float[] nullArray = null;
        final float fillValue = 1f;

        final float[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "fill() on a null array should return the same null reference");
    }
}
