package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillLongArray extends AbstractLangTest {

    @Test
    void testFillLongArray() {
        final int arrayLength = 3;
        final long fillValue = 1;
        final long[] arrayToFill = new long[arrayLength];

        final long[] filledArray = ArrayFill.fill(arrayToFill, fillValue);

        assertSame(arrayToFill, filledArray);
        for (final long value : filledArray) {
            assertEquals(fillValue, value);
        }
    }
}
