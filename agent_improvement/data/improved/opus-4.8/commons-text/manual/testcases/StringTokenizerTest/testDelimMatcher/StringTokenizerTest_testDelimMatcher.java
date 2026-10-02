package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link StringTokenizer} can split input using a custom
 * delimiter matcher that recognizes more than one delimiter character.
 */
public class StringTokenizerTest_testDelimMatcher {

    @Test
    void testDelimMatcher() {
        // "a", "b" and "c" are separated by two different delimiters: '/' and '\'.
        final String input = "a/b\\c";
        final StringMatcher delimiterMatcher =
                StringMatcherFactory.INSTANCE.charSetMatcher('/', '\\');

        final StringTokenizer tokenizer = new StringTokenizer(input, delimiterMatcher);

        // Both delimiter characters should be treated as token separators.
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
