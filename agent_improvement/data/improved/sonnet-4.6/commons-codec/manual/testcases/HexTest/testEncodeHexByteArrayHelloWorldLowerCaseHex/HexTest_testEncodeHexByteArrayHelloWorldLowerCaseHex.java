package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayHelloWorldLowerCaseHex {

    @Test
    void testEncodeHexByteArrayHelloWorldLowerCaseHex() {
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8("Hello World");
        final String expectedLowerCaseHex = "48656c6c6f20576f726c64";

        // Default encodeHex produces lowercase hex
        final char[] defaultResult = Hex.encodeHex(helloWorldBytes);
        assertEquals(expectedLowerCaseHex, new String(defaultResult));

        // Explicit toLowerCase=true also produces lowercase hex
        final char[] lowerCaseResult = Hex.encodeHex(helloWorldBytes, true);
        assertEquals(expectedLowerCaseHex, new String(lowerCaseResult));

        // toLowerCase=false produces uppercase hex, which differs from the lowercase expected value
        final char[] upperCaseResult = Hex.encodeHex(helloWorldBytes, false);
        assertNotEquals(expectedLowerCaseHex, new String(upperCaseResult));
    }
}
