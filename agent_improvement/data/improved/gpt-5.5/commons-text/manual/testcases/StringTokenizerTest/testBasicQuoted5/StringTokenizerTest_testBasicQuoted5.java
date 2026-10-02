package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted5 {

    @Test
    void testBasicQuoted5() {
        final String input = "a: 'b'x'c' :d";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');

        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("bxc", tokenizer.next());
        assertEquals("d", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
