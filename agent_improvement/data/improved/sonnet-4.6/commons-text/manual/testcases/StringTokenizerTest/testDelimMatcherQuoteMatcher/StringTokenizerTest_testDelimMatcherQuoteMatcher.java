package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testDelimMatcherQuoteMatcher {

    @Test
    void testDelimMatcherQuoteMatcher() {
        // Verify that a tokenizer with a custom delimiter (';') and quote character ('`')
        // correctly strips the quotes and splits on the delimiter.
        final String input = "`a`;`b`;`c`";
        final StringMatcher delimMatcher = StringMatcherFactory.INSTANCE.charSetMatcher(';');
        final StringMatcher quoteMatcher = StringMatcherFactory.INSTANCE.charSetMatcher('`');

        final StringTokenizer tok = new StringTokenizer(input, delimMatcher, quoteMatcher);

        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
