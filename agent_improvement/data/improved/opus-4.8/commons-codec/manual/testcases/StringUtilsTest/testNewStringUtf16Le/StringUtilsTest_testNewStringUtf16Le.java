package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newStringUtf16Le(byte[])}, which decodes a byte array
 * into a String using the UTF-16 little-endian charset.
 */
public class StringUtilsTest_testNewStringUtf16Le {

    /** Charset name passed to the generic {@link StringUtils#newString(byte[], String)} overload. */
    private static final String UTF_16LE_NAME = StandardCharsets.UTF_16LE.name();

    /**
     * Plain single-byte content {@code 'a', 'b', 'c'}. When decoded as UTF-16LE,
     * each pair of bytes forms one character, so the decoded result is the same
     * whether produced by {@code new String(...)} or by {@link StringUtils}.
     */
    private static final byte[] PLAIN_BYTES = { 'a', 'b', 'c' };

    /**
     * The text "abc" encoded as UTF-16LE: every character is a low byte followed
     * by a zero high byte ({@code 'a', 0, 'b', 0, 'c', 0}).
     */
    private static final byte[] UTF_16LE_BYTES = { 'a', 0, 'b', 0, 'c', 0 };

    @Test
    void testNewStringUtf16Le() throws UnsupportedEncodingException {
        // The named-charset overload must match the JDK's own decoding of the same bytes.
        final String expectedFromName = new String(PLAIN_BYTES, UTF_16LE_NAME);
        final String actualFromName = StringUtils.newString(PLAIN_BYTES, UTF_16LE_NAME);
        assertEquals(expectedFromName, actualFromName);

        // The dedicated UTF-16LE convenience method must likewise match the JDK's decoding.
        final String expectedUtf16Le = new String(UTF_16LE_BYTES, UTF_16LE_NAME);
        final String actualUtf16Le = StringUtils.newStringUtf16Le(UTF_16LE_BYTES);
        assertEquals(expectedUtf16Le, actualUtf16Le);
    }
}
