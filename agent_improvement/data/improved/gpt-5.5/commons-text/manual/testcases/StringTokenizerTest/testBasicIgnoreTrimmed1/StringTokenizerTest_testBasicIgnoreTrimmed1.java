package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed1 {

    private static final String INPUT_WITH_IGNORED_TEXT_AND_TRIMMED_EMPTY_TOKEN = "a: bIGNOREc : ";
    private static final char TOKEN_DELIMITER = ':';
    private static final String IGNORED_TEXT = "IGNORE";

    @Test
    void testBasicIgnoreTrimmed1() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_IGNORED_TEXT_AND_TRIMMED_EMPTY_TOKEN,
                TOKEN_DELIMITER);
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher(IGNORED_TEXT));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("bc", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
