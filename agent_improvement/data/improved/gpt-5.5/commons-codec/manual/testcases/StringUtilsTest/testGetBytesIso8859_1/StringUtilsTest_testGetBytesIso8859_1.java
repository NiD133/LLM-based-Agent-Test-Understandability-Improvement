package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testGetBytesIso8859_1 {

    private static final String STRING_FIXTURE = "ABC";

    private void assertGetBytesUncheckedMatchesJdk(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesIso8859_1() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.ISO_8859_1.name();

        assertGetBytesUncheckedMatchesJdk(charsetName);

        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesIso8859_1(STRING_FIXTURE);

        assertArrayEquals(expected, actual);
    }
}
