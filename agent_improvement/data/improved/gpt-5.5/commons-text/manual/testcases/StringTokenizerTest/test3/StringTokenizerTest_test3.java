package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test3 {

    private static final String SEMICOLON_DELIMITED_INPUT = "a;b; c;\"d;\"\"e\";f; ; ;";

    private static final String[] EXPECTED_TOKENS = {
        "a",
        "b",
        " c",
        "d;\"e",
        "f",
        " ",
        " ",
        ""
    };

    @Test
    void test3() {
        final StringTokenizer tokenizer = new StringTokenizer(SEMICOLON_DELIMITED_INPUT);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] tokens = tokenizer.getTokenArray();

        assertEquals(EXPECTED_TOKENS.length, tokens.length, Arrays.toString(tokens));
        for (int i = 0; i < EXPECTED_TOKENS.length; i++) {
            assertEquals(EXPECTED_TOKENS[i], tokens[i],
                    "token[" + i + "] was '" + tokens[i] + "' but was expected to be '" + EXPECTED_TOKENS[i] + "'");
        }
    }
}
