package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CircularByteBuffer#hasSpace(int)} on a buffer whose total
 * capacity is a single byte.
 *
 * <p>The scenario walks the buffer through a full fill/drain cycle twice to
 * confirm that {@code hasSpace(1)} tracks the remaining capacity correctly:
 * it is {@code true} while the buffer is empty and {@code false} once the
 * single slot is occupied, and reading the byte back frees the slot again.</p>
 */
public class CircularByteBufferTest_testHasSpaceInt {

    /** The buffer under test can hold exactly one byte at a time. */
    private static final int CAPACITY_ONE_BYTE = 1;

    @Test
    void testHasSpaceInt() {
        final CircularByteBuffer buffer = new CircularByteBuffer(CAPACITY_ONE_BYTE);

        // An empty single-byte buffer has room for one more byte.
        assertTrue(buffer.hasSpace(1), "empty buffer should have room for one byte");

        // After adding a byte the only slot is taken, so there is no more room.
        buffer.add((byte) 1);
        assertFalse(buffer.hasSpace(1), "full buffer should report no room");

        // Reading the byte back empties the buffer and frees the slot again.
        assertEquals(1, buffer.read());
        assertTrue(buffer.hasSpace(1), "buffer should have room again after reading");

        // Repeat the fill/drain cycle with a different value to confirm the
        // capacity accounting resets correctly.
        buffer.add((byte) 2);
        assertFalse(buffer.hasSpace(1), "full buffer should report no room");
        assertEquals(2, buffer.read());
        assertTrue(buffer.hasSpace(1), "buffer should have room again after reading");
    }
}
