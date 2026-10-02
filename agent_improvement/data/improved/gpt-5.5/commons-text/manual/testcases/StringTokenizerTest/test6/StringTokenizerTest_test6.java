package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_test6 {

    private static final String DELIMITED_INPUT = "a;b; c;\"d;\"\"e\";f; ; ;";
    private static final String[] EXPECTED_TOKENS = { "a", "b", " c", "d;\"e", "f", null, null, null };

    @Test
    void test6() {
        final StringTokenizer tokenizer = new StringTokenizer(DELIMITED_INPUT);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] actualTokens = tokenizer.getTokenArray();

        int forwardTraversalCount = 0;
        while (tokenizer.hasNext()) {
            tokenizer.next();
            forwardTraversalCount++;
        }

        int backwardTraversalCount = 0;
        while (tokenizer.hasPrevious()) {
            tokenizer.previous();
            backwardTraversalCount++;
        }

        assertEquals(EXPECTED_TOKENS.length, actualTokens.length, Arrays.toString(actualTokens));
        assertEquals(EXPECTED_TOKENS.length, forwardTraversalCount,
                "could not cycle through entire token list using the 'hasNext' and 'next' methods");
        assertEquals(EXPECTED_TOKENS.length, backwardTraversalCount,
                "could not cycle through entire token list using the 'hasPrevious' and 'previous' methods");
    }
}
