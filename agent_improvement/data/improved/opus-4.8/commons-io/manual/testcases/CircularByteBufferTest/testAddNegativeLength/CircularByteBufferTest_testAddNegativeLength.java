package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CircularByteBuffer#add(byte[], int, int)} rejects a negative
 * {@code length} argument by throwing an {@link IllegalArgumentException}.
 */
public class CircularByteBufferTest_testAddNegativeLength {

    @Test
    void testAddNegativeLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] sourceBytes = { 1, 2, 3 };

        final int validOffset = 0;
        final int negativeLength = -1;

        // A negative length is illegal and must be rejected.
        assertThrows(IllegalArgumentException.class,
                () -> buffer.add(sourceBytes, validOffset, negativeLength));
    }
}
