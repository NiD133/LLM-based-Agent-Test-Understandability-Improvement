package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferOfZeroesWithLimit {

    private static final int BUFFER_CAPACITY = 36;
    private static final int FIRST_LIMIT = 3;
    private static final int SECOND_POSITION = 1;
    private static final String FIRST_THREE_ZERO_BYTES_AS_HEX = "000000";
    private static final String LAST_TWO_ZERO_BYTES_AS_HEX = "0000";

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
    void testEncodeHexByteString_ByteBufferOfZeroesWithLimit() {
        final ByteBuffer bb = allocate(BUFFER_CAPACITY);

        bb.limit(FIRST_LIMIT);
        assertEquals(FIRST_THREE_ZERO_BYTES_AS_HEX, Hex.encodeHexString(bb));
        assertEquals(0, bb.remaining());

        bb.position(SECOND_POSITION);
        bb.limit(FIRST_LIMIT);
        assertEquals(LAST_TWO_ZERO_BYTES_AS_HEX, Hex.encodeHexString(bb));
        assertEquals(0, bb.remaining());
    }
}
