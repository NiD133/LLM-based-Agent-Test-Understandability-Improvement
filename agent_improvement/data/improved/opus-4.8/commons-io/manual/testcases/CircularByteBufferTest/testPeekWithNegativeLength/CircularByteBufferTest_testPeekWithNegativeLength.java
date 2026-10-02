package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CircularByteBuffer#peek(byte[], int, int)} rejects a
 * negative {@code length} argument by throwing an {@link IllegalArgumentException}.
 */
public class CircularByteBufferTest_testPeekWithNegativeLength {

    @Test
    void testPeekWithNegativeLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] sourceBuffer = { 1, 4, 3 };
        final int offset = 0;
        final int negativeLength = -1;

        // peek() must reject a negative length before reading any bytes.
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(sourceBuffer, offset, negativeLength));

        assertEquals("Illegal length: -1", thrown.getMessage());
    }
}
