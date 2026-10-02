package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testHebrewTest {

    // Lowercase English letters plus space, used as an encoding target alphabet
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    // Decimal digit characters, used as an encoding target alphabet
    private static final Character[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    // Binary digit characters, used as the most compact encoding target alphabet
    private static final Character[] BINARY = { '0', '1' };

    // Hebrew alphabet characters (alef-bet) plus underscore word-separator and space
    private static final Character[] HEBREW = {
        '_', ' ',
        'ק', 'ר', 'א', 'ט', 'ו', 'ן', 'ם', 'פ',
        'ש', 'ד', 'ג', 'כ', 'ע', 'י', 'ח', 'ל',
        'ך', 'ף', 'ז', 'ס', 'ב', 'ה', 'נ', 'מ',
        'צ', 'ת', 'ץ'
    };

    // A multi-word Hebrew sentence used as a realistic encoding input
    private static final String HEBREW_SENTENCE =
        "אלף_אוהבל_בית_זה_בית_"
        + "גימל_זה_כמל_גדול";

    /**
     * Verifies that the given converter correctly round-trips each string:
     * encoding uses only encoding-alphabet characters, decoding restores the
     * original, and the converter can be faithfully reconstructed from its map.
     */
    private void test(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... strings) throws UnsupportedEncodingException {

        final AlphabetConverter ac =
            AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // Verify round-trip consistency when reconstructed from the encoding map
        final AlphabetConverter reconstructedAlphabetConverter =
            AlphabetConverter.createConverterFromMap(ac.getOriginalToEncoded());
        assertEquals(ac, reconstructedAlphabetConverter);
        assertEquals(ac.hashCode(), reconstructedAlphabetConverter.hashCode());
        assertEquals(ac.toString(), reconstructedAlphabetConverter.toString());

        // Null and empty-string edge cases
        assertNull(ac.encode(null));
        assertEquals("", ac.encode(""));

        for (final String s : strings) {
            final String encoded = ac.encode(s);

            // Every character in the encoded output must belong to the encoding alphabet
            final List<Character> originalEncodingChars = Arrays.asList(encodingChars);
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(originalEncodingChars.contains(encoded.charAt(i)));
            }

            final String decoded = ac.decode(encoded);

            // Every character in the decoded output must belong to the original alphabet
            final List<Character> originalCharsList = Arrays.asList(originalChars);
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalCharsList.contains(decoded.charAt(i)));
            }

            // Decoding the encoded form must restore the original string exactly
            assertEquals(s, decoded,
                () -> "Encoded '" + s + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testHebrewTest() throws UnsupportedEncodingException {
        // Hebrew source alphabet encoded into binary (requires multi-character codes)
        test(HEBREW, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            "א", "ע", HEBREW_SENTENCE);

        // Hebrew source alphabet encoded into decimal digits
        test(HEBREW, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            "א", "ע", HEBREW_SENTENCE);

        // Decimal digits encoded into Hebrew characters
        test(NUMBERS, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            "123456789", "1", "5");

        // Lowercase English letters encoded into Hebrew characters
        test(LOWER_CASE_ENGLISH, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            "this is a test");
    }
}
