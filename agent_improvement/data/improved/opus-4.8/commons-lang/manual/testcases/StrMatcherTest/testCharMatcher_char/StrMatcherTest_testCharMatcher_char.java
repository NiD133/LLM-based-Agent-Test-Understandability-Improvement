package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#charMatcher(char)}.
 */
@Deprecated
public class StrMatcherTest_testCharMatcher_char extends AbstractLangTest {

    /** Input text used for matching. Indices: a=0, b=1, c=2, d=3, e=4, f=5. */
    private static final char[] INPUT = "abcdef".toCharArray();

    /** A single match contributes one matched character; no match contributes zero. */
    private static final int MATCH = 1;
    private static final int NO_MATCH = 0;

    @Test
    void testCharMatcher_char() {
        // A matcher that only recognizes the character 'c'.
        final StrMatcher matcher = StrMatcher.charMatcher('c');

        // 'c' appears in INPUT exactly once, at index 2, so only that position matches.
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 0)); // 'a'
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 1)); // 'b'
        assertEquals(MATCH, matcher.isMatch(INPUT, 2));    // 'c'
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 3)); // 'd'
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 4)); // 'e'
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 5)); // 'f'
    }
}
