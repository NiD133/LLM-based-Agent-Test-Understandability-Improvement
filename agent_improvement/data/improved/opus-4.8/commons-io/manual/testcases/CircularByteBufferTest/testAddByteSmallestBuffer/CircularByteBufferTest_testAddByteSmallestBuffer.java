package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a single-byte {@link CircularByteBuffer} can be written and
 * read repeatedly. Because the buffer only holds one byte at a time, each
 * {@code add} must be followed by a {@code read} that returns the same value,
 * and the freed slot can then be reused by the next {@code add}.
 */
public class CircularByteBufferTest_testAddByteSmallestBuffer {

    @Test
    void testAddByteSmallestBuffer() {
        // A buffer with capacity for exactly one byte.
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        // First byte: add then read it back; the slot is now free again.
        buffer.add((byte) 1);
        assertEquals(1, buffer.read());

        // Second byte: reusing the same single slot returns the new value.
        buffer.add((byte) 2);
        assertEquals(2, buffer.read());
    }
}
