package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteArrayBoolean_ToLowerCase {

    @Test
    void encodeHexStringUsesLowercaseDigitsWhenRequested() {
        assertEquals("0a", Hex.encodeHexString(new byte[] { 10 }, true));
    }
}
