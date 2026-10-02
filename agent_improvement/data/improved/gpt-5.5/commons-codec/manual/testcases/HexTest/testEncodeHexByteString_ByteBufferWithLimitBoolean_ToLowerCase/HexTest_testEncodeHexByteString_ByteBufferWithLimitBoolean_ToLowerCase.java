package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferWithLimitBoolean_ToLowerCase {

    /**
     * Allocate a ByteBuffer.
     *
     * <p>The default implementation uses {@link ByteBuffer#allocate(int)}.
     * The method is overridden in AllocateDirectHexTest to use
     * {@link ByteBuffer#allocateDirect(int)}
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferWithLimitBoolean_ToLowerCase() {
        final ByteBuffer bb = allocate(4);

        // Only the byte between position 1 and limit 2 is visible to Hex.
        bb.put(1, (byte) 10);
        bb.position(1);
        bb.limit(2);

        assertEquals("0a", Hex.encodeHexString(bb, true));
        assertEquals(0, bb.remaining());
    }
}
