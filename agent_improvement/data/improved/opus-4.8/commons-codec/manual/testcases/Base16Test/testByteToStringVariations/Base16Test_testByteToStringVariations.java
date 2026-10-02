package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16} turns raw bytes into their hexadecimal string form
 * the same way through two paths:
 * <ul>
 *   <li>the instance convenience method {@link Base16#encodeToString(byte[])}, and</li>
 *   <li>encoding to bytes via {@link Base16#encode(byte[])} and then decoding those
 *       bytes back to a UTF-8 string with {@link StringUtils#newStringUtf8(byte[])}.</li>
 * </ul>
 * Both paths are exercised against three representative inputs: normal text, an empty
 * array, and {@code null}.
 */
public class Base16Test_testByteToStringVariations {

    /** Plain-text input used as the "normal" (non-trivial) case. */
    private static final String PLAIN_TEXT = "Hello World";

    /** Expected Base16 (upper-case hex) encoding of {@link #PLAIN_TEXT}. */
    private static final String PLAIN_TEXT_AS_BASE16 = "48656C6C6F20576F726C64";

    @Test
    void testByteToStringVariations() {
        final Base16 base16 = new Base16();

        final byte[] plainTextBytes = StringUtils.getBytesUtf8(PLAIN_TEXT);
        final byte[] emptyBytes = {};
        final byte[] nullBytes = null;

        // Normal text encodes to its hex representation via both paths.
        assertEquals(PLAIN_TEXT_AS_BASE16, base16.encodeToString(plainTextBytes),
                "byteToString Hello World");
        assertEquals(PLAIN_TEXT_AS_BASE16, StringUtils.newStringUtf8(new Base16().encode(plainTextBytes)),
                "byteToString static Hello World");

        // An empty input encodes to an empty string via both paths.
        assertEquals("", base16.encodeToString(emptyBytes),
                "byteToString \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().encode(emptyBytes)),
                "byteToString static \"\"");

        // A null input yields null via both paths.
        assertNull(base16.encodeToString(nullBytes),
                "byteToString null");
        assertNull(StringUtils.newStringUtf8(new Base16().encode(nullBytes)),
                "byteToString static null");
    }
}
