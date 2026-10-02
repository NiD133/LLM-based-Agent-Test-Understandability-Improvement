package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicDelim2 {

    @Test
    void testBasicDelim2() {
        // Delimiter is comma, but input contains only colons — entire input is one token
        final String input = "a:b:c";
        final StringTokenizer tok = new StringTokenizer(input, ',');
        assertEquals("a:b:c", tok.next());
        assertFalse(tok.hasNext());
    }
}
