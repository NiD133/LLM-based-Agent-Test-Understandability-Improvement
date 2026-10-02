package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed4 {

    /**
     * Verifies tokenizing with a colon delimiter and single-quote quote character,
     * while ignoring every occurrence of the literal "IGNORE", trimming whitespace,
     * and mapping empty tokens to {@code null}.
     */
    @Test
    void testBasicIgnoreTrimmed4() {
        final String input = "IGNOREaIGNORE: IGNORE 'bIGNOREc'IGNORE'd' IGNORE : IGNORE ";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        // First field: "IGNOREaIGNORE" -> ignored markers removed, then trimmed -> "a"
        assertEquals("a", tokenizer.next());
        // Second field: quoted content preserves the inner "IGNORE", surrounding ignores stripped
        assertEquals("bIGNOREcd", tokenizer.next());
        // Third field: only an ignored marker and whitespace -> empty token returned as null
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
