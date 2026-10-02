package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        final ByteBuffer buffer = allocate(1);
        buffer.put((byte) 10);
        buffer.flip();

        assertEquals("0A", Hex.encodeHexString(buffer, false));
    }
}
