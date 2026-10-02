package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CircularByteBuffer#clear()} discards all buffered bytes
 * and restores the buffer to its initial, fully-empty state.
 */
public class CircularByteBufferTest_testClear {

    /** Total capacity of the buffer used in this test. */
    private static final int BUFFER_CAPACITY = 10;

    /** Sample payload written into the buffer before clearing it. */
    private static final byte[] PAYLOAD = { 1, 2, 3 };

    @Test
    void testClear() {
        final CircularByteBuffer buffer = new CircularByteBuffer(BUFFER_CAPACITY);

        // A freshly created buffer holds no bytes.
        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertFalse(buffer.hasBytes());

        // After adding the payload, the byte count and free space reflect the write.
        buffer.add(PAYLOAD, 0, PAYLOAD.length);
        assertEquals(PAYLOAD.length, buffer.getCurrentNumberOfBytes());
        assertEquals(BUFFER_CAPACITY - PAYLOAD.length, buffer.getSpace());
        assertTrue(buffer.hasBytes());
        assertTrue(buffer.hasSpace());

        // clear() must empty the buffer and free the full capacity again.
        buffer.clear();
        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertEquals(BUFFER_CAPACITY, buffer.getSpace());
        assertFalse(buffer.hasBytes());
        assertTrue(buffer.hasSpace());
    }
}
