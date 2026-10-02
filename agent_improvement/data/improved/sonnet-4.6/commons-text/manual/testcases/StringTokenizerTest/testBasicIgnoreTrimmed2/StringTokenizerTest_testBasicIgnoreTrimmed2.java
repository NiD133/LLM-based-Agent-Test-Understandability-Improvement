package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed2 {

    @Test
    void testBasicIgnoreTrimmed2() {
        // Tokenizes by ':', strips occurrences of "IGNORE", and trims surrounding whitespace.
        // Empty tokens are kept (ignoreEmptyTokens=false) and returned as null (emptyTokenAsNull=true).
        final String input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("bc", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
