package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testClear {

    private static final int BUFFER_CAPACITY = 10;
    private static final byte[] BYTES_TO_ADD = { 1, 2, 3 };

    @Test
    void testClear() {
        final CircularByteBuffer buffer = new CircularByteBuffer(BUFFER_CAPACITY);

        assertEmptyBufferHasNoBytes(buffer);

        buffer.add(BYTES_TO_ADD, 0, BYTES_TO_ADD.length);
        assertBufferState(buffer, BYTES_TO_ADD.length, BUFFER_CAPACITY - BYTES_TO_ADD.length, true, true);

        buffer.clear();
        assertBufferState(buffer, 0, BUFFER_CAPACITY, false, true);
    }

    private static void assertEmptyBufferHasNoBytes(final CircularByteBuffer buffer) {
        assertEquals(0, buffer.getCurrentNumberOfBytes());
        assertFalse(buffer.hasBytes());
    }

    private static void assertBufferState(final CircularByteBuffer buffer, final int expectedByteCount,
            final int expectedSpace, final boolean expectedHasBytes, final boolean expectedHasSpace) {
        assertEquals(expectedByteCount, buffer.getCurrentNumberOfBytes());
        assertEquals(expectedSpace, buffer.getSpace());
        assertEquals(expectedHasBytes, buffer.hasBytes());
        assertEquals(expectedHasSpace, buffer.hasSpace());
    }
}
