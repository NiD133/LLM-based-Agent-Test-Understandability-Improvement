package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testBetween {

    private static final char ESCAPE_RANGE_START = 'F';
    private static final char ESCAPE_RANGE_END = 'L';
    private static final String INPUT_WITH_CHARACTERS_INSIDE_AND_OUTSIDE_RANGE = "ADFGZ";
    private static final String EXPECTED_ONLY_RANGE_CHARACTERS_ESCAPED = "AD\\u0046\\u0047Z";

    @Test
    void testBetween() {
        final UnicodeEscaper escaper = UnicodeEscaper.between(ESCAPE_RANGE_START, ESCAPE_RANGE_END);
        final String actualEscapedText = escaper.translate(INPUT_WITH_CHARACTERS_INSIDE_AND_OUTSIDE_RANGE);

        assertEquals(
                EXPECTED_ONLY_RANGE_CHARACTERS_ESCAPED,
                actualEscapedText,
                "Failed to escape Unicode characters via the between method");
    }
}
