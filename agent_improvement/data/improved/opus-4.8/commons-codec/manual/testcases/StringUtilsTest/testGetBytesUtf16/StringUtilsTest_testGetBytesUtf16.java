package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#getBytesUtf16(String)} and confirms that the generic
 * {@link StringUtils#getBytesUnchecked(String, String)} agrees with the JDK when
 * the UTF-16 charset name is used.
 */
public class StringUtilsTest_testGetBytesUtf16 {

    /** The string that gets encoded to bytes in every assertion below. */
    private static final String STRING_FIXTURE = "ABC";

    @Test
    void testGetBytesUtf16() throws UnsupportedEncodingException {
        final String utf16Name = StandardCharsets.UTF_16.name();

        // getBytesUnchecked(String, charsetName) must match String#getBytes(charsetName).
        final byte[] expectedUnchecked = STRING_FIXTURE.getBytes(utf16Name);
        final byte[] actualUnchecked = StringUtils.getBytesUnchecked(STRING_FIXTURE, utf16Name);
        assertArrayEquals(expectedUnchecked, actualUnchecked);

        // getBytesUtf16(String) is a UTF-16 shortcut and must produce the same bytes.
        final byte[] expectedUtf16 = STRING_FIXTURE.getBytes(utf16Name);
        final byte[] actualUtf16 = StringUtils.getBytesUtf16(STRING_FIXTURE);
        assertArrayEquals(expectedUtf16, actualUtf16);
    }
}
