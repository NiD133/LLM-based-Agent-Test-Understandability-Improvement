package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayHelloWorldUpperCaseHex {

    @Test
    void testEncodeHexByteArrayHelloWorldUpperCaseHex() {
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8("Hello World");
        final String expectedUpperCaseHex = "48656C6C6F20576F726C64";

        char[] actualHex = Hex.encodeHex(helloWorldBytes);
        assertNotEquals(expectedUpperCaseHex, new String(actualHex));

        actualHex = Hex.encodeHex(helloWorldBytes, true);
        assertNotEquals(expectedUpperCaseHex, new String(actualHex));

        actualHex = Hex.encodeHex(helloWorldBytes, false);
        assertEquals(expectedUpperCaseHex, new String(actualHex));
    }
}
