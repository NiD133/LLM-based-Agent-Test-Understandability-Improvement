package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        // Byte 0x0A encoded in uppercase hex should be "0A"
        final boolean toLowerCase = false;
        final ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) 0x0A);
        buffer.flip();

        assertEquals("0A", Hex.encodeHexString(buffer, toLowerCase));
    }
}
