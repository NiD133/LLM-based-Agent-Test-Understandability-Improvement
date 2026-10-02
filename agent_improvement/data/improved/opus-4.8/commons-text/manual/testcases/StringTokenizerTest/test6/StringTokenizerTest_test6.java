package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link StringTokenizer} configured for quoted, semicolon-separated
 * input can be fully traversed forwards (via {@code hasNext}/{@code next}) and then
 * fully traversed backwards (via {@code hasPrevious}/{@code previous}), visiting every
 * token exactly once in each direction.
 */
public class StringTokenizerTest_test6 {

    @Test
    void canIterateForwardsAndBackwardsOverAllTokens() {
        // Input uses ';' as the delimiter and '"' as the quote character.
        // The quoted field "d;""e" contains an escaped quote and an embedded delimiter,
        // and the input ends with several empty fields.
        final String input = "a;b; c;\"d;\"\"e\";f; ; ;";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterChar(';');
        tokenizer.setQuoteChar('"');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);

        final String[] tokens = tokenizer.getTokenArray();

        // The expected tokens for the input above; only its length (8) is asserted,
        // since this test focuses on traversal completeness rather than token values.
        final String[] expectedTokens = {"a", "b", " c", "d;\"e", "f", null, null, null};
        final int expectedTokenCount = expectedTokens.length;

        // Walk forwards through every token.
        int forwardCount = 0;
        while (tokenizer.hasNext()) {
            tokenizer.next();
            forwardCount++;
        }

        // Walk backwards through every token.
        int backwardCount = 0;
        while (tokenizer.hasPrevious()) {
            tokenizer.previous();
            backwardCount++;
        }

        assertEquals(expectedTokenCount, tokens.length, Arrays.toString(tokens));
        assertEquals(forwardCount, expectedTokenCount,
                "could not cycle through entire token list using the 'hasNext' and 'next' methods");
        assertEquals(backwardCount, expectedTokenCount,
                "could not cycle through entire token list using the 'hasPrevious' and 'previous' methods");
    }
}
