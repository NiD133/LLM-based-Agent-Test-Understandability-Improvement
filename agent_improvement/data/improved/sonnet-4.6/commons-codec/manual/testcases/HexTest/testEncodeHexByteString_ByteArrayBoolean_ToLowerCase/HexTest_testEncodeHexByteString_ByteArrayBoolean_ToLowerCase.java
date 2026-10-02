package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteArrayBoolean_ToLowerCase {

    @Test
    void testEncodeHexByteString_ByteArrayBoolean_ToLowerCase() {
        // byte value 10 (0x0A) should encode to "0a" when toLowerCase=true
        byte[] input = new byte[] { 10 };
        String result = Hex.encodeHexString(input, true);
        assertEquals("0a", result);
    }
}
