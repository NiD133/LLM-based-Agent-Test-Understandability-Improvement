package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf16Be {

    private static final String STRING_FIXTURE = "ABC";
    private static final String UTF_16BE_CHARSET_NAME = StandardCharsets.UTF_16BE.name();

    private void assertGetBytesUncheckedMatchesJdkEncoding(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        assertGetBytesUncheckedMatchesJdkEncoding(UTF_16BE_CHARSET_NAME);

        final byte[] expectedUtf16BeBytes = STRING_FIXTURE.getBytes(UTF_16BE_CHARSET_NAME);
        final byte[] actualUtf16BeBytes = StringUtils.getBytesUtf16Be(STRING_FIXTURE);

        assertArrayEquals(expectedUtf16BeBytes, actualUtf16BeBytes);
    }
}
