package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed4 {

    @Test
    void testBasicIgnoreTrimmed4() {
        final String ignoredText = "IGNORE";
        final String input = "IGNOREaIGNORE: IGNORE 'bIGNOREc'IGNORE'd' IGNORE : IGNORE ";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher(ignoredText));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("bIGNOREcd", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
