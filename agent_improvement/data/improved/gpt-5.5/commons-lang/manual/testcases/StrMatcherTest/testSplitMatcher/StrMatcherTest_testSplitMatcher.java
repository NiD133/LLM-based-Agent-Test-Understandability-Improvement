package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSplitMatcher extends AbstractLangTest {

    private static final char[] MIXED_SEPARATOR_BUFFER = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    @Test
    void testSplitMatcher() {
        final StrMatcher matcher = StrMatcher.splitMatcher();

        assertSame(matcher, StrMatcher.splitMatcher());

        final int[] positionsToCheck = {2, 3, 4, 5, 6, 7, 8, 9, 10};
        final int[] expectedMatchLengths = {0, 1, 0, 1, 0, 1, 1, 1, 0};

        for (int i = 0; i < positionsToCheck.length; i++) {
            assertEquals(expectedMatchLengths[i], matcher.isMatch(MIXED_SEPARATOR_BUFFER, positionsToCheck[i]));
        }
    }
}
