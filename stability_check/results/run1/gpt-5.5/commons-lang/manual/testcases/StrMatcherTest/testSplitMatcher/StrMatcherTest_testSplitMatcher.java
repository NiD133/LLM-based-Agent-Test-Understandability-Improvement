package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSplitMatcher extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final SplitMatcherCase[] SPLIT_MATCHER_CASES = {
        new SplitMatcherCase(2, 0),
        new SplitMatcherCase(3, 1),
        new SplitMatcherCase(4, 0),
        new SplitMatcherCase(5, 1),
        new SplitMatcherCase(6, 0),
        new SplitMatcherCase(7, 1),
        new SplitMatcherCase(8, 1),
        new SplitMatcherCase(9, 1),
        new SplitMatcherCase(10, 0)
    };

    @Test
    void testSplitMatcher() {
        final StrMatcher matcher = StrMatcher.splitMatcher();

        assertSame(matcher, StrMatcher.splitMatcher());
        for (final SplitMatcherCase testCase : SPLIT_MATCHER_CASES) {
            assertEquals(testCase.expectedMatchLength, matcher.isMatch(BUFFER1, testCase.position));
        }
    }

    private static final class SplitMatcherCase {

        private final int position;
        private final int expectedMatchLength;

        private SplitMatcherCase(final int position, final int expectedMatchLength) {
            this.position = position;
            this.expectedMatchLength = expectedMatchLength;
        }
    }
}
