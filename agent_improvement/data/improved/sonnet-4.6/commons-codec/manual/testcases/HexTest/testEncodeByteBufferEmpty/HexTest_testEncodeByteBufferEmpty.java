package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteBufferEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeByteBufferEmpty() {
        // Encoding an empty ByteBuffer should produce an empty byte array (no hex digits).
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}
