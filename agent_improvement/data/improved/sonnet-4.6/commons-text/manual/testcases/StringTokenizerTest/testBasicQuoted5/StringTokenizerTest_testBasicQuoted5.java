package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted5 {

    /**
     * Verifies that adjacent quoted segments separated by unquoted characters are merged into a single token.
     * Input "a: 'b'x'c' :d" with delimiter ':' and quote '\'' should yield tokens "a", "bxc", "d".
     * The trimmer strips surrounding whitespace from each token, and empty-token-as-null is enabled
     * (though no empty tokens appear in this input).
     */
    @Test
    void testBasicQuoted5() {
        final String input = "a: 'b'x'c' :d";
        final StringTokenizer tok = new StringTokenizer(input, ':', '\'');
        tok.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);
        assertEquals("a", tok.next());
        assertEquals("bxc", tok.next());
        assertEquals("d", tok.next());
        assertFalse(tok.hasNext());
    }
}
