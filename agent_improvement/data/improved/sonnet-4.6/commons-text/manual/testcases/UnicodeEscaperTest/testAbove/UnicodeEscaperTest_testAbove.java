package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testAbove {

    /**
     * Verifies that UnicodeEscaper.above(codePoint) escapes only characters whose
     * code point is strictly greater than the given boundary (exclusive).
     *
     * Boundary: 'F' (code point 70 / 0x0046)
     *   - 'A' (0x41), 'D' (0x44), 'F' (0x46) are at or below the boundary → pass through unchanged
     *   - 'G' (0x47) and 'Z' (0x5A) are above the boundary, so they are escaped as \\uXXXX
     */
    @Test
    void testAbove() {
        // Escaper that unicode-escapes any character with code point > 'F' (0x46)
        final UnicodeEscaper escaper = UnicodeEscaper.above('F');

        final String input = "ADFGZ";

        final String result = escaper.translate(input);

        // 'A', 'D', 'F' are left as-is; 'G' → G, 'Z' → Z
        assertEquals("ADF\\u0047\\u005A", result,
                "Failed to escape Unicode characters via the above method");
    }
}
