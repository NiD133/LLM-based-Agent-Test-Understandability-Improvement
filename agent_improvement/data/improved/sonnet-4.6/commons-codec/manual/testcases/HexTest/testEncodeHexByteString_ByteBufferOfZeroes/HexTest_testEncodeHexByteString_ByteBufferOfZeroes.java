package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferOfZeroes {

    // A freshly allocated ByteBuffer is zero-initialized.
    // 36 zero bytes × 2 hex chars per byte = 72 '0' characters in the expected output.
    private static final int ZERO_BYTE_COUNT = 36;
    private static final String EXPECTED_HEX_OF_ZEROES =
            "000000000000000000000000000000000000000000000000000000000000000000000000";

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferOfZeroes() {
        final ByteBuffer zeroBuffer = allocate(ZERO_BYTE_COUNT);
        final String hexString = Hex.encodeHexString(zeroBuffer);
        assertEquals(EXPECTED_HEX_OF_ZEROES, hexString);
    }
}
