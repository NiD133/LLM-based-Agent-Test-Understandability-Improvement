package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferWithLimitBoolean_ToUpperCase {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferWithLimitBoolean_ToUpperCase() {
        final ByteBuffer buffer = allocate(4);
        buffer.put(1, (byte) 10);
        buffer.position(1);
        buffer.limit(2);

        assertEquals("0A", Hex.encodeHexString(buffer, false));
        assertEquals(0, buffer.remaining());
    }
}
