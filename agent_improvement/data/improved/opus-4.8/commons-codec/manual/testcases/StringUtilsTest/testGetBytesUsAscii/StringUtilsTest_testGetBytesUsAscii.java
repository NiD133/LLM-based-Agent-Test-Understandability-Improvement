package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#getBytesUsAscii(String)}.
 *
 * <p>
 * The expected bytes are derived from the JDK's own {@link String#getBytes(String)} so that the test verifies
 * the commons-codec helper produces exactly the same US-ASCII encoding.
 * </p>
 */
public class StringUtilsTest_testGetBytesUsAscii {

    /** The string that gets encoded in every assertion below. */
    private static final String STRING_FIXTURE = "ABC";

    /** Canonical name of the US-ASCII charset, e.g. "US-ASCII". */
    private static final String US_ASCII_NAME = StandardCharsets.US_ASCII.name();

    @Test
    void testGetBytesUsAscii() throws UnsupportedEncodingException {
        final byte[] expected = STRING_FIXTURE.getBytes(US_ASCII_NAME);

        // getBytesUnchecked(string, charsetName) must match String#getBytes(charsetName).
        assertArrayEquals(expected, StringUtils.getBytesUnchecked(STRING_FIXTURE, US_ASCII_NAME));

        // getBytesUsAscii(string) is the US-ASCII-specific shortcut and must produce the same bytes.
        assertArrayEquals(expected, StringUtils.getBytesUsAscii(STRING_FIXTURE));
    }
}
