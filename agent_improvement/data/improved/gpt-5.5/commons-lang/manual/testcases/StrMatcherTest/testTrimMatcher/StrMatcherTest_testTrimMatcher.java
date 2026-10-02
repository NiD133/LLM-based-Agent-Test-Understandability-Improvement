package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testTrimMatcher extends AbstractLangTest {

    private static final char[] BUFFER = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int[][] EXPECTED_TRIM_MATCH_LENGTH_BY_POSITION = {
        {2, 0},
        {3, 1},
        {4, 0},
        {5, 1},
        {6, 0},
        {7, 1},
        {8, 1},
        {9, 1},
        {10, 1}
    };

    @Test
    void testTrimMatcher() {
        final StrMatcher matcher = StrMatcher.trimMatcher();
        assertSame(matcher, StrMatcher.trimMatcher());

        for (final int[] positionAndExpectedMatchLength : EXPECTED_TRIM_MATCH_LENGTH_BY_POSITION) {
            final int position = positionAndExpectedMatchLength[0];
            final int expectedMatchLength = positionAndExpectedMatchLength[1];

            assertEquals(expectedMatchLength, matcher.isMatch(BUFFER, position));
        }
    }
}
