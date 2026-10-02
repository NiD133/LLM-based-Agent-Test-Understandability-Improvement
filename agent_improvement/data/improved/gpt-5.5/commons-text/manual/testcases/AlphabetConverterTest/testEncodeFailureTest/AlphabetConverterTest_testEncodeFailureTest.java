package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEncodeFailureTest {

    private static final Character[] BINARY_ALPHABET = { '0', '1' };
    private static final Character[] DECIMAL_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private void assertRoundTripForSupportedInputs(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... inputs) throws UnsupportedEncodingException {
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

        for (final String input : inputs) {
            final String encoded = converter.encode(input);
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(encodingAlphabet.contains(encoded.charAt(i)));
            }

            final String decoded = converter.decode(encoded);
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalAlphabet.contains(decoded.charAt(i)));
            }

            assertEquals(input, decoded,
                    () -> "Encoded '" + input + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testEncodeFailureTest() {
        final UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> assertRoundTripForSupportedInputs(
                        BINARY_ALPHABET,
                        DECIMAL_DIGITS,
                        ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
                        "3"));

        assertEquals("Couldn't find encoding for '3' in 3", exception.getMessage());
    }
}
