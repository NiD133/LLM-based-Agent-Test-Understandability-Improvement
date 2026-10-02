package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer#add(byte[], int, int)}.
 */
public class CircularByteBufferTest_testAddValidData {

    /**
     * Adding a full byte array should make the buffer report exactly that
     * many bytes as currently buffered.
     */
    @Test
    void testAddValidData() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] data = { 3, 6, 9 };
        final int offset = 0;
        final int length = data.length;

        buffer.add(data, offset, length);

        assertEquals(length, buffer.getCurrentNumberOfBytes(),
                "Buffer should hold every byte that was added");
    }
}
