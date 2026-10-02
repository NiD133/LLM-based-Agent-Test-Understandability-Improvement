package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testNewStringIso8859_1 {

    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    private void assertNewStringMatchesJdkDecoding(final String charsetName) throws UnsupportedEncodingException {
        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newString(BYTES_FIXTURE, charsetName);
        assertEquals(expected, actual);
    }

    @Test
    void testNewStringIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();

        assertNewStringMatchesJdkDecoding(charsetName);

        final String expected = new String(BYTES_FIXTURE, charsetName);
        final String actual = StringUtils.newStringIso8859_1(BYTES_FIXTURE);
        assertEquals(expected, actual);
    }
}
