package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf16 {

    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };

    private void assertNewStringMatchesJdkDecoding(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(ASCII_BYTES, charsetName);
        final String actual = StringUtils.newString(ASCII_BYTES, charsetName);

        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16() throws UnsupportedEncodingException {
        final String utf16CharsetName = StandardCharsets.UTF_16.name();

        assertNewStringMatchesJdkDecoding(utf16CharsetName);

        final String expected = new String(ASCII_BYTES, utf16CharsetName);
        final String actual = StringUtils.newStringUtf16(ASCII_BYTES);

        assertEquals(expected, actual);
    }
}
