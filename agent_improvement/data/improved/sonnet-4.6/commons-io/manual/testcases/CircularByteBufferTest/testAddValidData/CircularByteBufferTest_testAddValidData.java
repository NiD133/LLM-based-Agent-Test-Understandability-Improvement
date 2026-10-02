package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddValidData {

    /**
     * Verifies that adding a byte array into the buffer via add(byte[], offset, length)
     * increases the buffer's byte count by exactly the number of bytes added.
     */
    @Test
    void testAddValidData() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] bytesToAdd = { 3, 6, 9 };
        final int offset = 0;
        final int byteCount = bytesToAdd.length;

        buffer.add(bytesToAdd, offset, byteCount);

        assertEquals(byteCount, buffer.getCurrentNumberOfBytes(),
                "Buffer should contain exactly the number of bytes that were added");
    }
}
