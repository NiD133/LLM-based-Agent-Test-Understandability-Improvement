package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(double[], double)} when the supplied array is {@code null}.
 */
public class ArrayFillTest_testFillDoubleArrayNull extends AbstractLangTest {

    /**
     * When given a {@code null} double array, {@code fill} should simply return that
     * same {@code null} reference instead of attempting to fill anything.
     */
    @Test
    void testFillDoubleArrayNull() {
        final double[] nullArray = null;
        final double fillValue = 1;

        final double[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "Filling a null array should return the same null reference");
    }
}
