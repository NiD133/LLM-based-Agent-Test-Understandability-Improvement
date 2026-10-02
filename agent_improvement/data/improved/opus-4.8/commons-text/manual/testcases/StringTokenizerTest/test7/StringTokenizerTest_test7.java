package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test7 {

    /**
     * Tokenizes a space-delimited string while keeping empty tokens, so that
     * runs of consecutive spaces yield empty strings and a double-quoted
     * section ("d e") is treated as a single token.
     */
    @Test
    void tokenizesSpaceDelimitedInputKeepingEmptyAndQuotedTokens() {
        final String input = "a   b c \"d e\" f ";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] actualTokens = tokenizer.getTokenArray();

        final String[] expectedTokens = { "a", "", "", "b", "c", "d e", "f", "" };
        assertEquals(expectedTokens.length, actualTokens.length, Arrays.toString(actualTokens));
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], actualTokens[i],
                "token[" + i + "] was '" + actualTokens[i] + "' but was expected to be '" + expectedTokens[i] + "'");
        }
    }
}
