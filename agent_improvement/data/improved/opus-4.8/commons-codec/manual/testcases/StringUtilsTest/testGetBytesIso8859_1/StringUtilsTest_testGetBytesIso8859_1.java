package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils} encodes a String to bytes using the ISO-8859-1 charset,
 * both through the generic {@code getBytesUnchecked(String, charsetName)} method and through
 * the dedicated {@code getBytesIso8859_1(String)} convenience method.
 */
public class StringUtilsTest_testGetBytesIso8859_1 {

    /** The String that gets encoded in every assertion below. */
    private static final String STRING_TO_ENCODE = "ABC";

    /** Canonical name of the ISO-8859-1 charset, e.g. "ISO-8859-1". */
    private static final String ISO_8859_1_NAME = StandardCharsets.ISO_8859_1.name();

    @Test
    void testGetBytesIso8859_1() throws UnsupportedEncodingException {
        // The JDK's String#getBytes is the source of truth for the expected encoding.
        final byte[] expected = STRING_TO_ENCODE.getBytes(ISO_8859_1_NAME);

        // getBytesUnchecked(...) must match the JDK encoding when given the charset name.
        assertArrayEquals(expected, StringUtils.getBytesUnchecked(STRING_TO_ENCODE, ISO_8859_1_NAME));

        // The ISO-8859-1 convenience method must produce the same bytes.
        assertArrayEquals(expected, StringUtils.getBytesIso8859_1(STRING_TO_ENCODE));
    }
}
