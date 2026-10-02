package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHex} produces lower-case hexadecimal output by default
 * (and when {@code toLowerCase} is explicitly {@code true}), and upper-case output otherwise.
 */
public class HexTest_testEncodeHexByteArrayHelloWorldLowerCaseHex {

    @Test
    void testEncodeHexByteArrayHelloWorldLowerCaseHex() {
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8("Hello World");
        final String expectedLowerCaseHex = "48656c6c6f20576f726c64";

        // Default encoding is lower case.
        final char[] defaultHex = Hex.encodeHex(helloWorldBytes);
        assertEquals(expectedLowerCaseHex, new String(defaultHex));

        // Explicitly requesting lower case matches the default.
        final char[] lowerCaseHex = Hex.encodeHex(helloWorldBytes, true);
        assertEquals(expectedLowerCaseHex, new String(lowerCaseHex));

        // Upper-case encoding must differ from the lower-case expectation.
        final char[] upperCaseHex = Hex.encodeHex(helloWorldBytes, false);
        assertNotEquals(expectedLowerCaseHex, new String(upperCaseHex));
    }
}
