package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testDoNotEncodeTest {

    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    private static final Character[] LOWER_CASE_ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', ' '
    };

    private static final Character[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    @Test
    void testDoNotEncodeTest() throws UnsupportedEncodingException {
        verifyRoundTripWithUnencodedCharacters(
            ENGLISH_AND_NUMBERS,
            LOWER_CASE_ENGLISH_AND_NUMBERS,
            LOWER_CASE_ENGLISH,
            "1",
            "456",
            "abc",
            "ABC",
            "this will not be converted but THIS WILL");

        verifyRoundTripWithUnencodedCharacters(
            ENGLISH_AND_NUMBERS,
            LOWER_CASE_ENGLISH_AND_NUMBERS,
            NUMBERS,
            "1",
            "456",
            "abc",
            "ABC",
            "this will be converted but 12345 and this will be");
    }

    private void verifyRoundTripWithUnencodedCharacters(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... strings) throws UnsupportedEncodingException {
        final AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
            originalChars,
            encodingChars,
            doNotEncodeChars);
        final AlphabetConverter reconstructedConverter = AlphabetConverter.createConverterFromMap(
            converter.getOriginalToEncoded());

        assertEquals(converter, reconstructedConverter);
        assertEquals(converter.hashCode(), reconstructedConverter.hashCode());
        assertEquals(converter.toString(), reconstructedConverter.toString());
        assertNull(converter.encode(null));
        assertEquals("", converter.encode(""));

        for (final String stringToConvert : strings) {
            final String encoded = converter.encode(stringToConvert);
            assertEncodedStringUsesOnlyEncodingCharacters(encodingChars, encoded);
            assertDecodingRestoresOriginalString(converter, originalChars, stringToConvert, encoded);
        }
    }

    private void assertEncodedStringUsesOnlyEncodingCharacters(
            final Character[] encodingChars,
            final String encoded) {
        final List<Character> validEncodingCharacters = Arrays.asList(encodingChars);

        for (int i = 0; i < encoded.length(); i++) {
            assertTrue(validEncodingCharacters.contains(encoded.charAt(i)));
        }
    }

    private void assertDecodingRestoresOriginalString(
            final AlphabetConverter converter,
            final Character[] originalChars,
            final String stringToConvert,
            final String encoded) throws UnsupportedEncodingException {
        final String decoded = converter.decode(encoded);
        final List<Character> validOriginalCharacters = Arrays.asList(originalChars);

        for (int i = 0; i < decoded.length(); i++) {
            assertTrue(validOriginalCharacters.contains(decoded.charAt(i)));
        }
        assertEquals(
            stringToConvert,
            decoded,
            () -> "Encoded '" + stringToConvert + "' into '" + encoded
                + "', but decoded into '" + decoded + "'");
    }
}
