package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(float[], float)} when the input array is {@code null}.
 */
public class ArrayFillTest_testFillFloatArrayNull extends AbstractLangTest {

    /**
     * Calling {@code fill} on a {@code null} float array is a no-op: it must return
     * the same {@code null} reference that was passed in, without throwing.
     */
    @Test
    void testFillFloatArrayNull() {
        final float[] nullArray = null;
        final float fillValue = 1;

        final float[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result);
    }
}
