package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer#peek(byte[], int, int)}.
 */
public class CircularByteBufferTest_testPeekWithValidArguments {

    @Test
    void testPeekWithValidArguments() {
        // peek() compares the buffer's pending bytes against the given segment.
        // An empty buffer cannot match the requested 5 bytes, so peek() returns false.
        final CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        final byte[] expectedBytes = { 5, 10, 15, 20, 25 };

        final boolean matchesPendingBytes = emptyBuffer.peek(expectedBytes, 0, expectedBytes.length);

        assertFalse(matchesPendingBytes);
    }
}
