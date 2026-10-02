package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils} encodes strings to UTF-16BE bytes,
 * both through the generic {@code getBytesUnchecked(String, String)} entry
 * point and through the dedicated {@code getBytesUtf16Be(String)} shortcut.
 */
public class StringUtilsTest_testGetBytesUtf16Be {

    /** The string that every assertion in this test encodes. */
    private static final String STRING_FIXTURE = "ABC";

    /**
     * Encodes {@link #STRING_FIXTURE} with the given charset name and checks that
     * {@link StringUtils#getBytesUnchecked(String, String)} matches the JDK's own
     * {@link String#getBytes(String)}.
     */
    private void assertGetBytesUncheckedMatchesJdk(final String charsetName) throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(charsetName);
        final byte[] actual = StringUtils.getBytesUnchecked(STRING_FIXTURE, charsetName);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        final String utf16BeName = StandardCharsets.UTF_16BE.name();

        // The generic charset-name based method must agree with the JDK.
        assertGetBytesUncheckedMatchesJdk(utf16BeName);

        // The dedicated UTF-16BE shortcut must produce the same bytes.
        final byte[] expected = STRING_FIXTURE.getBytes(utf16BeName);
        final byte[] actual = StringUtils.getBytesUtf16Be(STRING_FIXTURE);
        assertArrayEquals(expected, actual);
    }
}
