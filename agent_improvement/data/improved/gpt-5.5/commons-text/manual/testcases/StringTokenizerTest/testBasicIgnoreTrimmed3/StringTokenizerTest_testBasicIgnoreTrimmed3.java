package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed3 {

    private static final char TOKEN_DELIMITER = ':';
    private static final String IGNORED_TEXT = "IGNORE";
    private static final String INPUT_WITH_IGNORED_MARKERS = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";

    @Test
    void testBasicIgnoreTrimmed3() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_IGNORED_MARKERS, TOKEN_DELIMITER);
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher(IGNORED_TEXT));
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("  bc  ", tokenizer.next());
        assertEquals("  ", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
