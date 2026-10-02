package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf16 {

    private static final String STRING_FIXTURE = "ABC";

    private void assertGetBytesUncheckedMatchesJdkEncoding(final String charsetName)
            throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16() throws UnsupportedEncodingException {
        final String utf16CharsetName = StandardCharsets.UTF_16.name();

        assertGetBytesUncheckedMatchesJdkEncoding(utf16CharsetName);

        final byte[] expected = STRING_FIXTURE.getBytes(utf16CharsetName);
        final byte[] actual = StringUtils.getBytesUtf16(STRING_FIXTURE);

        assertArrayEquals(expected, actual);
    }
}
