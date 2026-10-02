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

    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final Character[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final Character[] BINARY = {
        '0', '1'
    };

    private static final Character[] HEBREW = {
        '_', ' ', '\u05e7', '\u05e8', '\u05d0', '\u05d8', '\u05d5',
        '\u05df', '\u05dd', '\u05e4', '\u05e9', '\u05d3', '\u05d2',
        '\u05db', '\u05e2', '\u05d9', '\u05d7', '\u05dc', '\u05da',
        '\u05e3', '\u05d6', '\u05e1', '\u05d1', '\u05d4', '\u05e0',
        '\u05de', '\u05e6', '\u05ea', '\u05e5'
    };

    private void assertRoundTripConversion(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... samples) throws UnsupportedEncodingException {
        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);
        final AlphabetConverter reconstructedConverter =
                AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());

        assertEquals(converter, reconstructedConverter);
        assertEquals(converter.hashCode(), reconstructedConverter.hashCode());
        assertEquals(converter.toString(), reconstructedConverter.toString());

        assertNull(converter.encode(null));
        assertEquals("", converter.encode(""));

        final List<Character> allowedEncodingChars = Arrays.asList(encodingChars);
        final List<Character> allowedOriginalChars = Arrays.asList(originalChars);

        for (final String sample : samples) {
            final String encoded = converter.encode(sample);
            assertOnlyContainsCharactersFrom(encoded, allowedEncodingChars);

            final String decoded = converter.decode(encoded);
            assertOnlyContainsCharactersFrom(decoded, allowedOriginalChars);

            assertEquals(sample, decoded,
                    () -> "Encoded '" + sample + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    private void assertOnlyContainsCharactersFrom(final String value, final List<Character> allowedChars) {
        for (int i = 0; i < value.length(); i++) {
            assertTrue(allowedChars.contains(value.charAt(i)));
        }
    }

    @Test
    void testHebrewTest() throws UnsupportedEncodingException {
        assertRoundTripConversion(HEBREW, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                "\u05d0",
                "\u05e2",
                "\u05d0\u05dc\u05e3_\u05d0\u05d5\u05d4\u05d1\u05dc_\u05d1\u05d9\u05ea_\u05d6\u05d4_\u05d1\u05d9\u05ea_"
                        + "\u05d2\u05d9\u05de\u05dc_\u05d6\u05d4_\u05db\u05de\u05dc_\u05d2\u05d3\u05d5\u05dc");
        assertRoundTripConversion(HEBREW, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                "\u05d0",
                "\u05e2",
                "\u05d0\u05dc\u05e3_\u05d0\u05d5\u05d4\u05d1\u05dc_\u05d1\u05d9\u05ea_\u05d6\u05d4_\u05d1\u05d9\u05ea_"
                        + "\u05d2\u05d9\u05de\u05dc_\u05d6\u05d4_\u05db\u05de\u05dc_\u05d2\u05d3\u05d5\u05dc");
        assertRoundTripConversion(NUMBERS, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                "123456789",
                "1",
                "5");
        assertRoundTripConversion(LOWER_CASE_ENGLISH, HEBREW, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                "this is a test");
    }
}
