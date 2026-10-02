package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted4 {

    @Test
    void testBasicQuoted4() {
        // Tokenize using ':' as delimiter and '\'' as quote char, with whitespace trimming.
        // Adjacent quoted tokens between delimiters are concatenated into a single token.
        final String input = "a: 'b' 'c' :d";
        final StringTokenizer tok = new StringTokenizer(input, ':', '\'');
        tok.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());
        assertEquals("b c", tok.next());
        assertEquals("d", tok.next());
        assertFalse(tok.hasNext());
    }
}
