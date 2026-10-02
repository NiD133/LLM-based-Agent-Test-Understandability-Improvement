package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillIntArray extends AbstractLangTest {

    private static final int ARRAY_LENGTH = 3;
    private static final int FILL_VALUE = 1;

    @Test
    void testFillIntArray() {
        final int[] array = new int[ARRAY_LENGTH];
        final int val = FILL_VALUE;

        final int[] actual = ArrayFill.fill(array, val);

        assertSame(array, actual);
        for (final int v : actual) {
            assertEquals(val, v);
        }
    }
}
