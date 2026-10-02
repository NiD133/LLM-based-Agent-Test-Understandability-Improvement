package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHex_ByteBufferOfZeroes {

    // Each zero byte encodes to two hex characters ("00"), so the output is twice the buffer length.
    private static final int BUFFER_SIZE_BYTES = 36;
    private static final String EXPECTED_HEX;

    static {
        final StringBuilder sb = new StringBuilder(BUFFER_SIZE_BYTES * 2);
        for (int i = 0; i < BUFFER_SIZE_BYTES; i++) {
            sb.append("00");
        }
        EXPECTED_HEX = sb.toString();
    }

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHex_ByteBufferOfZeroes() {
        final char[] encoded = Hex.encodeHex(allocate(BUFFER_SIZE_BYTES));
        assertEquals(EXPECTED_HEX, new String(encoded));
    }
}
