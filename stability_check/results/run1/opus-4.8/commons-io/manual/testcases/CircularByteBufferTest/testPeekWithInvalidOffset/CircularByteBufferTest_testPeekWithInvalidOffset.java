package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CircularByteBuffer#peek(byte[], int, int)} rejects a
 * negative source offset by throwing an {@link IllegalArgumentException}.
 */
public class CircularByteBufferTest_testPeekWithInvalidOffset {

    @Test
    void testPeekWithInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final byte[] sourceBytes = { 2, 4, 6, 8, 10 };
        final int negativeOffset = -1;
        final int length = 5;

        // A negative offset is illegal and must be rejected before any comparison.
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(sourceBytes, negativeOffset, length));

        assertEquals("Illegal offset: -1", thrown.getMessage());
    }
}
