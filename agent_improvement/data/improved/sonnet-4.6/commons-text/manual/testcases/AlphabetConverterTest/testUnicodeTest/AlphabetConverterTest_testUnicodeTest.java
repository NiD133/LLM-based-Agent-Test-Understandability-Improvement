package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link AlphabetConverter#createConverter(Integer[], Integer[], Integer[])}
 * with a source alphabet that includes supplementary (non-BMP) Unicode code points.
 */
public class AlphabetConverterTest_testUnicodeTest {

    /**
     * Source alphabet: a mix of CJK Unified Ideographs (35201–36302), a handful of
     * code points in the 1001–1005 range, basic Latin letters a–n (97–110), and a
     * space (32). The converter must map every one of these to the encoding alphabet.
     */
    private static final Integer[] UNICODE = {
        32,                                  // space
        35395, 35397, 36302, 36291,          // CJK: 蚃 蚅 跎 跃
        35203, 35201, 35215, 35219, 35268,   // CJK: 蚃-range characters
        97, 98, 99, 100, 101, 102, 103,      // a–g
        104, 105, 106, 107, 108, 109, 110,   // h–n
        1001, 1002, 1003, 1004, 1005         // supplementary range sample
    };

    /**
     * Encoding alphabet: code points for space (32) and lower-case letters a–z
     * (97–122). This alphabet is smaller than {@link #UNICODE}, so the converter
     * must use 2-character encoded sequences for most source characters.
     */
    private static final Integer[] LOWER_CASE_ENGLISH_CODEPOINTS = {
        32,                                              // space
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106,  // a–j
        107, 108, 109, 110, 111, 112, 113, 114, 115,    // k–s
        116, 117, 118, 119, 120, 121, 122               // t–z
    };

    /**
     * Characters that must pass through the converter unchanged (i.e. encoded as
     * themselves). Space and 'a'–'c' appear in both the source and encoding
     * alphabets, satisfying the precondition for do-not-encode entries.
     */
    private static final Integer[] DO_NOT_ENCODE_CODEPOINTS = {
        32,       // space
        97, 98, 99 // a, b, c
    };

    /**
     * Verifies that a converter built from Unicode code-point arrays correctly
     * encodes and then decodes a string that contains CJK characters, basic Latin
     * letters, and spaces, producing the original string after a round-trip.
     *
     * <p>Expected behaviour:
     * <ul>
     *   <li>Encoded character length is 2, because the encoding alphabet (27
     *       characters) is smaller than the source alphabet (29 characters).</li>
     *   <li>Do-not-encode characters (space, 'a', 'b', 'c') appear verbatim in
     *       the encoded output.</li>
     *   <li>decode(encode(original)) == original for all inputs.</li>
     * </ul>
     */
    @Test
    void testUnicodeTest() throws UnsupportedEncodingException {
        final AlphabetConverter ac =
                AlphabetConverter.createConverter(UNICODE, LOWER_CASE_ENGLISH_CODEPOINTS, DO_NOT_ENCODE_CODEPOINTS);

        // The encoding alphabet is smaller than the source alphabet, so the
        // converter must use 2-character sequences to cover every source character.
        assertEquals(2, ac.getEncodedCharLength());

        // A representative string: CJK characters, spaces, and pass-through letters.
        final String original = "詃詅 跎 ab 跃 c 覃";
        final String encoded = ac.encode(original);
        final String decoded = ac.decode(encoded);

        assertEquals(original, decoded,
                () -> "Encoded '" + original + "' into '" + encoded
                        + "', but decoded into '" + decoded + "'");
    }
}
