package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteArrayBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteArrayBoolean_ToUpperCase() {
        // Byte value 10 (decimal) = 0x0A; passing false requests uppercase hex output.
        byte[] inputBytes = new byte[] { 10 };
        boolean toUpperCase = false; // false means uppercase in encodeHexString API

        String hexString = Hex.encodeHexString(inputBytes, toUpperCase);

        assertEquals("0A", hexString);
    }
}
