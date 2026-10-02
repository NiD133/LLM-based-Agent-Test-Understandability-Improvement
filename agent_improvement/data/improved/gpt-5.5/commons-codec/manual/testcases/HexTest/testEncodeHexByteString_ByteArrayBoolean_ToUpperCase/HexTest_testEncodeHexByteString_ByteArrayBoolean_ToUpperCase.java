package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteArrayBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteArrayBoolean_ToUpperCase() {
        final byte[] singleLineFeedByte = { 10 };
        final boolean toLowerCase = false;

        assertEquals("0A", Hex.encodeHexString(singleLineFeedByte, toLowerCase));
    }
}
