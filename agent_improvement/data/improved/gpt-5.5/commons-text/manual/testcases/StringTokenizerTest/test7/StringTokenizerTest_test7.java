package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test7 {

    private static final String INPUT = "a   b c \"d e\" f ";

    private static final String[] EXPECTED_TOKENS = { "a", "", "", "b", "c", "d e", "f", "" };

    @Test
    void test7() {
        final StringTokenizer tokenizer = newSpaceDelimitedTokenizerKeepingEmptyTokens(INPUT);

        final String[] tokens = tokenizer.getTokenArray();

        assertEquals(EXPECTED_TOKENS.length, tokens.length, Arrays.toString(tokens));
        for (int i = 0; i < EXPECTED_TOKENS.length; i++) {
            assertEquals(EXPECTED_TOKENS[i], tokens[i],
                    "token[" + i + "] was '" + tokens[i] + "' but was expected to be '" + EXPECTED_TOKENS[i] + "'");
        }
    }

    private StringTokenizer newSpaceDelimitedTokenizerKeepingEmptyTokens(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        return tokenizer;
    }
}
