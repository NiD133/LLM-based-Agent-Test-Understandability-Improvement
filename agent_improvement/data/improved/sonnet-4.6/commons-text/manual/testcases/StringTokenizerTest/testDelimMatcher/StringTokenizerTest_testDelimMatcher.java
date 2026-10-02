package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testDelimMatcher {

    @Test
    void testDelimMatcher() {
        // Tokenize a string that uses two different delimiter characters: '/' and '\'
        final String input = "a/b\\c";
        final StringMatcher delimMatcher = StringMatcherFactory.INSTANCE.charSetMatcher('/', '\\');
        final StringTokenizer tok = new StringTokenizer(input, delimMatcher);

        // Each segment between delimiters should be returned as a separate token
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
