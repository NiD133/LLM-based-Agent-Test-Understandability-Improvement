package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testBelow {

    private static final char ESCAPE_BELOW_BOUNDARY = 'F';
    private static final String INPUT_WITH_CHARACTERS_BELOW_AT_AND_ABOVE_BOUNDARY = "ADFGZ";
    private static final String EXPECTED_ESCAPED_CHARACTERS_BELOW_BOUNDARY = "\\u0041\\u0044FGZ";

    @Test
    void testBelow() {
        final UnicodeEscaper escaper = UnicodeEscaper.below(ESCAPE_BELOW_BOUNDARY);

        final String result = escaper.translate(INPUT_WITH_CHARACTERS_BELOW_AT_AND_ABOVE_BOUNDARY);

        assertEquals(
            EXPECTED_ESCAPED_CHARACTERS_BELOW_BOUNDARY,
            result,
            "Failed to escape Unicode characters via the below method");
    }
}
