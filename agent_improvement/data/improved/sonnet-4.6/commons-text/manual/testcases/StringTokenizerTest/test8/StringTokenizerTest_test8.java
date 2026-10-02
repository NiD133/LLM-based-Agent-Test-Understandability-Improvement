package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test8 {

    @Test
    @DisplayName("Space-delimited input with double-quote grouping and empty-token suppression produces correct tokens")
    void test8() {
        final String input = "a   b c \"d e\" f ";
        final StringTokenizer tok = new StringTokenizer(input);
        tok.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tok.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tok.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tok.setIgnoreEmptyTokens(true);

        final String[] expected = {"a", "b", "c", "d e", "f"};
        final String[] actual = tok.getTokenArray();

        assertArrayEquals(expected, actual);
    }
}
