package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferWithLimitBoolean_ToLowerCase {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferWithLimitBoolean_ToLowerCase() {
        // Create a 4-byte buffer and write 0x0A (decimal 10) at absolute index 1.
        // Then narrow the active window to [position=1, limit=2] so that only
        // the single byte at index 1 is "remaining" and visible to the encoder.
        final ByteBuffer bb = allocate(4);
        bb.put(1, (byte) 10);
        bb.position(1);
        bb.limit(2);

        // Encode the single remaining byte as lowercase hex; 0x0A → "0a".
        // The boolean flag `true` requests lowercase output.
        assertEquals("0a", Hex.encodeHexString(bb, true));

        // encodeHexString consumes all remaining bytes, so the buffer is now exhausted.
        assertEquals(0, bb.remaining());
    }
}
