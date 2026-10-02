package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted7 {

    @Test
    void testBasicQuoted7() {
        // Input has a colon-delimited token that is double-quoted and contains an apostrophe;
        // the quote matcher should strip the surrounding quotes and preserve the inner content.
        final String input = "a:\"There's a reason here\":b";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("a", tokenizer.next());
        assertEquals("There's a reason here", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
