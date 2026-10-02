package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferOfZeroesWithLimit {

    /**
     * Allocates a heap ByteBuffer. Subclasses may override to use direct allocation.
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Verifies that {@link Hex#encodeHexString(ByteBuffer)} respects the buffer's
     * current position and limit, encoding only the bytes in the remaining region.
     *
     * <p>A 36-byte buffer of zeroes is used. Two sub-scenarios are tested:</p>
     * <ol>
     *   <li>Limit set to 3 at position 0 → 3 readable bytes → "000000"</li>
     *   <li>Position advanced to 1, limit reset to 3 → 2 readable bytes → "0000"</li>
     * </ol>
     * <p>After each call the buffer must be fully consumed (remaining == 0).</p>
     */
    @Test
    void testEncodeHexByteString_ByteBufferOfZeroesWithLimit() {
        // Allocate a 36-byte buffer; ByteBuffer initialises all bytes to zero.
        final ByteBuffer bb = allocate(36);

        // Scenario 1: encode bytes [0, 3) — three zero bytes → "000000"
        bb.limit(3);
        assertEquals("000000", Hex.encodeHexString(bb));
        assertEquals(0, bb.remaining(), "Buffer should be fully consumed after encoding");

        // Scenario 2: advance position to 1, reset limit to 3 → encode bytes [1, 3)
        // — two zero bytes → "0000"
        bb.position(1);
        bb.limit(3);
        assertEquals("0000", Hex.encodeHexString(bb));
        assertEquals(0, bb.remaining(), "Buffer should be fully consumed after encoding");
    }
}
