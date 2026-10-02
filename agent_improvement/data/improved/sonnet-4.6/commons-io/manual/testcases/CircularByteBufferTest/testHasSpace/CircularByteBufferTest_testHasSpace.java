package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testHasSpace {

    /**
     * A capacity-1 buffer makes it easy to reason about hasSpace(): after every add()
     * the buffer must be full, and after every read() it must be empty again.
     */
    @Test
    @DisplayName("hasSpace() toggles correctly as the single-slot buffer fills and drains")
    void testHasSpace() {
        final CircularByteBuffer cbb = new CircularByteBuffer(1);

        // Newly created buffer has one free slot
        assertTrue(cbb.hasSpace());

        // First write/read cycle
        cbb.add((byte) 1);
        assertFalse(cbb.hasSpace()); // slot is now occupied
        assertEquals(1, cbb.read()); // byte 1 is returned and the slot is freed
        assertTrue(cbb.hasSpace());  // back to one free slot

        // Second write/read cycle — confirms hasSpace() keeps tracking correctly
        cbb.add((byte) 2);
        assertFalse(cbb.hasSpace()); // slot occupied again
        assertEquals(2, cbb.read()); // byte 2 is returned and slot is freed
        assertTrue(cbb.hasSpace());
    }
}
