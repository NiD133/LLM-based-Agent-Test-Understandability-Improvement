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
        assertArrayEquals(new char[0], Hex.encodeHex(allocate(0)));
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}
