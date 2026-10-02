package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteBufferEmpty() {
        // An empty ByteBuffer should produce an empty char array from the static encodeHex method
        ByteBuffer emptyBuffer = allocate(0);
        char[] hexChars = Hex.encodeHex(emptyBuffer);
        assertArrayEquals(new char[0], hexChars);

        // An empty ByteBuffer should produce an empty byte array from the instance encode method
        ByteBuffer emptyBufferForEncode = allocate(0);
        byte[] encodedBytes = new Hex().encode(emptyBufferForEncode);
        assertArrayEquals(new byte[0], encodedBytes);
    }
}
