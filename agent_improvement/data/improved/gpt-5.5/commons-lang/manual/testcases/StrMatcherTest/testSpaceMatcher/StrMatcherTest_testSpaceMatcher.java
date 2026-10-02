package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSpaceMatcher extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int NON_SPACE_BEFORE_SPACE_INDEX = 4;
    private static final int SPACE_INDEX = 5;
    private static final int NON_SPACE_AFTER_SPACE_INDEX = 6;

    private static final int NO_MATCH_LENGTH = 0;
    private static final int SINGLE_CHARACTER_MATCH_LENGTH = 1;

    @Test
    void testSpaceMatcher() {
        final StrMatcher matcher = StrMatcher.spaceMatcher();

        assertSame(matcher, StrMatcher.spaceMatcher());
        assertEquals(NO_MATCH_LENGTH, matcher.isMatch(BUFFER1, NON_SPACE_BEFORE_SPACE_INDEX));
        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH, matcher.isMatch(BUFFER1, SPACE_INDEX));
        assertEquals(NO_MATCH_LENGTH, matcher.isMatch(BUFFER1, NON_SPACE_AFTER_SPACE_INDEX));
    }
}
