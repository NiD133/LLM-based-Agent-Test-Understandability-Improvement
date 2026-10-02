package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayHelloWorldUpperCaseHex {

    @Test
    void testEncodeHexByteArrayHelloWorldUpperCaseHex() {
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8("Hello World");

        // The uppercase hex representation of "Hello World"
        final String expectedUpperCaseHex = "48656C6C6F20576F726C64";

        // encodeHex(byte[]) defaults to lowercase — must not match the uppercase expected value
        char[] actual = Hex.encodeHex(helloWorldBytes);
        assertNotEquals(expectedUpperCaseHex, new String(actual));

        // encodeHex(byte[], true) explicitly requests lowercase — must not match the uppercase expected value
        actual = Hex.encodeHex(helloWorldBytes, true);
        assertNotEquals(expectedUpperCaseHex, new String(actual));

        // encodeHex(byte[], false) requests uppercase — must match the uppercase expected value
        actual = Hex.encodeHex(helloWorldBytes, false);
        assertEquals(expectedUpperCaseHex, new String(actual));
    }
}
