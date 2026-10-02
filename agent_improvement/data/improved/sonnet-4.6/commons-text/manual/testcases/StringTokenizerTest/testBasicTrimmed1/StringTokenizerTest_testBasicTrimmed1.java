package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicTrimmed1 {

    /**
     * Verifies that a colon-delimited string with surrounding whitespace is tokenized correctly:
     * whitespace is trimmed from each token, empty tokens are kept (not ignored) but returned as null.
     *
     * Input "a: b :  " splits on ':' into three raw parts: "a", " b ", "  ".
     * After trimming: "a", "b", "" — the trailing empty string becomes null due to emptyTokenAsNull=true.
     */
    @Test
    void testBasicTrimmed1() {
        final String input = "a: b :  ";
        final StringTokenizer tok = new StringTokenizer(input, ':');
        tok.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertNull(tok.next());
        assertFalse(tok.hasNext());
    }
}
