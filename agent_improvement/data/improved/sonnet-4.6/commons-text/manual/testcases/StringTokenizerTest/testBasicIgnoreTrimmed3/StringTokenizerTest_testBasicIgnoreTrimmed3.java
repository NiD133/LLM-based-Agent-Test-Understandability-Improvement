package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed3 {

    /**
     * Verifies that tokens with ignored substrings (not trimmed) are correctly parsed when
     * ignoreEmptyTokens=false and emptyTokenAsNull=true.
     *
     * Input: "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE "
     * Delimiter: ':'
     * Ignored matcher: "IGNORE"
     *
     * After stripping "IGNORE" occurrences from each field:
     *   field 0: "IGNOREaIGNORE"  -> "a"
     *   field 1: " IGNORE bIGNOREc IGNORE " -> "  bc  "
     *   field 2: " IGNORE "       -> "  "
     */
    @Test
    void testBasicIgnoreTrimmed3() {
        final String input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";
        final StringTokenizer tok = new StringTokenizer(input, ':');
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());
        assertEquals("  bc  ", tok.next());
        assertEquals("  ", tok.next());
        assertFalse(tok.hasNext());
    }
}
