package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testHasSpaceInt {

    @Test
    @DisplayName("hasSpace(int) reflects available capacity as bytes are added and removed from a size-1 buffer")
    void testHasSpaceInt() {
        // A buffer with capacity 1: it can hold exactly one byte at a time.
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        // Initially empty — there is room for one more byte.
        assertTrue(buffer.hasSpace(1));

        // --- First write/read cycle ---
        buffer.add((byte) 1);
        // Buffer is now full; no room for another byte.
        assertFalse(buffer.hasSpace(1));

        // Reading drains the buffer and returns the written value.
        assertEquals(1, buffer.read());
        // Buffer is empty again — space is restored.
        assertTrue(buffer.hasSpace(1));

        // --- Second write/read cycle (confirms state resets correctly) ---
        buffer.add((byte) 2);
        // Buffer is full again after the second write.
        assertFalse(buffer.hasSpace(1));

        // Reading returns the second written value.
        assertEquals(2, buffer.read());
        // Buffer is empty once more — space is available again.
        assertTrue(buffer.hasSpace(1));
    }
}
