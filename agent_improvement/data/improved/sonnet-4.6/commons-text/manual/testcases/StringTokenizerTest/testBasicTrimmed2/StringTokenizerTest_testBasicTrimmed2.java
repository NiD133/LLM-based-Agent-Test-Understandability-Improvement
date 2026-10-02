package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicTrimmed2 {

    @Test
    void testBasicTrimmed2() {
        // Input "a:  b  :" has three colon-delimited fields: "a", "  b  ", and "" (empty after trailing colon).
        // The two-space trimmer strips the surrounding spaces from each token.
        // ignoreEmptyTokens=false keeps the trailing empty field; emptyTokenAsNull=true returns it as null.
        final String input = "a:  b  :";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.stringMatcher("  "));
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
