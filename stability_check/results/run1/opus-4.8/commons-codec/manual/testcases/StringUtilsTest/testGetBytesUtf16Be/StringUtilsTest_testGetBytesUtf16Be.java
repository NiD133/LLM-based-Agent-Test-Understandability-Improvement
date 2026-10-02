package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils} encodes strings to bytes using the UTF-16BE
 * charset in the same way as the standard JDK {@link String#getBytes(String)}.
 */
public class StringUtilsTest_testGetBytesUtf16Be {

    /** The string encoded throughout this test. */
    private static final String STRING_TO_ENCODE = "ABC";

    /** Canonical name of the UTF-16BE charset (i.e. "UTF-16BE"). */
    private static final String UTF_16BE = StandardCharsets.UTF_16BE.name();

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        // The bytes the JDK produces for UTF-16BE are our reference result.
        final byte[] expected = STRING_TO_ENCODE.getBytes(UTF_16BE);

        // getBytesUnchecked with the charset name must match the JDK output.
        assertArrayEquals(expected, StringUtils.getBytesUnchecked(STRING_TO_ENCODE, UTF_16BE));

        // The dedicated UTF-16BE convenience method must match it too.
        assertArrayEquals(expected, StringUtils.getBytesUtf16Be(STRING_TO_ENCODE));
    }
}
