package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ArrayFillTest_testClearByteArray extends AbstractLangTest {

    @Test
    void testClearByteArray() {
        final byte[] arrayToClear = new byte[3];
        final byte expectedClearedValue = 0;

        final byte[] clearedArray = ArrayFill.clear(arrayToClear);

        assertSame(arrayToClear, clearedArray);
        for (final byte actualValue : clearedArray) {
            assertEquals(expectedClearedValue, actualValue);
        }
    }
}
