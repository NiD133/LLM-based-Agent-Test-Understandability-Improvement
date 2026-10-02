package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed1 {

    /**
     * Verifies that a {@link StringTokenizer} combines an "ignore" matcher, a trimmer matcher
     * and empty-token handling correctly.
     *
     * <p>The input {@code "a: bIGNOREc : "} is split on ':' into three raw tokens:
     * {@code "a"}, {@code " bIGNOREc "} and {@code " "}. With the configuration below:</p>
     * <ul>
     *   <li>the literal {@code "IGNORE"} is stripped out of every token,</li>
     *   <li>surrounding whitespace is trimmed from every token,</li>
     *   <li>empty tokens are kept (not skipped) but reported as {@code null}.</li>
     * </ul>
     *
     * <p>So the expected sequence of tokens is {@code "a"}, {@code "bc"} and {@code null}.</p>
     */
    @Test
    void ignoredAndTrimmedTokensWithEmptyAsNull() {
        final String input = "a: bIGNOREc : ";
        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next(), "first token, trimmed");
        assertEquals("bc", tokenizer.next(), "second token, with 'IGNORE' removed and trimmed");
        assertNull(tokenizer.next(), "third token is empty and reported as null");
        assertFalse(tokenizer.hasNext(), "no tokens remain");
    }
}
