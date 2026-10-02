package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testDelimMatcher {

    @Test
    void testDelimMatcher() {
        final String input = "a/b\\c";
        final StringMatcher delimMatcher = StringMatcherFactory.INSTANCE.charSetMatcher('/', '\\');
        final StringTokenizer tokenizer = new StringTokenizer(input, delimMatcher);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
