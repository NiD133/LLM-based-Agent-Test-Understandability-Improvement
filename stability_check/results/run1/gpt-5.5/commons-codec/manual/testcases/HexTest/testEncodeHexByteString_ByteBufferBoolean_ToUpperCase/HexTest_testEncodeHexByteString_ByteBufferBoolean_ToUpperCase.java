package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    private static final int SINGLE_BYTE_CAPACITY = 1;
    private static final byte VALUE_REQUIRING_HEX_LETTER = 10;
    private static final boolean USE_LOWER_CASE_HEX = false;
    private static final String EXPECTED_UPPER_CASE_HEX = "0A";

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        final ByteBuffer buffer = allocate(SINGLE_BYTE_CAPACITY);
        buffer.put(VALUE_REQUIRING_HEX_LETTER);
        buffer.flip();

        assertEquals(EXPECTED_UPPER_CASE_HEX, Hex.encodeHexString(buffer, USE_LOWER_CASE_HEX));
    }
}
