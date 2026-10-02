package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#singleQuoteMatcher()}.
 *
 * <p>The matcher should detect exactly one character: the single quote ({@code '}).
 * {@code isMatch} returns {@code 1} when the character at the given position is a
 * single quote and {@code 0} otherwise.</p>
 */
@Deprecated
public class StrMatcherTest_testSingleQuoteMatcher extends AbstractLangTest {

    /**
     * Sample text laid out so that three adjacent positions exercise the matcher:
     * <pre>
     *   index 10 -> '\u0000' (null char)    -> no match
     *   index 11 -> '\''     (single quote) -> match
     *   index 12 -> '"'      (double quote) -> no match
     * </pre>
     */
    private static final char[] INPUT = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int NULL_CHAR_INDEX = 10;
    private static final int SINGLE_QUOTE_INDEX = 11;
    private static final int DOUBLE_QUOTE_INDEX = 12;

    private static final int NO_MATCH = 0;
    private static final int SINGLE_QUOTE_MATCH = 1;

    @Test
    void testSingleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.singleQuoteMatcher();

        // The factory returns a shared singleton instance.
        assertSame(matcher, StrMatcher.singleQuoteMatcher());

        // Only the single quote at index 11 is matched; its neighbours are not.
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, NULL_CHAR_INDEX));
        assertEquals(SINGLE_QUOTE_MATCH, matcher.isMatch(INPUT, SINGLE_QUOTE_INDEX));
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, DOUBLE_QUOTE_INDEX));
    }
}
