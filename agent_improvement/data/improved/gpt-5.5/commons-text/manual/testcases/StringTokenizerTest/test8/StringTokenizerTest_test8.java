package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test8 {

    private static final String QUOTED_SPACE_DELIMITED_INPUT = "a   b c \"d e\" f ";

    private static final String[] EXPECTED_TOKENS = { "a", "b", "c", "d e", "f" };

    @Test
    void test8() {
        final StringTokenizer tokenizer = createSpaceDelimitedTokenizer(QUOTED_SPACE_DELIMITED_INPUT);
        final String[] tokens = tokenizer.getTokenArray();

        assertTokens(EXPECTED_TOKENS, tokens);
    }

    private StringTokenizer createSpaceDelimitedTokenizer(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(true);
        return tokenizer;
    }

    private void assertTokens(final String[] expected, final String[] actual) {
        assertEquals(expected.length, actual.length, Arrays.toString(actual));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "token[" + i + "] was '" + actual[i] + "' but was expected to be '" + expected[i] + "'");
        }
    }
}
