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

    // Binary alphabet: only '0' and '1' are valid source characters
    private static final Character[] BINARY = { '0', '1' };

    // Decimal digits used as the encoding alphabet
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * Creates a converter, verifies it round-trips correctly for null and empty
     * inputs, and then encodes each trial string, asserting that every character
     * in the encoded output belongs to the encoding alphabet and that decoding
     * the encoded result recovers the original string.
     */
    private void test(
            final Character[] originalChars,
            final Character[] encodingChars,
            final Character[] doNotEncodeChars,
            final String... strings) throws UnsupportedEncodingException {

        final AlphabetConverter ac =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // A converter rebuilt from the encoding map must be equal to the original
        final AlphabetConverter reconstructed =
                AlphabetConverter.createConverterFromMap(ac.getOriginalToEncoded());
        assertEquals(ac, reconstructed);
        assertEquals(ac.hashCode(), reconstructed.hashCode());
        assertEquals(ac.toString(), reconstructed.toString());

        // Boundary inputs
        assertNull(ac.encode(null));
        assertEquals("", ac.encode(""));

        // Verify each trial string encodes and decodes correctly
        final List<Character> validEncodingChars = Arrays.asList(encodingChars);
        final List<Character> validOriginalChars  = Arrays.asList(originalChars);

        for (final String s : strings) {
            final String encoded = ac.encode(s);

            // Every character in the encoded output must be from the encoding alphabet
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(validEncodingChars.contains(encoded.charAt(i)));
            }

            final String decoded = ac.decode(encoded);

            // Every character in the decoded output must be from the original alphabet
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(validOriginalChars.contains(decoded.charAt(i)));
            }

            assertEquals(s, decoded,
                    () -> "Encoded '" + s + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    /**
     * Encoding a character that is outside the source alphabet ('3' is not in
     * BINARY) must throw UnsupportedEncodingException with a descriptive message.
     */
    @Test
    void testEncodeFailureTest() throws Exception {
        // '3' is not a valid BINARY source character, so encoding must fail
        UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> test(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "3"));

        assertEquals("Couldn't find encoding for '3' in 3", exception.getMessage());
    }
}
