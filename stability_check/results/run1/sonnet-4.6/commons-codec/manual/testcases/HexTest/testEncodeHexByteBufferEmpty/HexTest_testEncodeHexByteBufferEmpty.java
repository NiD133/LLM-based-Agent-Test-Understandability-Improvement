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
        ByteBuffer emptyBuffer = allocate(0);

        // Static encodeHex should return an empty char array for an empty buffer
        char[] hexChars = Hex.encodeHex(emptyBuffer);
        assertArrayEquals(new char[0], hexChars);

        // Instance encode should return an empty byte array for an empty buffer
        emptyBuffer = allocate(0);
        byte[] hexBytes = new Hex().encode(emptyBuffer);
        assertArrayEquals(new byte[0], hexBytes);
    }
}
