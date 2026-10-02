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
        // byte value 10 (0x0A) should encode to uppercase hex string "0A"
        final boolean toUpperCase = false; // false means "not toLowerCase" = uppercase
        final ByteBuffer bb = allocate(1);
        bb.put((byte) 10);
        bb.flip();
        assertEquals("0A", Hex.encodeHexString(bb, toUpperCase));
    }
}
