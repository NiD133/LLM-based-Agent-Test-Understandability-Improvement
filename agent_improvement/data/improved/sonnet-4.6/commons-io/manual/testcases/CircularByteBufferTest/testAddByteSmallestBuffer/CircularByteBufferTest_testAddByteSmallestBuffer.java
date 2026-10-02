package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddByteSmallestBuffer {

    /**
     * Verifies that a size-1 CircularByteBuffer correctly cycles through writes and reads:
     * adding a byte and then reading it should return the same byte, and the buffer
     * should be reusable immediately after reading (since capacity is freed).
     */
    @Test
    void testAddByteSmallestBuffer() {
        // A buffer of capacity 1 is the smallest valid CircularByteBuffer.
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        // First write/read cycle: add byte value 1, expect to read it back.
        buffer.add((byte) 1);
        assertEquals(1, buffer.read(), "Reading after first add should return the byte that was added");

        // Second write/read cycle: after reading, the single slot is free again,
        // so adding a different byte value (2) and reading should return that new value.
        buffer.add((byte) 2);
        assertEquals(2, buffer.read(), "Reading after second add should return the newly added byte");
    }
}
