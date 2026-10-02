package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#createConverter(Integer[], Integer[], Integer[])}
 * works when the source alphabet is expressed as Unicode code points (rather than
 * {@code char}s), and that encoding followed by decoding round-trips back to the
 * original text.
 */
public class AlphabetConverterTest_testUnicodeTest {

    /**
     * Source alphabet as code points. The first entry (32) is a space, followed by
     * several CJK code points, then the ASCII letters {@code a}-{@code n}, then a few
     * higher code points. This alphabet (29 symbols) is larger than the 27-symbol
     * encoding alphabet, so each source symbol must be encoded with more than one
     * encoding character.
     */
    private static final Integer[] SOURCE_ALPHABET_CODEPOINTS = {
            32, 35395, 35397, 36302, 36291, 35203, 35201, 35215, 35219, 35268,
            97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
            1001, 1002, 1003, 1004, 1005 };

    /**
     * Encoding alphabet as code points: a space (32) followed by the lower-case
     * ASCII letters {@code a}-{@code z}.
     */
    private static final Integer[] ENCODING_ALPHABET_CODEPOINTS = {
            32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
            110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };

    /** Code points left unencoded (present in both alphabets): space, {@code a}, {@code b}, {@code c}. */
    private static final Integer[] DO_NOT_ENCODE_CODEPOINTS = { 32, 97, 98, 99 };

    /**
     * Because the source alphabet is larger than the encoding alphabet, the converter
     * needs two encoding characters to represent each source symbol.
     */
    private static final int EXPECTED_ENCODED_CHAR_LENGTH = 2;

    /**
     * Builds a converter from code points and checks that encoding then decoding a
     * mixed string (CJK characters, spaces, and ASCII letters) returns the original.
     */
    @Test
    void testUnicodeTest() throws UnsupportedEncodingException {
        final AlphabetConverter converter = AlphabetConverter.createConverter(
                SOURCE_ALPHABET_CODEPOINTS,
                ENCODING_ALPHABET_CODEPOINTS,
                DO_NOT_ENCODE_CODEPOINTS);

        assertEquals(EXPECTED_ENCODED_CHAR_LENGTH, converter.getEncodedCharLength());

        final String original = "\u8a43\u8a45 \u8dce ab \u8dc3 c \u8983";
        final String encoded = converter.encode(original);
        final String decoded = converter.decode(encoded);

        assertEquals(original, decoded,
                () -> "Encoded '" + original + "' into '" + encoded
                        + "', but decoded into '" + decoded + "'");
    }
}
