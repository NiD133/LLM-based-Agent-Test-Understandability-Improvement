package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillFloatArrayNull extends AbstractLangTest {

    @Test
    void testFillFloatArrayNull() {
        final float[] nullFloatArray = null;
        final float fillValue = 1;

        final float[] returnedArray = ArrayFill.fill(nullFloatArray, fillValue);

        assertSame(nullFloatArray, returnedArray);
    }
}
