package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        // Byte value 0x0A (decimal 10) should encode to "0A" in uppercase hex
        final boolean toUpperCase = false; // false means uppercase in Hex.encodeHexString
        final ByteBuffer input = ByteBuffer.allocate(1);
        input.put((byte) 0x0A);
        input.flip();

        final String result = Hex.encodeHexString(input, toUpperCase);

        assertEquals("0A", result, "ByteBuffer with byte 0x0A should encode to uppercase hex '0A'");
    }
}
