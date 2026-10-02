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
        final char[] expectedHexCharacters = new char[0];
        final byte[] expectedEncodedBytes = new byte[0];

        assertArrayEquals(expectedHexCharacters, Hex.encodeHex(allocate(0)));
        assertArrayEquals(expectedEncodedBytes, new Hex().encode(allocate(0)));
    }
}
