package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#newStringUsAscii(byte[])}.
 *
 * <p>
 * The fixture {@code {'a', 'b', 'c'}} decodes to {@code "abc"} under US-ASCII, so both the
 * convenience method {@code newStringUsAscii} and the generic {@code newString} with the
 * "US-ASCII" charset name must match the JDK reference decoding.
 * </p>
 */
public class StringUtilsTest_testNewStringUsAscii {

    /** ASCII bytes for the characters 'a', 'b', 'c'. */
    private static final byte[] ASCII_BYTES = { 'a', 'b', 'c' };

    @Test
    void testNewStringUsAscii() throws UnsupportedEncodingException {
        final String charsetName = StandardCharsets.US_ASCII.name();

        // Reference value produced by the JDK's own US-ASCII decoder.
        final String expected = new String(ASCII_BYTES, charsetName);

        // The charset-name overload must agree with the JDK reference.
        assertEquals(expected, StringUtils.newString(ASCII_BYTES, charsetName));

        // The US-ASCII convenience method must produce the same result.
        assertEquals(expected, StringUtils.newStringUsAscii(ASCII_BYTES));
    }
}
