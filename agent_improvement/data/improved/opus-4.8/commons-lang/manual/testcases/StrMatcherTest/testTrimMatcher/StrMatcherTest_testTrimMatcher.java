package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testTrimMatcher extends AbstractLangTest {

    /**
     * Sample text exercised by the trim matcher. Each index below is annotated with the
     * character at that position and whether the trim matcher treats it as whitespace.
     * The trim matcher reports a match (1) for any character with a code point &lt;= 32.
     *
     *   index | character        | code | whitespace?
     *   ------+------------------+------+------------
     *     2   | digit '1'        |  49  | no  -> 0
     *     3   | tab              |   9  | yes -> 1
     *     4   | digit '2'        |  50  | no  -> 0
     *     5   | space            |  32  | yes -> 1
     *     6   | digit '3'        |  51  | no  -> 0
     *     7   | line feed        |  10  | yes -> 1
     *     8   | carriage return  |  13  | yes -> 1
     *     9   | form feed        |  12  | yes -> 1
     *    10   | null character   |   0  | yes -> 1
     *
     * Buffer content is identical to the original test.
     */
    private static final char[] SAMPLE_TEXT = "0,1\\t2 3\\n\\r\\f\\u0000'\"".toCharArray();

    private static final int NO_MATCH = 0;
    private static final int MATCH = 1;

    @Test
    void testTrimMatcher() {
        final StrMatcher matcher = StrMatcher.trimMatcher();

        // trimMatcher() always returns the same shared singleton instance.
        assertSame(matcher, StrMatcher.trimMatcher());

        // Non-whitespace characters do not match.
        assertEquals(NO_MATCH, matcher.isMatch(SAMPLE_TEXT, 2));  // digit '1'
        assertEquals(NO_MATCH, matcher.isMatch(SAMPLE_TEXT, 4));  // digit '2'
        assertEquals(NO_MATCH, matcher.isMatch(SAMPLE_TEXT, 6));  // digit '3'

        // Whitespace and control characters (code point <= 32) match.
        assertEquals(MATCH, matcher.isMatch(SAMPLE_TEXT, 3));   // tab
        assertEquals(MATCH, matcher.isMatch(SAMPLE_TEXT, 5));   // space
        assertEquals(MATCH, matcher.isMatch(SAMPLE_TEXT, 7));   // line feed
        assertEquals(MATCH, matcher.isMatch(SAMPLE_TEXT, 8));   // carriage return
        assertEquals(MATCH, matcher.isMatch(SAMPLE_TEXT, 9));   // form feed
        assertEquals(MATCH, matcher.isMatch(SAMPLE_TEXT, 10));  // null character
    }
}
