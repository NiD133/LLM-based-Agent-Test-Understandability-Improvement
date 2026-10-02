package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillShortArray extends AbstractLangTest {

    private static final int ARRAY_LENGTH = 3;
    private static final short FILL_VALUE = 1;

    @Test
    void testFillShortArray() {
        final short[] arrayToFill = new short[ARRAY_LENGTH];

        final short[] filledArray = ArrayFill.fill(arrayToFill, FILL_VALUE);

        assertSame(arrayToFill, filledArray);
        for (final short actualValue : filledArray) {
            assertEquals(FILL_VALUE, actualValue);
        }
    }
}
