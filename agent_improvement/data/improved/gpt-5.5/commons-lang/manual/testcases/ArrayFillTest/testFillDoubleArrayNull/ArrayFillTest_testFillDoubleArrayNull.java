package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillDoubleArrayNull extends AbstractLangTest {

    @Test
    void testFillDoubleArrayNull() {
        final double[] nullArray = null;
        final double fillValue = 1;

        final double[] filledArray = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, filledArray);
    }
}
