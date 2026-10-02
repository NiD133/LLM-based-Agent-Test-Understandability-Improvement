package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testBinaryTest {

    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final Character[] BINARY = { '0', '1' };

    /**
     * Verifies that encoding followed by decoding reproduces the original string,
     * and that the converter reconstructed from its own map is equivalent to the original.
     *
     * @param originalChars   the source alphabet
     * @param encodingChars   the target alphabet used for encoding
     * @param doNotEncodeChars characters that should pass through unencoded
     * @param strings         sample strings to encode and decode
     */
    private void assertRoundTripEncoding(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... strings) throws UnsupportedEncodingException {

        final AlphabetConverter ac =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // A converter rebuilt from the serialized map must be identical to the original.
        final AlphabetConverter reconstructedAlphabetConverter =
                AlphabetConverter.createConverterFromMap(ac.getOriginalToEncoded());
        assertEquals(ac, reconstructedAlphabetConverter);
        assertEquals(ac.hashCode(), reconstructedAlphabetConverter.hashCode());
        assertEquals(ac.toString(), reconstructedAlphabetConverter.toString());

        // Encoding null should return null; encoding empty string should return empty string.
        assertNull(ac.encode(null));
        assertEquals("", ac.encode(""));

        final List<Character> encodingCharList = Arrays.asList(encodingChars);
        final List<Character> originalCharList  = Arrays.asList(originalChars);

        for (final String s : strings) {
            final String encoded = ac.encode(s);

            // Every character in the encoded output must belong to the encoding alphabet.
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(encodingCharList.contains(encoded.charAt(i)));
            }

            final String decoded = ac.decode(encoded);

            // Every character in the decoded output must belong to the original alphabet.
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalCharList.contains(decoded.charAt(i)));
            }

            // The decoded string must equal the original input.
            assertEquals(s, decoded,
                    () -> "Encoded '" + s + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testBinaryTest() throws UnsupportedEncodingException {
        // Encoding a binary alphabet (0/1) using decimal digits as the target alphabet.
        assertRoundTripEncoding(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "0", "1", "10", "11");

        // Encoding decimal digits using a binary alphabet (0/1) as the target alphabet.
        assertRoundTripEncoding(NUMBERS, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "12345", "0");

        // Encoding lower-case English letters using a binary alphabet (0/1) as the target alphabet.
        assertRoundTripEncoding(LOWER_CASE_ENGLISH, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "abc", "a");
    }
}
