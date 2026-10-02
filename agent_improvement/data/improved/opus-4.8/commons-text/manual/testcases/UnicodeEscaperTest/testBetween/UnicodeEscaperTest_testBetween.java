package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link UnicodeEscaper#between(int, int)}, which escapes only the
 * characters whose code points fall inside the given inclusive range.
 */
public class UnicodeEscaperTest_testBetween {

    @Test
    void escapesOnlyCharactersWithinInclusiveRange() {
        // Escape every character between 'F' (0x46) and 'L' (0x4C), inclusive.
        final UnicodeEscaper escaper = UnicodeEscaper.between('F', 'L');

        // 'A' and 'D' are below the range and 'Z' is above it, so they stay as-is;
        // 'F' and 'G' fall inside the range and become their \\uXXXX escapes.
        final String input = "ADFGZ";
        final String escaped = escaper.translate(input);

        final String expected = "AD\\u0046\\u0047Z";
        assertEquals(expected, escaped,
                "Only characters within the inclusive [F, L] range should be Unicode-escaped");
    }
}
