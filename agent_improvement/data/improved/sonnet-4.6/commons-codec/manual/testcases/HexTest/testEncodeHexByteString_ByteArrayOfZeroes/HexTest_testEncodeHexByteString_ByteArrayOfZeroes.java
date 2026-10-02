package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteArrayOfZeroes {

    // Each zero byte encodes to the two-character string "00", so 36 zero bytes
    // produce exactly 72 '0' characters.
    private static final String HEX_OF_36_ZERO_BYTES =
            "000000000000000000000000000000000000000000000000000000000000000000000000";

    @Test
    void testEncodeHexByteString_ByteArrayOfZeroes() {
        final String actual = Hex.encodeHexString(new byte[36]);
        assertEquals(HEX_OF_36_ZERO_BYTES, actual);
    }
}
