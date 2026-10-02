package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newStringUtf8(byte[])}.
 */
public class StringUtilsTest_testNewStringUtf8 {

    /** Sample bytes ("abc") that are decoded back into a String during the test. */
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    @Test
    void testNewStringUtf8() throws UnsupportedEncodingException {
        final String utf8 = StandardCharsets.UTF_8.name();

        // Decoding via the JDK is the reference behaviour we expect StringUtils to match.
        final String expected = new String(BYTES_FIXTURE, utf8);

        // The generic newString(bytes, charsetName) overload must agree with the JDK.
        assertEquals(expected, StringUtils.newString(BYTES_FIXTURE, utf8));

        // The UTF-8 convenience method must produce the same result.
        assertEquals(expected, StringUtils.newStringUtf8(BYTES_FIXTURE));
    }
}
