package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testInitialsSurrogatePairs {

    // Supplementary Unicode characters from the Old Italic block (U+10300–U+10303),
    // each encoded as a UTF-16 surrogate pair (two Java chars).
    private static final String SURROGATE_CHAR_A = "𐌀"; // U+10300
    private static final String SURROGATE_CHAR_B = "𐌁"; // U+10301
    private static final String SURROGATE_CHAR_C = "𐌂"; // U+10302
    private static final String SURROGATE_CHAR_D = "𐌃"; // U+10303

    // Supplementary characters used as word delimiters in UTF-32 delimiter tests.
    private static final String SURROGATE_DELIM_1 = "𐌔"; // U+10314
    private static final String SURROGATE_DELIM_2 = "𐌘"; // U+10318

    // Expected result for all test cases: the first char of each word.
    private static final String EXPECTED_INITIALS = SURROGATE_CHAR_A + SURROGATE_CHAR_C;

    @Test
    void testInitialsSurrogatePairs() {
        // Space as default delimiter: initials() with no delimiter arg
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + SURROGATE_CHAR_B + " " + SURROGATE_CHAR_C + SURROGATE_CHAR_D));

        // Space as default delimiter: explicit null means "use whitespace"
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + SURROGATE_CHAR_B + " " + SURROGATE_CHAR_C + SURROGATE_CHAR_D, null));

        // Trailing space is ignored; only non-empty words contribute an initial
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + " " + SURROGATE_CHAR_C + " ", null));

        // BMP single-char delimiter '.'
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + SURROGATE_CHAR_B + "." + SURROGATE_CHAR_C + SURROGATE_CHAR_D,
                        new char[]{ '.' }));

        // BMP single-char delimiter 'A'
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + SURROGATE_CHAR_B + "A" + SURROGATE_CHAR_C + SURROGATE_CHAR_D,
                        new char[]{ 'A' }));

        // Supplementary delimiter passed as its two surrogate chars (UTF-32 split across the char[] array)
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + SURROGATE_CHAR_B + SURROGATE_DELIM_1 + SURROGATE_CHAR_C + SURROGATE_CHAR_D,
                        new char[]{ '\uD800', '\uDF14' }));

        // Two supplementary delimiters: SURROGATE_DELIM_1 followed by SURROGATE_DELIM_2
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(SURROGATE_CHAR_A + SURROGATE_CHAR_B + SURROGATE_DELIM_1 + SURROGATE_DELIM_2 + SURROGATE_CHAR_C + SURROGATE_CHAR_D,
                        new char[]{ '\uD800', '\uDF14', '\uD800', '\uDF18' }));
    }
}
