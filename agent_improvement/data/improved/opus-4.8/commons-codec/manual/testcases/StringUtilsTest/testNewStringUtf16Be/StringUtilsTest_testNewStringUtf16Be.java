package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newStringUtf16Be(byte[])}.
 */
public class StringUtilsTest_testNewStringUtf16Be {

    /** Plain ASCII bytes, decoded below using the UTF-16BE charset. */
    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };

    /**
     * The string "abc" encoded as UTF-16BE: each character is two bytes,
     * high byte first (0x00) followed by the ASCII code point.
     */
    private static final byte[] UTF16BE_ENCODED_ABC = { 0, 'a', 0, 'b', 0, 'c' };

    @Test
    void newStringUtf16BeShouldMatchJavaUtf16BeDecoding() throws UnsupportedEncodingException {
        final String utf16BeName = StandardCharsets.UTF_16BE.name();

        // The generic newString(byte[], charsetName) must behave like the JDK's
        // String(byte[], charsetName) constructor for the UTF-16BE charset.
        assertEquals(
                new String(ASCII_BYTES, utf16BeName),
                StringUtils.newString(ASCII_BYTES, utf16BeName));

        // The dedicated UTF-16BE helper must decode identically to the JDK.
        assertEquals(
                new String(UTF16BE_ENCODED_ABC, utf16BeName),
                StringUtils.newStringUtf16Be(UTF16BE_ENCODED_ABC));
    }
}
