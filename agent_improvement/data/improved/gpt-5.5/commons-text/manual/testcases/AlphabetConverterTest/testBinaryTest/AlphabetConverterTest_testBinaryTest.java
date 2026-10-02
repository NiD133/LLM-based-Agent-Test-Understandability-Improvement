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

    private static final Character[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final Character[] BINARY = { '0', '1' };

    private void assertRoundTrips(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... strings) throws UnsupportedEncodingException {

        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);
        final AlphabetConverter reconstructedConverter =
                AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());

        assertEquals(converter, reconstructedConverter);
        assertEquals(converter.hashCode(), reconstructedConverter.hashCode());
        assertEquals(converter.toString(), reconstructedConverter.toString());

        assertNull(converter.encode(null));
        assertEquals("", converter.encode(""));

        final List<Character> encodingAlphabet = Arrays.asList(encodingChars);
        final List<Character> originalAlphabet = Arrays.asList(originalChars);

        for (final String original : strings) {
            final String encoded = converter.encode(original);

            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(encodingAlphabet.contains(encoded.charAt(i)));
            }

            final String decoded = converter.decode(encoded);

            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalAlphabet.contains(decoded.charAt(i)));
            }

            assertEquals(original, decoded,
                    () -> "Encoded '" + original + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testBinaryTest() throws UnsupportedEncodingException {
        assertRoundTrips(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "0", "1", "10", "11");
        assertRoundTrips(NUMBERS, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "12345", "0");
        assertRoundTrips(LOWER_CASE_ENGLISH, BINARY, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "abc", "a");
    }
}
