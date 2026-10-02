package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test1 {

    private static final String SEMICOLON_DELIMITED_TEXT_WITH_QUOTED_DELIMITER = "a;b;c;\"d;\"\"e\";f; ; ;  ";

    private static final String[] EXPECTED_TOKENS = { "a", "b", "c", "d;\"e", "f", "", "", "" };

    @Test
    void test1() {
        final StringTokenizer tokenizer = new StringTokenizer(SEMICOLON_DELIMITED_TEXT_WITH_QUOTED_DELIMITER);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] actualTokens = tokenizer.getTokenArray();

        assertEquals(EXPECTED_TOKENS.length, actualTokens.length, Arrays.toString(actualTokens));
        for (int tokenIndex = 0; tokenIndex < EXPECTED_TOKENS.length; tokenIndex++) {
            assertEquals(EXPECTED_TOKENS[tokenIndex], actualTokens[tokenIndex],
                    "token[" + tokenIndex + "] was '" + actualTokens[tokenIndex] + "' but was expected to be '"
                            + EXPECTED_TOKENS[tokenIndex] + "'");
        }
    }
}
