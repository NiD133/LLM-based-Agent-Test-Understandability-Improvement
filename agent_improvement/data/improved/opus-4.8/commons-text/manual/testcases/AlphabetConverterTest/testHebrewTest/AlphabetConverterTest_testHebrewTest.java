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
 * Round-trip tests for {@link AlphabetConverter} that exercise a Hebrew alphabet,
 * both as the original alphabet and as the encoding alphabet.
 */
public class AlphabetConverterTest_testHebrewTest {

    /** A 27-character Hebrew alphabet (plus the underscore and space separators). */
    private static final Character[] HEBREW = {
        '_', ' ', 'ק', 'ר', 'א', 'ט', 'ו', 'ן', 'ם',
        'פ', 'ש', 'ד', 'ג', 'כ', 'ע', 'י', 'ח',
        'ל', 'ך', 'ף', 'ז', 'ס', 'ב', 'ה', 'נ',
        'מ', 'צ', 'ת', 'ץ'
    };

    /** The decimal digits 0-9, used as a 10-character encoding alphabet. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /** A two-character binary encoding alphabet. */
    private static final Character[] BINARY = { '0', '1' };

    /** Lower-case English letters (preceded by a space), used as an original alphabet. */
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    /** Hebrew word meaning roughly "aleph". */
    private static final String HEBREW_ALEPH = "א";

    /** Hebrew letter "ayin". */
    private static final String HEBREW_AYIN = "ע";

    /** A longer Hebrew sentence (with '_' word separators) used to exercise multi-letter encodings. */
    private static final String HEBREW_SENTENCE =
        "אלף_אוהבל_בית_זה_בית_"
        + "גימל_זה_כמל_גדול";

    /**
     * Builds a converter from the given alphabets, then verifies that:
     * <ul>
     *   <li>a converter reconstructed from the original-to-encoded map is equal to it,</li>
     *   <li>{@code null} and empty inputs are handled, and</li>
     *   <li>every trial string survives an encode/decode round trip, using only the
     *       expected alphabets along the way.</li>
     * </ul>
     */
    private void assertRoundTrips(final Character[] originalChars,
                                  final Character[] encodingChars,
                                  final Character[] doNotEncodeChars,
                                  final String... trialStrings) throws UnsupportedEncodingException {
        final AlphabetConverter converter =
            AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // A converter rebuilt from its own map must be indistinguishable from the original.
        final AlphabetConverter reconstructed =
            AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());
        assertEquals(converter, reconstructed);
        assertEquals(converter.hashCode(), reconstructed.hashCode());
        assertEquals(converter.toString(), reconstructed.toString());

        // Boundary inputs.
        assertNull(converter.encode(null), "encoding null should return null");
        assertEquals("", converter.encode(""), "encoding the empty string should return the empty string");

        final List<Character> allowedEncodingChars = Arrays.asList(encodingChars);
        final List<Character> allowedOriginalChars = Arrays.asList(originalChars);

        for (final String original : trialStrings) {
            final String encoded = converter.encode(original);
            assertOnlyUses(allowedEncodingChars, encoded, "encoded output");

            final String decoded = converter.decode(encoded);
            assertOnlyUses(allowedOriginalChars, decoded, "decoded output");

            assertEquals(original, decoded,
                () -> "Encoded '" + original + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    /** Asserts that every character in {@code text} is contained in {@code allowed}. */
    private void assertOnlyUses(final List<Character> allowed, final String text, final String description) {
        for (int i = 0; i < text.length(); i++) {
            assertTrue(allowed.contains(text.charAt(i)),
                () -> "Unexpected character in " + description + ": '" + text + "'");
        }
    }

    @Test
    void testHebrewTest() throws UnsupportedEncodingException {
        // Hebrew original alphabet encoded into binary digits (long, multi-letter encodings).
        assertRoundTrips(HEBREW, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            HEBREW_ALEPH, HEBREW_AYIN, HEBREW_SENTENCE);

        // Hebrew original alphabet encoded into decimal digits.
        assertRoundTrips(HEBREW, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            HEBREW_ALEPH, HEBREW_AYIN, HEBREW_SENTENCE);

        // Digits encoded into the (larger) Hebrew alphabet.
        assertRoundTrips(NUMBERS, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            "123456789", "1", "5");

        // English text encoded into the Hebrew alphabet.
        assertRoundTrips(LOWER_CASE_ENGLISH, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
            "this is a test");
    }
}
