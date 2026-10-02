package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUsAscii {

    private static final String ASCII_TEXT = "ABC";

    @Test
    void testGetBytesUsAscii() throws UnsupportedEncodingException {
        final String usAsciiCharsetName = StandardCharsets.US_ASCII.name();

        assertGetBytesUncheckedMatchesJdkEncoding(usAsciiCharsetName);

        final byte[] expectedUsAsciiBytes = ASCII_TEXT.getBytes(usAsciiCharsetName);
        final byte[] actualUsAsciiBytes = StringUtils.getBytesUsAscii(ASCII_TEXT);
        assertArrayEquals(expectedUsAsciiBytes, actualUsAsciiBytes);
    }

    private void assertGetBytesUncheckedMatchesJdkEncoding(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expectedBytes = ASCII_TEXT.getBytes(charsetName);
        final byte[] actualBytes = StringUtils.getBytesUnchecked(ASCII_TEXT, charsetName);
        assertArrayEquals(expectedBytes, actualBytes);
    }
}
