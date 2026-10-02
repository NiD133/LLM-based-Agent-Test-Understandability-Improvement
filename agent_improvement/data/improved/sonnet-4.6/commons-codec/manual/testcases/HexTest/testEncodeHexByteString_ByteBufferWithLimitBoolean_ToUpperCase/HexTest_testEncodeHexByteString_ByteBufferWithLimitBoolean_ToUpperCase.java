package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferWithLimitBoolean_ToUpperCase {

    // false = uppercase output (toLowerCase parameter is false)
    private static final boolean UPPERCASE = false;

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Verifies that encodeHexString(ByteBuffer, toLowerCase=false) produces uppercase hex
     * and fully consumes the bytes between the buffer's position and limit.
     *
     * Buffer layout: capacity=4, byte 0x0A placed at index 1.
     * After position(1)/limit(2), exactly one byte (0x0A) is "remaining".
     * Expected encoded result: "0A" (uppercase).
     */
    @Test
    void testEncodeHexByteString_ByteBufferWithLimitBoolean_ToUpperCase() {
        final ByteBuffer bb = allocate(4);
        bb.put(1, (byte) 10);   // 10 decimal == 0x0A
        bb.position(1);
        bb.limit(2);

        assertEquals("0A", Hex.encodeHexString(bb, UPPERCASE));
        assertEquals(0, bb.remaining());
    }
}
