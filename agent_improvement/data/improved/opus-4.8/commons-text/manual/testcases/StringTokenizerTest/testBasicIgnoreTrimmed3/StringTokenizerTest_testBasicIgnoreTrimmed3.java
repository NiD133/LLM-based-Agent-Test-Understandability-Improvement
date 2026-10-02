package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicIgnoreTrimmed3 {

    /**
     * Verifies tokenizing with an "ignored" matcher combined with default whitespace trimming.
     *
     * <p>The tokenizer splits on ':' while stripping every occurrence of the literal "IGNORE"
     * from the input. After the "IGNORE" fragments are removed, the surrounding whitespace of
     * each token is trimmed, yielding the three expected tokens.</p>
     */
    @Test
    void testBasicIgnoreTrimmed3() {
        // Input contains ':' delimiters and "IGNORE" markers scattered throughout.
        final String input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE ";

        final StringTokenizer tokenizer = new StringTokenizer(input, ':');
        // Remove every literal "IGNORE" before splitting into tokens.
        tokenizer.setIgnoredMatcher(StringMatcherFactory.INSTANCE.stringMatcher("IGNORE"));
        // Keep empty tokens (do not collapse them away)...
        tokenizer.setIgnoreEmptyTokens(false);
        // ...but represent any empty token as null.
        tokenizer.setEmptyTokenAsNull(true);

        // After stripping "IGNORE": "a: bc  :  " -> split on ':' and trim each segment.
        assertEquals("a", tokenizer.next());
        assertEquals("  bc  ", tokenizer.next());
        assertEquals("  ", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
