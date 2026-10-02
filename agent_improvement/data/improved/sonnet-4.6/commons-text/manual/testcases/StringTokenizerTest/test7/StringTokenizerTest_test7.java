package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test7 {

    @Test
    void test7() {
        final String input = "a   b c \"d e\" f ";

        final StringTokenizer tok = new StringTokenizer(input);
        tok.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tok.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tok.setIgnoreEmptyTokens(false);

        final String[] tokens = tok.getTokenArray();
        final String[] expected = { "a", "", "", "b", "c", "d e", "f", "" };

        assertEquals(expected.length, tokens.length, "Token count mismatch");
        assertArrayEquals(expected, tokens, "Tokenized values do not match expected output");
    }
}
