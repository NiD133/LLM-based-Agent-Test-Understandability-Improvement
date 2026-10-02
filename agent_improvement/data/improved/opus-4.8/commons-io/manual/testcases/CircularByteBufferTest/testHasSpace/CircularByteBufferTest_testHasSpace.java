package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer#hasSpace()} on a buffer whose capacity is a
 * single byte, so that the buffer toggles between "empty" and "full" after
 * every add or read.
 */
public class CircularByteBufferTest_testHasSpace {

    /** Capacity that holds exactly one byte, making space exhaustion easy to observe. */
    private static final int SINGLE_BYTE_CAPACITY = 1;

    @Test
    void testHasSpace() {
        final CircularByteBuffer buffer = new CircularByteBuffer(SINGLE_BYTE_CAPACITY);

        // A freshly created buffer is empty, so it has room for a byte.
        assertTrue(buffer.hasSpace(), "Empty buffer should have space");

        // Adding a byte fills the single slot, leaving no space.
        buffer.add((byte) 1);
        assertFalse(buffer.hasSpace(), "Full buffer should report no space");

        // Reading the byte back frees the slot again.
        assertEquals(1, buffer.read());
        assertTrue(buffer.hasSpace(), "Buffer should have space after the byte is read");

        // The same fill-then-drain cycle works a second time, confirming the slot is reusable.
        buffer.add((byte) 2);
        assertFalse(buffer.hasSpace(), "Full buffer should report no space");
        assertEquals(2, buffer.read());
        assertTrue(buffer.hasSpace(), "Buffer should have space after the byte is read");
    }
}
