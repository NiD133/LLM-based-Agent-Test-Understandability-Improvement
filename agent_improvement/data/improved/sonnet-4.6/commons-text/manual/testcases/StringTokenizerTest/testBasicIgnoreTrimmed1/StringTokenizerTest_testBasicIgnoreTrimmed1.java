package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed1 {

    @Test
    void testBasicIgnoreTrimmed1() {
        // Tokenize "a: bIGNOREc : " on ':', trimming whitespace and ignoring the substring "IGNORE".
        // Empty tokens are kept but returned as null.
        final String input = "a: bIGNOREc : ";
        final StringTokenizer tok = new StringTokenizer(input, ':');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tok.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());   // first token: "a" (trimmed)
        assertEquals("bc", tok.next());  // second token: "bIGNOREc" → "bc" after ignore+trim
        assertNull(tok.next());          // third token: " " (whitespace only) → null after trim
        assertFalse(tok.hasNext());
    }
}
