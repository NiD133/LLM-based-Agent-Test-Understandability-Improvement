package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted4 {

    private static final char FIELD_DELIMITER = ':';
    private static final char QUOTE_CHARACTER = '\'';
    private static final String INPUT_WITH_ADJACENT_QUOTED_VALUES = "a: 'b' 'c' :d";

    @Test
    void testBasicQuoted4() {
        final StringTokenizer tokenizer = new StringTokenizer(
                INPUT_WITH_ADJACENT_QUOTED_VALUES,
                FIELD_DELIMITER,
                QUOTE_CHARACTER);
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b c", tokenizer.next());
        assertEquals("d", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
