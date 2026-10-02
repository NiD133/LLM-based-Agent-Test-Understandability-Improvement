package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringUtf16Le {

    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };

    private static final byte[] UTF_16LE_BYTES = { 'a', 0, 'b', 0, 'c', 0 };

    private void assertNewStringMatchesJdkDecoding(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(ASCII_BYTES, charsetName);
        final String actual = StringUtils.newString(ASCII_BYTES, charsetName);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringUtf16Le() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16LE.name();

        assertNewStringMatchesJdkDecoding(charsetName);

        final String expected = new String(UTF_16LE_BYTES, charsetName);
        final String actual = StringUtils.newStringUtf16Le(UTF_16LE_BYTES);
        assertEquals(expected, actual);
    }
}
