package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringTokenizer} can be configured with custom delimiter and
 * quote matchers via the {@code (String, StringMatcher, StringMatcher)} constructor.
 */
public class StringTokenizerTest_testDelimMatcherQuoteMatcher {

    /**
     * Verifies tokenization when the delimiter and quote characters are supplied as
     * custom matchers rather than the defaults.
     *
     * <p>The input {@code `a`;`b`;`c`} uses {@code ;} as the field delimiter and
     * {@code `} (back-tick) as the quote character. The tokenizer should strip the
     * surrounding quotes and return the three unquoted values {@code "a"}, {@code "b"}
     * and {@code "c"}.</p>
     */
    @Test
    void testDelimMatcherQuoteMatcher() {
        final String quotedInput = "`a`;`b`;`c`";
        final StringMatcher semicolonDelimiter = StringMatcherFactory.INSTANCE.charSetMatcher(';');
        final StringMatcher backTickQuote = StringMatcherFactory.INSTANCE.charSetMatcher('`');

        final StringTokenizer tokenizer =
                new StringTokenizer(quotedInput, semicolonDelimiter, backTickQuote);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
