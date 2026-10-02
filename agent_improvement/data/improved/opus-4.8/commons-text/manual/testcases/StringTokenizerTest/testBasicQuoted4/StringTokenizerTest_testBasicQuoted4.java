package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted4 {

    /**
     * Tokenizes a string that mixes a colon delimiter with single-quoted sections.
     *
     * <p>Input layout: {@code a: 'b' 'c' :d}
     * <ul>
     *   <li>{@code ':'} is the delimiter, so the input splits into three fields:
     *       {@code "a"}, {@code " 'b' 'c' "} and {@code "d"}.</li>
     *   <li>{@code '\''} is the quote char, so the quotes around {@code b} and {@code c}
     *       are stripped while their surrounding spaces are preserved as token content.</li>
     *   <li>The trimmer matcher removes the leading/trailing whitespace of each field,
     *       turning the middle field's {@code " b c "} into {@code "b c"}.</li>
     * </ul>
     */
    @Test
    void testBasicQuoted4() {
        final String input = "a: 'b' 'c' :d";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':', '\'');
        tokenizer.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b c", tokenizer.next());
        assertEquals("d", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
