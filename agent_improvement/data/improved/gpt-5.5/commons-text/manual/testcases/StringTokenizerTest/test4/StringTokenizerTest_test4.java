package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test4 {

    private static final String SEMICOLON_DELIMITED_INPUT = "a;b; c;\"d;\"\"e\";f; ; ;";

    private static final String[] EXPECTED_TOKENS = { "a", "b", "c", "d;\"e", "f" };

    @Test
    void test4() {
        final StringTokenizer tokenizer = tokenizerIgnoringBlankSemicolonFields(SEMICOLON_DELIMITED_INPUT);

        final String[] tokens = tokenizer.getTokenArray();

        assertEquals(EXPECTED_TOKENS.length, tokens.length, Arrays.toString(tokens));
        for (int tokenIndex = 0; tokenIndex < EXPECTED_TOKENS.length; tokenIndex++) {
            assertEquals(EXPECTED_TOKENS[tokenIndex], tokens[tokenIndex],
                    "token[" + tokenIndex + "] was '" + tokens[tokenIndex] + "' but was expected to be '"
                            + EXPECTED_TOKENS[tokenIndex] + "'");
        }
    }

    private StringTokenizer tokenizerIgnoringBlankSemicolonFields(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(true);
        return tokenizer;
    }
}
