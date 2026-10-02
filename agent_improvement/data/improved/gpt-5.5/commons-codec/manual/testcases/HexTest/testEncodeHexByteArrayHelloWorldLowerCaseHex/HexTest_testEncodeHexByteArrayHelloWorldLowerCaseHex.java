package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayHelloWorldLowerCaseHex {

    private static final String HELLO_WORLD = "Hello World";
    private static final String HELLO_WORLD_LOWER_CASE_HEX = "48656c6c6f20576f726c64";

    @Test
    void testEncodeHexByteArrayHelloWorldLowerCaseHex() {
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8(HELLO_WORLD);

        final char[] defaultCaseHex = Hex.encodeHex(helloWorldBytes);
        assertEquals(HELLO_WORLD_LOWER_CASE_HEX, new String(defaultCaseHex));

        final char[] lowerCaseHex = Hex.encodeHex(helloWorldBytes, true);
        assertEquals(HELLO_WORLD_LOWER_CASE_HEX, new String(lowerCaseHex));

        final char[] upperCaseHex = Hex.encodeHex(helloWorldBytes, false);
        assertNotEquals(HELLO_WORLD_LOWER_CASE_HEX, new String(upperCaseHex));
    }
}
