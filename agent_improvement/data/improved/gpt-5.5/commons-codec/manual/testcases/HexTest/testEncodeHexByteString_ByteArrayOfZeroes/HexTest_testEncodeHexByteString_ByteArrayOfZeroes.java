package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteArrayOfZeroes {

    private static final int BYTE_COUNT = 36;
    private static final String EXPECTED_HEX_FOR_ZERO_BYTES =
            "000000000000000000000000000000000000000000000000000000000000000000000000";

    @Test
    void testEncodeHexByteString_ByteArrayOfZeroes() {
        final String c = Hex.encodeHexString(new byte[BYTE_COUNT]);
        assertEquals(EXPECTED_HEX_FOR_ZERO_BYTES, c);
    }
}
