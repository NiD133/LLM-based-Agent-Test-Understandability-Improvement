package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testClear {

    private static final int BUFFER_CAPACITY = 10;
    private static final byte[] THREE_BYTES = { 1, 2, 3 };

    @Test
    void testClear() {
        final CircularByteBuffer buffer = new CircularByteBuffer(BUFFER_CAPACITY);

        // Newly created buffer should be empty with full space available
        assertEquals(0, buffer.getCurrentNumberOfBytes(), "New buffer should contain zero bytes");
        assertFalse(buffer.hasBytes(), "New buffer should report no bytes present");

        // Add three bytes so the buffer is partially filled
        buffer.add(THREE_BYTES, 0, THREE_BYTES.length);
        assertEquals(3, buffer.getCurrentNumberOfBytes(), "Buffer should hold the 3 added bytes");
        assertEquals(7, buffer.getSpace(), "Remaining space should be capacity minus bytes added (10 - 3 = 7)");
        assertTrue(buffer.hasBytes(), "Buffer should report bytes present after add");
        assertTrue(buffer.hasSpace(), "Buffer should report space available after partial fill");

        // clear() should discard all bytes and restore the full capacity
        buffer.clear();
        assertEquals(0, buffer.getCurrentNumberOfBytes(), "Buffer should contain zero bytes after clear");
        assertEquals(BUFFER_CAPACITY, buffer.getSpace(), "All capacity should be available after clear");
        assertFalse(buffer.hasBytes(), "Buffer should report no bytes present after clear");
        assertTrue(buffer.hasSpace(), "Buffer should report space available after clear");
    }
}
