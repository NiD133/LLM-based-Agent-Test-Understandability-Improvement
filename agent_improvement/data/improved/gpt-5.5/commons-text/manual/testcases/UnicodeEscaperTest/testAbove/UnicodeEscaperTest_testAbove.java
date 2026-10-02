package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testAbove {

    private static final char ESCAPE_ABOVE = 'F';
    private static final String INPUT_WITH_BOUNDARY_AND_ABOVE_BOUNDARY_CHARACTERS = "ADFGZ";
    private static final String EXPECTED_ONLY_CHARACTERS_ABOVE_BOUNDARY_ESCAPED = "ADF\\u0047\\u005A";

    @Test
    void testAbove() {
        final UnicodeEscaper escaper = UnicodeEscaper.above(ESCAPE_ABOVE);

        final String result = escaper.translate(INPUT_WITH_BOUNDARY_AND_ABOVE_BOUNDARY_CHARACTERS);

        assertEquals(
                EXPECTED_ONLY_CHARACTERS_ABOVE_BOUNDARY_ESCAPED,
                result,
                "Failed to escape Unicode characters via the above method");
    }
}
