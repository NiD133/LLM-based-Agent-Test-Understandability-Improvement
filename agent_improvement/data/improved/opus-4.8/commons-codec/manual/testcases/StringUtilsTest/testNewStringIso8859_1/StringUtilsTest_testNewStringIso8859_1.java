package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newStringIso8859_1(byte[])}.
 *
 * <p>The dedicated ISO-8859-1 helper should decode bytes exactly the same way as:
 * <ul>
 *   <li>the JDK reference {@code new String(bytes, "ISO-8859-1")}, and</li>
 *   <li>the generic {@link StringUtils#newString(byte[], String)} given the same charset name.</li>
 * </ul>
 */
public class StringUtilsTest_testNewStringIso8859_1 {

    /** Bytes used as the decoding input across all assertions. */
    private static final byte[] BYTES_FIXTURE = { 'a', 'b', 'c' };

    /** Canonical name of the charset under test ("ISO-8859-1"). */
    private static final String ISO_8859_1_NAME = StandardCharsets.ISO_8859_1.name();

    @Test
    void testNewStringIso8859_1() throws UnsupportedEncodingException {
        // The JDK's own ISO-8859-1 decoding is our source of truth.
        final String expected = new String(BYTES_FIXTURE, ISO_8859_1_NAME);

        // The generic, charset-name-driven helper must match the JDK reference.
        assertEquals(expected, StringUtils.newString(BYTES_FIXTURE, ISO_8859_1_NAME));

        // The dedicated ISO-8859-1 helper must produce the same result.
        assertEquals(expected, StringUtils.newStringIso8859_1(BYTES_FIXTURE));
    }
}
