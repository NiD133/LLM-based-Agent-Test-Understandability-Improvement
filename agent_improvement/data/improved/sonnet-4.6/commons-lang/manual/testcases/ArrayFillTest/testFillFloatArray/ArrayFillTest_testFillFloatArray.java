package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillFloatArray extends AbstractLangTest {

    @Test
    void testFillFloatArray() {
        final float[] array = new float[3];
        final float fillValue = 1f;

        final float[] result = ArrayFill.fill(array, fillValue);

        assertSame(array, result);
        assertArrayEquals(new float[]{fillValue, fillValue, fillValue}, result);
    }
}
