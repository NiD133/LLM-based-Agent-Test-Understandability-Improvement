package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayZeroes {

    private static final int ZERO_BYTE_COUNT = 36;
    private static final String HEX_FOR_THIRTY_SIX_ZERO_BYTES =
            "000000000000000000000000000000000000000000000000000000000000000000000000";

    @Test
    void testEncodeHexByteArrayZeroes() {
        final byte[] zeroBytes = new byte[ZERO_BYTE_COUNT];
        final char[] encodedHex = Hex.encodeHex(zeroBytes);

        assertEquals(HEX_FOR_THIRTY_SIX_ZERO_BYTES, new String(encodedHex));
    }
}
