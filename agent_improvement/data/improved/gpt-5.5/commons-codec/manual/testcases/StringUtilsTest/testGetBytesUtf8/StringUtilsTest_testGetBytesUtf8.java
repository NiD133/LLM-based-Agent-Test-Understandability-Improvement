package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesUtf8 {

    private static final String STRING_FIXTURE = "ABC";

    private void testGetBytesUnchecked(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf8() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_8.name();

        testGetBytesUnchecked(charsetName);

        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUtf8(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }
}
