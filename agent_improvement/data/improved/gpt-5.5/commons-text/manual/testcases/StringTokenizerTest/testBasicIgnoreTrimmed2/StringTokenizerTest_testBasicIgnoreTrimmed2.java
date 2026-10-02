package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed2 {

    private static final String IGNORED_TEXT = "IGNORE";
    private static final char TOKEN_DELIMITER = ':';
    private static final String INPUT_WITH_IGNORED_AND_TRIMMED_TEXT =
            "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";

    @Test
    void testBasicIgnoreTrimmed2() {
        final StringTokenizer tokenizer = createTokenizerThatKeepsEmptyTokensAsNull();

        assertEquals("a", tokenizer.next());
        assertEquals("bc", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }

    private StringTokenizer createTokenizerThatKeepsEmptyTokensAsNull() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_IGNORED_AND_TRIMMED_TEXT, TOKEN_DELIMITER);
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher(IGNORED_TEXT));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);
        return tokenizer;
    }
}
