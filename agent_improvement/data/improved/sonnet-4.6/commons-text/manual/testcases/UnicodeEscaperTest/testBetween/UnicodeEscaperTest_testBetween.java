package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link UnicodeEscaper#between(int, int)}, which creates an escaper
 * that converts characters in the inclusive range [codePointLow, codePointHigh]
 * to their Unicode escape sequences (e.g. {@code F}), leaving all other
 * characters unchanged.
 */
public class UnicodeEscaperTest_testBetween {

    // Inclusive boundary characters for the escape range.
    private static final char RANGE_START = 'F'; // U+0046
    private static final char RANGE_END   = 'L'; // U+004C

    @Test
    void testBetween() {
        // Create an escaper that targets only characters from 'F' (U+0046) through 'L' (U+004C), inclusive.
        final UnicodeEscaper escaper = UnicodeEscaper.between(RANGE_START, RANGE_END);

        // Input contains characters both inside and outside the escape range:
        //   'A' (U+0041) — below range, left as-is
        //   'D' (U+0044) — below range, left as-is
        //   'F' (U+0046) — at range start, escaped to F
        //   'G' (U+0047) — inside range, escaped to G
        //   'Z' (U+005A) — above range, left as-is
        final String input = "ADFGZ";

        final String actual = escaper.translate(input);

        // 'A' and 'D' pass through unchanged; 'F' and 'G' are escaped; 'Z' passes through.
        assertEquals("AD\\u0046\\u0047Z", actual,
                "Characters within [F, L] should be Unicode-escaped; characters outside should be unchanged");
    }
}
