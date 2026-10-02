package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies the different ways a Base16-encoded {@link String} can be decoded back into bytes.
 *
 * <p>The encoded string {@code "48656C6C6F20576F726C64"} is the upper-case hexadecimal
 * representation of the UTF-8 bytes of {@code "Hello World"}.</p>
 */
public class Base16Test_testStringToByteVariations {

    /** Upper-case Base16 (hex) encoding of the UTF-8 bytes of "Hello World". */
    private static final String ENCODED_HELLO_WORLD = "48656C6C6F20576F726C64";

    /** Encoding of an empty input. */
    private static final String ENCODED_EMPTY = "";

    /** A {@code null} input. */
    private static final String ENCODED_NULL = null;

    /** Decodes the given Base16 string and returns the result as a UTF-8 string. */
    private static String decodeToUtf8(final String encoded) {
        return StringUtils.newStringUtf8(new Base16().decode(encoded));
    }

    @Test
    void testStringToByteVariations() throws DecoderException {
        final Base16 base16 = new Base16();

        // Decode via the String overload on a shared instance.
        assertEquals("Hello World", StringUtils.newStringUtf8(base16.decode(ENCODED_HELLO_WORLD)),
                "StringToByte Hello World");

        // Decode via the Object overload, which returns the bytes as an Object.
        assertEquals("Hello World", StringUtils.newStringUtf8((byte[]) new Base16().decode((Object) ENCODED_HELLO_WORLD)),
                "StringToByte Hello World");

        // Decode via the String overload on a fresh instance.
        assertEquals("Hello World", decodeToUtf8(ENCODED_HELLO_WORLD), "StringToByte static Hello World");

        // An empty encoding decodes to an empty string.
        assertEquals("", decodeToUtf8(ENCODED_EMPTY), "StringToByte \"\"");
        assertEquals("", decodeToUtf8(ENCODED_EMPTY), "StringToByte static \"\"");

        // A null encoding decodes to null.
        assertNull(decodeToUtf8(ENCODED_NULL), "StringToByte null");
        assertNull(decodeToUtf8(ENCODED_NULL), "StringToByte static null");
    }
}
