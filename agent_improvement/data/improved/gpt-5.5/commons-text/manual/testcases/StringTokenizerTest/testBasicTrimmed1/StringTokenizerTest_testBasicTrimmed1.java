package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicTrimmed1 {

    private static final String INPUT_WITH_TRAILING_EMPTY_TOKEN = "a: b :  ";
    private static final char FIELD_DELIMITER = ':';

    @Test
    void testBasicTrimmed1() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_TRAILING_EMPTY_TOKEN, FIELD_DELIMITER);
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
