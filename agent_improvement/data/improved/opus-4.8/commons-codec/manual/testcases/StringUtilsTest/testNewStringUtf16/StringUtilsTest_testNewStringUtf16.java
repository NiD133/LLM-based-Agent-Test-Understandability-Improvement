package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newStringUtf16(byte[])}.
 *
 * <p>The convenience method {@code newStringUtf16(bytes)} must decode bytes exactly as
 * {@code new String(bytes, "UTF-16")} does. We verify this against both the JDK constructor
 * directly and the generic {@link StringUtils#newString(byte[], String)} method, which the
 * convenience method delegates to.</p>
 */
public class StringUtilsTest_testNewStringUtf16 {

    /** Arbitrary bytes to decode; the exact content is irrelevant, only that decoding is consistent. */
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    private static final String UTF_16_NAME = StandardCharsets.UTF_16.name();

    @Test
    void testNewStringUtf16() throws UnsupportedEncodingException {
        // Reference result: decode the bytes with the JDK String constructor using UTF-16.
        final String expected = new String(BYTES_FIXTURE, UTF_16_NAME);

        // The generic newString(bytes, charsetName) must match the JDK constructor.
        assertEquals(expected, StringUtils.newString(BYTES_FIXTURE, UTF_16_NAME));

        // The UTF-16 convenience overload must produce the same result.
        assertEquals(expected, StringUtils.newStringUtf16(BYTES_FIXTURE));
    }
}
