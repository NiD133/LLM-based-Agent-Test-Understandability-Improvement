package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed2 {

    /**
     * Verifies that a tokenizer combining an "ignored" matcher and a trimmer matcher
     * strips the ignored substring, trims surrounding whitespace, and finally returns
     * a trailing empty token as {@code null}.
     */
    @Test
    void testBasicIgnoreTrimmed2() {
        // Delimiter is ':'. The literal "IGNORE" is removed wherever it occurs, and the
        // remaining whitespace around each token is trimmed.
        final String input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        // "IGNOREaIGNORE" -> ignore "IGNORE" -> "a"
        assertEquals("a", tokenizer.next());
        // " IGNORE bIGNOREc " -> ignore "IGNORE", trim whitespace -> "bc"
        assertEquals("bc", tokenizer.next());
        // " IGNORE " -> ignore "IGNORE", trim -> empty -> reported as null
        assertNull(tokenizer.next());
        // No further tokens remain.
        assertFalse(tokenizer.hasNext());
    }
}
