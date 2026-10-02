package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHex_ByteBufferOfZeroes {

    /**
     * Allocate a ByteBuffer.
     *
     * <p>The default implementation uses {@link ByteBuffer#allocate(int)}.
     * The method is overridden in AllocateDirectHexTest to use
     * {@link ByteBuffer#allocateDirect(int)}.
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHex_ByteBufferOfZeroes() {
        final char[] encodedZeroBytes = Hex.encodeHex(allocate(36));

        assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000", new String(encodedZeroBytes));
    }
}
