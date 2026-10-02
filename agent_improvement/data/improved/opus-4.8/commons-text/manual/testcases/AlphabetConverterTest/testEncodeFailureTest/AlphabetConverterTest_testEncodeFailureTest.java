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

    /** Original alphabet: only the binary digits 0 and 1. */
    private static final Character[] BINARY = { '0', '1' };

    /** Encoding alphabet: the decimal digits 0 through 9. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * Builds a converter from the given alphabets, verifies it survives a
     * round trip through its map representation, and then encodes/decodes each
     * trial string, checking the result stays within the expected alphabets.
     *
     * <p>If a trial string contains a character that is not part of the
     * original alphabet, {@link AlphabetConverter#encode(String)} throws an
     * {@link UnsupportedEncodingException} before any decoding happens.</p>
     */
    private void encodeAndDecodeRoundTrip(final Character[] originalChars,
                                          final Character[] encodingChars,
                                          final Character[] doNotEncodeChars,
                                          final String... strings) throws UnsupportedEncodingException {
        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);

        // The converter must be reconstructable from its original-to-encoded map.
        final AlphabetConverter reconstructed =
                AlphabetConverter.createConverterFromMap(converter.getOriginalToEncoded());
        assertEquals(converter, reconstructed);
        assertEquals(converter.hashCode(), reconstructed.hashCode());
        assertEquals(converter.toString(), reconstructed.toString());

        // A null input encodes to null and an empty input encodes to empty.
        assertNull(converter.encode(null));
        assertEquals("", converter.encode(""));

        final List<Character> allowedEncodingChars = Arrays.asList(encodingChars);
        final List<Character> allowedOriginalChars = Arrays.asList(originalChars);
        for (final String original : strings) {
            final String encoded = converter.encode(original);

            // The encoded form must use only characters from the encoding alphabet.
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(allowedEncodingChars.contains(encoded.charAt(i)));
            }

            final String decoded = converter.decode(encoded);

            // Decoding must yield only characters from the original alphabet.
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(allowedOriginalChars.contains(decoded.charAt(i)));
            }

            assertEquals(original, decoded,
                    () -> "Encoded '" + original + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testEncodeFailureTest() {
        // The original alphabet is BINARY (only '0' and '1'), so encoding the
        // string "3" cannot succeed: '3' has no mapping in the original alphabet.
        final UnsupportedEncodingException thrown = assertThrows(
                UnsupportedEncodingException.class,
                () -> encodeAndDecodeRoundTrip(BINARY, NUMBERS, ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, "3"));

        assertEquals("Couldn't find encoding for '3' in 3", thrown.getMessage());
    }
}
