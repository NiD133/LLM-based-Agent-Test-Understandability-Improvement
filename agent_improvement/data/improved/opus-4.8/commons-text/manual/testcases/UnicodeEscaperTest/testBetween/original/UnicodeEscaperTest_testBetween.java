package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnicodeEscaperTest_testBetween {

    @Test
    void testBetween() {
        final UnicodeEscaper escaper = UnicodeEscaper.between('F', 'L');
        final String input = "ADFGZ";
        final String result = escaper.translate(input);
        assertEquals("AD\\u0046\\u0047Z", result, "Failed to escape Unicode characters via the between method");
    }
}
