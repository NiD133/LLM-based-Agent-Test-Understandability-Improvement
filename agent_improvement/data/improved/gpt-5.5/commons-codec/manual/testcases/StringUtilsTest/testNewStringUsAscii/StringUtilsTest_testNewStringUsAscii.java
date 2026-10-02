package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUsAscii {

    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };

    private void assertNewStringMatchesJdkConstructor(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(ASCII_BYTES, charsetName);
        final String actual = StringUtils.newString(ASCII_BYTES, charsetName);

        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();

        assertNewStringMatchesJdkConstructor(charsetName);

        final String expected = new String(ASCII_BYTES, charsetName);
        final String actual = StringUtils.newStringUsAscii(ASCII_BYTES);

        assertEquals(expected, actual);
    }
}
