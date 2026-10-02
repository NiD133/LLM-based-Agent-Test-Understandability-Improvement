package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSplitMatcher extends AbstractLangTest {

    private static final char[] MIXED_DELIMITERS = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int COMMA_POSITION = 1;
    private static final int DIGIT_ONE_POSITION = 2;
    private static final int TAB_POSITION = 3;
    private static final int DIGIT_TWO_POSITION = 4;
    private static final int SPACE_POSITION = 5;
    private static final int DIGIT_THREE_POSITION = 6;
    private static final int NEWLINE_POSITION = 7;
    private static final int CARRIAGE_RETURN_POSITION = 8;
    private static final int FORM_FEED_POSITION = 9;
    private static final int NUL_POSITION = 10;

    private static final int NO_MATCH = 0;
    private static final int SINGLE_CHARACTER_MATCH = 1;

    @Test
    void testSplitMatcher() {
        final StrMatcher matcher = StrMatcher.splitMatcher();

        assertSame(matcher, StrMatcher.splitMatcher());
        assertEquals(NO_MATCH, matcher.isMatch(MIXED_DELIMITERS, DIGIT_ONE_POSITION));
        assertEquals(SINGLE_CHARACTER_MATCH, matcher.isMatch(MIXED_DELIMITERS, TAB_POSITION));
        assertEquals(NO_MATCH, matcher.isMatch(MIXED_DELIMITERS, DIGIT_TWO_POSITION));
        assertEquals(SINGLE_CHARACTER_MATCH, matcher.isMatch(MIXED_DELIMITERS, SPACE_POSITION));
        assertEquals(NO_MATCH, matcher.isMatch(MIXED_DELIMITERS, DIGIT_THREE_POSITION));
        assertEquals(SINGLE_CHARACTER_MATCH, matcher.isMatch(MIXED_DELIMITERS, NEWLINE_POSITION));
        assertEquals(SINGLE_CHARACTER_MATCH, matcher.isMatch(MIXED_DELIMITERS, CARRIAGE_RETURN_POSITION));
        assertEquals(SINGLE_CHARACTER_MATCH, matcher.isMatch(MIXED_DELIMITERS, FORM_FEED_POSITION));
        assertEquals(NO_MATCH, matcher.isMatch(MIXED_DELIMITERS, NUL_POSITION));
    }
}
