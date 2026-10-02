package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#clear(byte[])} when the input array is {@code null}.
 */
public class ArrayFillTest_testClearByteArrayNull extends AbstractLangTest {

    /**
     * Clearing a {@code null} byte array should be a no-op that simply returns
     * the same {@code null} reference back to the caller.
     */
    @Test
    void testClearByteArrayNull() {
        final byte[] nullArray = null;

        final byte[] result = ArrayFill.clear(nullArray);

        assertSame(nullArray, result, "clear(null) should return the same null reference");
    }
}
