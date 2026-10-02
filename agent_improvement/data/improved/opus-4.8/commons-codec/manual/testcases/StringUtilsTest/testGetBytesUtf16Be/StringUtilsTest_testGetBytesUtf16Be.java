package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringUtils} encodes a String to UTF-16BE bytes correctly.
 *
 * <p>The reference value is always {@link String#getBytes(String)} using the JDK's own
 * UTF-16BE encoder, so each assertion checks that {@code StringUtils} produces exactly the
 * same bytes the platform would.</p>
 */
public class StringUtilsTest_testGetBytesUtf16Be {

    /** Arbitrary ASCII text to encode; in UTF-16BE this becomes {0,'A', 0,'B', 0,'C'}. */
    private static final String STRING_TO_ENCODE = "ABC";

    @Test
    void testGetBytesUtf16Be() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.UTF_16BE.name();

        // The JDK's UTF-16BE encoding of the input is the expected result for every call below.
        final byte[] expectedUtf16BeBytes = STRING_TO_ENCODE.getBytes(charsetName);

        // getBytesUnchecked(String, charsetName) must match the JDK encoder for UTF-16BE.
        assertArrayEquals(expectedUtf16BeBytes,
                StringUtils.getBytesUnchecked(STRING_TO_ENCODE, charsetName));

        // The dedicated getBytesUtf16Be(String) convenience method must match it too.
        assertArrayEquals(expectedUtf16BeBytes,
                StringUtils.getBytesUtf16Be(STRING_TO_ENCODE));
    }
}
