package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillFloatArray extends AbstractLangTest {

    private static final int ARRAY_LENGTH = 3;
    private static final float FILL_VALUE = 1;

    @Test
    void testFillFloatArray() {
        final float[] arrayToFill = new float[ARRAY_LENGTH];

        final float[] filledArray = ArrayFill.fill(arrayToFill, FILL_VALUE);

        assertSame(arrayToFill, filledArray);
        for (final float filledValue : filledArray) {
            assertEquals(FILL_VALUE, filledValue);
        }
    }
}
