package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testBelow {

    @Test
    void testBelow() {
        final UnicodeEscaper escaper = UnicodeEscaper.below('F');
        final String input = "ADFGZ";
        final String result = escaper.translate(input);
        assertEquals("\\u0041\\u0044FGZ", result, "Failed to escape Unicode characters via the below method");
    }
}
