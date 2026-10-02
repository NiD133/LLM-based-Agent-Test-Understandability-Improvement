package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillDoubleArray extends AbstractLangTest {

    private static final int ARRAY_LENGTH = 3;
    private static final double FILL_VALUE = 1;

    @Test
    void testFillDoubleArray() {
        final double[] array = new double[ARRAY_LENGTH];

        final double[] filledArray = ArrayFill.fill(array, FILL_VALUE);

        assertSame(array, filledArray);
        for (final double element : filledArray) {
            assertEquals(FILL_VALUE, element);
        }
    }
}
