package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testDoNotEncodeTest {

    // Source alphabet: digits, both-case English letters, and space
    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        ' '
    };

    // Encoding alphabet: digits, lowercase English letters, and space
    private static final Character[] LOWER_CASE_ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        ' '
    };

    // Characters to leave unencoded in scenario 1: lowercase letters and space
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    // Characters to leave unencoded in scenario 2: digits only
    private static final Character[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * Creates an AlphabetConverter from the given alphabets, then verifies:
     * <ul>
     *   <li>The converter can be reconstructed from its own mapping and remains equal.</li>
     *   <li>Encoding {@code null} returns {@code null}; encoding {@code ""} returns {@code ""}.</li>
     *   <li>Each encoded string uses only characters from {@code encodingChars}.</li>
     *   <li>Decoding the encoded string recovers the original input exactly.</li>
     * </ul>
     */
    private void assertEncodeDecodeRoundtrip(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... testStrings) throws UnsupportedEncodingException {

        final AlphabetConverter ac =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);
        final AlphabetConverter reconstructed =
                AlphabetConverter.createConverterFromMap(ac.getOriginalToEncoded());

        // Converter reconstructed from its mapping must be identical
        assertEquals(ac, reconstructed);
        assertEquals(ac.hashCode(), reconstructed.hashCode());
        assertEquals(ac.toString(), reconstructed.toString());

        // Edge cases: null and empty input
        assertNull(ac.encode(null));
        assertEquals("", ac.encode(""));

        final List<Character> encodingCharList = Arrays.asList(encodingChars);
        final List<Character> originalCharList  = Arrays.asList(originalChars);

        for (final String input : testStrings) {
            final String encoded = ac.encode(input);

            // Every character in the encoded output must belong to the encoding alphabet
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(encodingCharList.contains(encoded.charAt(i)));
            }

            final String decoded = ac.decode(encoded);

            // Every character in the decoded output must belong to the original alphabet
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalCharList.contains(decoded.charAt(i)));
            }

            assertEquals(input, decoded,
                    () -> "Encoded '" + input + "' into '" + encoded
                            + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testDoNotEncodeTest() throws UnsupportedEncodingException {
        // Scenario 1: lowercase letters and space pass through unencoded;
        // digits and uppercase letters are encoded using the lowercase-and-numbers alphabet.
        assertEncodeDecodeRoundtrip(
                ENGLISH_AND_NUMBERS,
                LOWER_CASE_ENGLISH_AND_NUMBERS,
                LOWER_CASE_ENGLISH,
                "1",
                "456",
                "abc",
                "ABC",
                "this will not be converted but THIS WILL");

        // Scenario 2: digits pass through unencoded;
        // all letters (lower- and uppercase) are encoded using the lowercase-and-numbers alphabet.
        assertEncodeDecodeRoundtrip(
                ENGLISH_AND_NUMBERS,
                LOWER_CASE_ENGLISH_AND_NUMBERS,
                NUMBERS,
                "1",
                "456",
                "abc",
                "ABC",
                "this will be converted but 12345 and this will be");
    }
}
