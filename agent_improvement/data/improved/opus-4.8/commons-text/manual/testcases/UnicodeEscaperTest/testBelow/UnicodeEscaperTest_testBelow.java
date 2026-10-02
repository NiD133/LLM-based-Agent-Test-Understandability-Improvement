package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link UnicodeEscaper#below(int)}, which escapes every character
 * whose code point is strictly less than the given boundary.
 */
public class UnicodeEscaperTest_testBelow {

    @Test
    void escapesOnlyCharactersBelowTheBoundary() {
        // Boundary 'F' (U+0046): characters with a lower code point are escaped,
        // 'F' itself and anything higher is left untouched.
        final UnicodeEscaper escaper = UnicodeEscaper.below('F');

        final String input = "ADFGZ";
        final String escaped = escaper.translate(input);

        // 'A' (U+0041) and 'D' (U+0044) are below 'F', so they are escaped;
        // 'F', 'G' and 'Z' are at or above the boundary and stay as-is.
        final String expected = "\\u0041\\u0044FGZ";
        assertEquals(expected, escaped,
                "Characters below the boundary should be Unicode-escaped, the rest left unchanged");
    }
}
