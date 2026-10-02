package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHex} honours the case selection flag.
 *
 * <p>The bytes of "Hello World" encode to the hex string {@code 48656c6c6f20576f726c64}.
 * The expected value below is the upper-case form, which should only be produced when
 * upper-case output is explicitly requested.</p>
 */
public class HexTest_testEncodeHexByteArrayHelloWorldUpperCaseHex {

    @Test
    void testEncodeHexByteArrayHelloWorldUpperCaseHex() {
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8("Hello World");
        final String expectedUpperCaseHex = "48656C6C6F20576F726C64";

        // Default encoding produces lower-case hex, so it differs from the upper-case expectation.
        final char[] defaultHex = Hex.encodeHex(helloWorldBytes);
        assertNotEquals(expectedUpperCaseHex, new String(defaultHex));

        // toLowerCase = true also produces lower-case hex, which still differs.
        final char[] lowerCaseHex = Hex.encodeHex(helloWorldBytes, true);
        assertNotEquals(expectedUpperCaseHex, new String(lowerCaseHex));

        // toLowerCase = false produces upper-case hex, matching the expectation.
        final char[] upperCaseHex = Hex.encodeHex(helloWorldBytes, false);
        assertEquals(expectedUpperCaseHex, new String(upperCaseHex));
    }
}
