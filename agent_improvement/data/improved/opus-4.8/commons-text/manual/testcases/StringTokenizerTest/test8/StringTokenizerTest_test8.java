package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer} splits a string on spaces while honouring
 * double-quoted sections and discarding the empty tokens produced by runs of
 * consecutive delimiters.
 */
public class StringTokenizerTest_test8 {

    @Test
    void tokenizesSpaceSeparatedInputWithQuotedSectionAndEmptyTokensIgnored() {
        // Input has multiple spaces between "a" and "b" (which would yield empty
        // tokens), a quoted segment "d e" that must stay intact, and a trailing space.
        final String input = "a   b c \"d e\" f ";

        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setDelimiterMatcher(StringMatcherFactory.INSTANCE.spaceMatcher());
        tokenizer.setQuoteMatcher(StringMatcherFactory.INSTANCE.doubleQuoteMatcher());
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        tokenizer.setIgnoreEmptyTokens(true);

        final String[] expectedTokens = { "a", "b", "c", "d e", "f" };
        final String[] actualTokens = tokenizer.getTokenArray();

        assertArrayEquals(expectedTokens, actualTokens);
    }
}
