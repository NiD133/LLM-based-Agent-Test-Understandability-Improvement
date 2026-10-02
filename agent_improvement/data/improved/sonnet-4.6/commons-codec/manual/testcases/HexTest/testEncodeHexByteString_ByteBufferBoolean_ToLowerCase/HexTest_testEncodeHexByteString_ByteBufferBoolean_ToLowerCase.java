package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToLowerCase {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToLowerCase() {
        // Byte value 10 (0x0A) should encode to lowercase hex string "0a"
        final ByteBuffer bb = allocate(1);
        bb.put((byte) 10);
        bb.flip();
        assertEquals("0a", Hex.encodeHexString(bb, true));
    }
}
