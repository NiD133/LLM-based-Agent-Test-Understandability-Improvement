package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link AlphabetConverter} round-trip behaviour (encode then decode) for
 * cases where the original alphabet is converted into a smaller "binary-like"
 * encoding alphabet, so each original character maps to a fixed-length group of
 * encoding characters.
 */
public class AlphabetConverterTest_testBinaryTest {

    /** The full lower-case English alphabet, prefixed with a space character. */
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /** The ten decimal digits. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /** A two-character alphabet: the binary digits. */
    private static final Character[] BINARY = { '0', '1' };

    /** No characters are exempt from encoding in these scenarios. */
    private static final Character[] ENCODE_EVERYTHING = ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;

    @Test
    void testBinaryTest() throws UnsupportedEncodingException {
        // Encode binary digits using the (larger) decimal-digit alphabet.
        assertRoundTrips(BINARY, NUMBERS, ENCODE_EVERYTHING, "0", "1", "10", "11");

        // Encode decimal digits using the (smaller) binary alphabet.
        assertRoundTrips(NUMBERS, BINARY, ENCODE_EVERYTHING, "12345", "0");

        // Encode lower-case English text using the (much smaller) binary alphabet.
        assertRoundTrips(LOWER_CASE_ENGLISH, BINARY, ENCODE_EVERYTHING, "abc", "a");
    }

    /**
     * Builds a converter for the given alphabets and verifies that, for every
     * supplied input string, encoding followed by decoding reproduces the
     * original string. Also checks the converter can be reconstructed from its
     * map form and that the various conversion edge cases behave as documented.
     *
     * @param originalChars    the alphabet of the input strings
     * @param encodingChars    the alphabet used to represent encoded output
     * @param doNotEncodeChars characters that must be left unencoded
     * @param inputs           the strings to round-trip through the converter
     */
    private void assertRoundTrips(final Character[] originalChars,
                                  final Character[] encodingChars,
                                  final Character[] doNotEncodeChars,
                                  final String... inputs) throws UnsupportedEncodingException {
        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // A converter rebuilt from its map form must be equivalent to the original.
        final AlphabetConverter reconstructed =
                AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());
        assertEquals(converter, reconstructed);
        assertEquals(converter.hashCode(), reconstructed.hashCode());
        assertEquals(converter.toString(), reconstructed.toString());

        // Documented edge cases: null encodes to null, empty encodes to empty.
        assertNull(converter.encode(null));
        assertEquals("", converter.encode(""));

        final List<Character> allowedEncodingChars = Arrays.asList(encodingChars);
        final List<Character> allowedOriginalChars = Arrays.asList(originalChars);

        for (final String input : inputs) {
            final String encoded = converter.encode(input);
            assertOnlyContains(encoded, allowedEncodingChars);

            final String decoded = converter.decode(encoded);
            assertOnlyContains(decoded, allowedOriginalChars);

            assertEquals(input, decoded,
                    () -> "Encoded '" + input + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    /**
     * Asserts that every character of {@code text} belongs to {@code allowedChars}.
     */
    private void assertOnlyContains(final String text, final List<Character> allowedChars) {
        for (int i = 0; i < text.length(); i++) {
            assertTrue(allowedChars.contains(text.charAt(i)));
        }
    }
}
