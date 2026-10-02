package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link AlphabetConverter} when a "do not encode" alphabet is supplied:
 * the listed characters must survive an encode/decode round trip unchanged, while
 * every other character is translated into the encoding alphabet.
 */
public class AlphabetConverterTest_testDoNotEncodeTest {

    /** Original alphabet: digits, lower-case and upper-case English letters, plus a space. */
    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        ' '
    };

    /** Encoding alphabet: digits, lower-case English letters, plus a space. */
    private static final Character[] LOWER_CASE_ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        ' '
    };

    /** "Do not encode" set covering the space and the lower-case English letters. */
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    /** "Do not encode" set covering only the digits. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Test
    void testDoNotEncodeTest() throws UnsupportedEncodingException {
        // Keep the lower-case letters and space unencoded; only digits and upper-case letters get encoded.
        assertRoundTripPreservesAlphabets(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH,
            "1", "456", "abc", "ABC", "this will not be converted but THIS WILL");

        // Keep the digits unencoded; letters get encoded.
        assertRoundTripPreservesAlphabets(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH_AND_NUMBERS, NUMBERS,
            "1", "456", "abc", "ABC", "this will be converted but 12345 and this will be");
    }

    /**
     * Builds a converter from the given alphabets and asserts that, for every sample string, encoding then
     * decoding reproduces the original input while respecting the alphabet boundaries.
     *
     * @param originalChars    the original alphabet
     * @param encodingChars    the alphabet that encoded output is restricted to
     * @param doNotEncodeChars characters that must pass through unencoded
     * @param strings          sample inputs to round trip
     */
    private void assertRoundTripPreservesAlphabets(final Character[] originalChars,
                                                   final Character[] encodingChars,
                                                   final Character[] doNotEncodeChars,
                                                   final String... strings) throws UnsupportedEncodingException {
        final AlphabetConverter converter =
            AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // A converter rebuilt from its own mapping must be equivalent in every observable way.
        final AlphabetConverter reconstructed =
            AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());
        assertEquals(converter, reconstructed);
        assertEquals(converter.hashCode(), reconstructed.hashCode());
        assertEquals(converter.toString(), reconstructed.toString());

        // Edge cases: null encodes to null, empty string encodes to empty string.
        assertNull(converter.encode(null));
        assertEquals("", converter.encode(""));

        final List<Character> allowedEncodingChars = Arrays.asList(encodingChars);
        final List<Character> allowedOriginalChars = Arrays.asList(originalChars);

        for (final String input : strings) {
            final String encoded = converter.encode(input);
            // Encoded output may only use characters from the encoding alphabet.
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(allowedEncodingChars.contains(encoded.charAt(i)));
            }

            final String decoded = converter.decode(encoded);
            // Decoded output may only use characters from the original alphabet.
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(allowedOriginalChars.contains(decoded.charAt(i)));
            }

            assertEquals(input, decoded,
                () -> "Encoded '" + input + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }
}
