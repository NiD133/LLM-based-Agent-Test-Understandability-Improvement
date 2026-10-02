package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testAbove {

    @Test
    void testAbove() {
        final UnicodeEscaper escaper = UnicodeEscaper.above('F');
        final String input = "ADFGZ";
        final String result = escaper.translate(input);
        assertEquals("ADF\\u0047\\u005A", result, "Failed to escape Unicode characters via the above method");
    }
}
