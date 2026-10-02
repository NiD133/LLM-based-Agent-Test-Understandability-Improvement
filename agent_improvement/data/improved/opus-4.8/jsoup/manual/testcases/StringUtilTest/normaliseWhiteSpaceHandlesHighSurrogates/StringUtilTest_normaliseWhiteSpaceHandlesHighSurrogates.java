package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_normaliseWhiteSpaceHandlesHighSurrogates {

    /**
     * Regression test for issue #1540: whitespace normalisation must correctly handle text that contains
     * supplementary (high-surrogate) characters. Because such a character is encoded as a surrogate pair
     * (two {@code char}s), normalisation must iterate by Unicode code point rather than by {@code char},
     * otherwise it could split the pair or mishandle the surrounding whitespace.
     */
    @Test
    public void normaliseWhiteSpaceHandlesHighSurrogates() {
        // The input is, in order: the supplementary code point U+2A6B2 (the surrogate pair 𪚲),
        // the CJK character U+304B, the combining mark U+309A, then two spaces and the digit 1.
        // Unicode escapes are used so the bytes are independent of the source file encoding.
        String textWithSurrogateAndDoubleSpace = "𪚲か゚  1";
        // After normalisation the two spaces collapse into a single space; every other character is preserved.
        String textWithSingleSpace = "𪚲か゚ 1";

        // normaliseWhitespace collapses consecutive whitespace down to a single space.
        assertEquals(textWithSingleSpace, normaliseWhitespace(textWithSurrogateAndDoubleSpace));

        // Jsoup.parse(...).text() applies the same whitespace normalisation when extracting text.
        String parsedText = Jsoup.parse(textWithSurrogateAndDoubleSpace).text();
        assertEquals(textWithSingleSpace, parsedText);
    }
}
