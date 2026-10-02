package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf16Be {

    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };

    private static final byte[] UTF_16BE_BYTES = { 0, 'a', 0, 'b', 0, 'c' };

    @Test
    void testNewStringUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();

        assertNewStringMatchesJdkStringConstructor(charsetName);

        final String expected = new String(UTF_16BE_BYTES, charsetName);
        final String actual = StringUtils.newStringUtf16Be(UTF_16BE_BYTES);
        assertEquals(expected, actual);
    }

    private void assertNewStringMatchesJdkStringConstructor(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(ASCII_BYTES, charsetName);
        final String actual = StringUtils.newString(ASCII_BYTES, charsetName);
        assertEquals(expected, actual);
    }
}
