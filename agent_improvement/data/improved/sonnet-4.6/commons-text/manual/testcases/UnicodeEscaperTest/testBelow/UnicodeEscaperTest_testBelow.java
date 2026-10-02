package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testBelow {

    /**
     * UnicodeEscaper.below(threshold) escapes every code point that is
     * strictly less than {@code threshold} (exclusive upper bound).
     *
     * Input "ADFGZ" covers three categories:
     *   'A' (U+0041, code point 65) — below 'F' (70) → escaped to A
     *   'D' (U+0044, code point 68) — below 'F' (70) → escaped to D
     *   'F' (U+0046, code point 70) — equal to threshold → NOT escaped
     *   'G' (U+0047, code point 71) — above threshold  → NOT escaped
     *   'Z' (U+005A, code point 90) — above threshold  → NOT escaped
     */
    @Test
    void testBelow() {
        // 'F' is the exclusive threshold: characters with code point < 'F' are escaped
        final char escapeThreshold = 'F';
        final UnicodeEscaper escaper = UnicodeEscaper.below(escapeThreshold);

        // Input deliberately includes characters below, at, and above the threshold
        final String input = "ADFGZ";

        // Only 'A' and 'D' (code points below 'F') are Unicode-escaped; the rest pass through unchanged
        final String escapedA = "\\u0041"; // 'A' is below threshold
        final String escapedD = "\\u0044"; // 'D' is below threshold
        final String passThrough = "FGZ";  // 'F' (at threshold), 'G', 'Z' are not escaped
        final String expectedResult = escapedA + escapedD + passThrough;

        final String result = escaper.translate(input);

        assertEquals(expectedResult, result,
                "Characters strictly below 'F' should be Unicode-escaped; others should pass through unchanged");
    }
}
