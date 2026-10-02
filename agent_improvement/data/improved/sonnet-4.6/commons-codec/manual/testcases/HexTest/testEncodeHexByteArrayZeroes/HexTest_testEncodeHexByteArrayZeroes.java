package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayZeroes {

    // Each zero byte encodes to "00", so 36 zero bytes produce 72 zero hex characters.
    private static final int ZERO_BYTE_COUNT = 36;

    @Test
    void testEncodeHexByteArrayZeroes() {
        final char[] encoded = Hex.encodeHex(new byte[ZERO_BYTE_COUNT]);
        String expectedHex = new String(new char[ZERO_BYTE_COUNT * 2]).replace('\0', '0');
        assertEquals(expectedHex, new String(encoded));
    }
}
