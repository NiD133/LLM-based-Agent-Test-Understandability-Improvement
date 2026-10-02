package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(byte[], byte)} when the input array is {@code null}.
 */
public class ArrayFillTest_testFillByteArrayNull extends AbstractLangTest {

    /**
     * Filling a {@code null} byte array should be a no-op: the method must simply
     * return the same {@code null} reference it was given, without throwing.
     */
    @Test
    void testFillByteArrayNull() {
        final byte[] nullArray = null;
        final byte fillValue = 1;

        final byte[] result = ArrayFill.fill(nullArray, fillValue);

        assertSame(nullArray, result, "Filling a null array should return the same null reference");
    }
}
