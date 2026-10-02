package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddValidData {

    /**
     * Verifies that adding a valid byte range increases the buffer's byte count.
     */
    @Test
    void testAddValidData() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] validBytes = { 3, 6, 9 };
        final int startOffset = 0;
        final int byteCount = 3;

        buffer.add(validBytes, startOffset, byteCount);

        assertEquals(byteCount, buffer.getCurrentNumberOfBytes());
    }
}
