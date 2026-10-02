package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer#peek(byte[], int, int)} when the requested
 * length exceeds what the buffer can match.
 */
public class CircularByteBufferTest_testPeekWithExcessiveLength {

    /**
     * Peeking into a freshly created, empty buffer must report no match: the
     * empty buffer holds no bytes that can equal the expected sequence, so
     * {@code peek} returns {@code false}.
     */
    @Test
    void testPeekWithExcessiveLength() {
        final CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        final byte[] expectedBytes = { 1, 3, 5, 7, 9 };

        // Compare 6 bytes starting at offset 0 against the empty buffer.
        final boolean matches = emptyBuffer.peek(expectedBytes, 0, 6);

        assertFalse(matches, "peek on an empty buffer should not match");
    }
}
