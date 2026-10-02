package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} treats double-quoted text as a single
 * token, preserving any delimiter-like characters (here, an apostrophe) found
 * inside the quotes.
 */
public class StringTokenizerTest_testBasicQuoted7 {

    @Test
    void quotedTokenKeepsInnerCharactersAndIsReturnedWhole() {
        // Input split on ':' where the middle field is wrapped in double quotes.
        final String input = "a:\"There's a reason here\":b";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.quoteMatcher());

        assertEquals("a", tokenizer.next());
        // The quoted field stays intact: quotes are stripped, apostrophe kept.
        assertEquals("There's a reason here", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
