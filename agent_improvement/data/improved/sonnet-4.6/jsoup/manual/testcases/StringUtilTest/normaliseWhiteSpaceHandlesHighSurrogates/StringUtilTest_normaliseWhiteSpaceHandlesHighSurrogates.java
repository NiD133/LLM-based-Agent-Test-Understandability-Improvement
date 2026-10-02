package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_normaliseWhiteSpaceHandlesHighSurrogates {

    // U+2A6B2 (a CJK unified ideograph extension B character) encoded as a UTF-16 surrogate pair.
    // Using a supplementary character at the start ensures the whitespace normaliser advances
    // by two Java chars (Character.charCount > 1) and does not misinterpret either surrogate
    // half as a whitespace or invisible character.
    private static final String CJK_SUPPLEMENTARY_CHAR = "𪚲";

    // U+304B HIRAGANA LETTER KA followed by U+309A COMBINING KATAKANA-HIRAGANA SEMI-VOICED
    // SOUND MARK — a multi-code-unit sequence that must survive whitespace normalisation intact.
    private static final String HIRAGANA_WITH_COMBINING = "か゚";

    /**
     * Input has two consecutive spaces; after normalisation exactly one space should remain.
     * The surrounding supplementary (surrogate-pair) and combining characters must be preserved.
     */
    @Test
    public void normaliseWhiteSpaceHandlesHighSurrogates() {
        String inputWithDoubleSpace = CJK_SUPPLEMENTARY_CHAR + HIRAGANA_WITH_COMBINING + "  1";
        String expectedSingleSpace  = CJK_SUPPLEMENTARY_CHAR + HIRAGANA_WITH_COMBINING + " 1";

        // Verify the utility method collapses the double space directly.
        assertEquals(expectedSingleSpace, normaliseWhitespace(inputWithDoubleSpace),
            "normaliseWhitespace should collapse consecutive spaces into one, " +
            "even when the string begins with a surrogate-pair character");

        // Verify the same behaviour is preserved end-to-end through jsoup's HTML parser.
        String parsedText = Jsoup.parse(inputWithDoubleSpace).text();
        assertEquals(expectedSingleSpace, parsedText,
            "Jsoup.parse().text() should produce the same normalised whitespace result");
    }
}
