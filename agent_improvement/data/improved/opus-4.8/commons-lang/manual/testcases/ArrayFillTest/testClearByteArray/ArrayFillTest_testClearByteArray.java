package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#clear(byte[])}, which resets every element of a
 * {@code byte} array to {@code 0} and returns the same array instance.
 */
public class ArrayFillTest_testClearByteArray extends AbstractLangTest {

    @Test
    void testClearByteArray() {
        final byte[] arrayToClear = new byte[3];
        final byte expectedClearedValue = 0;

        final byte[] clearedArray = ArrayFill.clear(arrayToClear);

        // clear() fills the array in place and returns the very same instance.
        assertSame(arrayToClear, clearedArray);
        // Every element must have been reset to 0.
        for (final byte actualValue : clearedArray) {
            assertEquals(expectedClearedValue, actualValue);
        }
    }
}
