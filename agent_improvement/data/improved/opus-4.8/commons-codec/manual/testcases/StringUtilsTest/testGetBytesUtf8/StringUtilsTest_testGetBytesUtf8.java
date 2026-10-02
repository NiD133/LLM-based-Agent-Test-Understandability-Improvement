package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringUtils#getBytesUtf8(String)} encodes a String to bytes
 * exactly as the JDK's {@link String#getBytes(String)} does for the UTF-8 charset.
 *
 * <p>As a baseline, the test also confirms that the more general
 * {@link StringUtils#getBytesUnchecked(String, String)} agrees with the JDK for the
 * same UTF-8 charset name.</p>
 */
public class StringUtilsTest_testGetBytesUtf8 {

    /** Sample text to encode. All characters are ASCII, so UTF-8 yields one byte each. */
    private static final String STRING_TO_ENCODE = "ABC";

    /** The canonical UTF-8 charset name ("UTF-8"). */
    private static final String UTF_8_NAME = StandardCharsets.UTF_8.name();

    /** The bytes the JDK produces for {@link #STRING_TO_ENCODE} under UTF-8; the expected result. */
    private static final byte[] EXPECTED_UTF_8_BYTES = STRING_TO_ENCODE.getBytes(StandardCharsets.UTF_8);

    @Test
    void testGetBytesUtf8() throws UnsupportedEncodingException {
        // The named-charset entry point must match the JDK for the UTF-8 charset name.
        assertArrayEquals(EXPECTED_UTF_8_BYTES, StringUtils.getBytesUnchecked(STRING_TO_ENCODE, UTF_8_NAME),
            "getBytesUnchecked with \"UTF-8\" should match String#getBytes");

        // The dedicated UTF-8 entry point must produce the same bytes.
        assertArrayEquals(EXPECTED_UTF_8_BYTES, StringUtils.getBytesUtf8(STRING_TO_ENCODE),
            "getBytesUtf8 should match String#getBytes(\"UTF-8\")");
    }
}
