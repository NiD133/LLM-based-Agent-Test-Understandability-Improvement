package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testTabMatcher extends AbstractLangTest {

    private static final char[] SAMPLE_TEXT = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int POSITION_BEFORE_TAB = 2;
    private static final int POSITION_OF_TAB = 3;
    private static final int POSITION_AFTER_TAB = 4;

    @Test
    void testTabMatcher() {
        final StrMatcher matcher = StrMatcher.tabMatcher();

        assertSame(matcher, StrMatcher.tabMatcher());
        assertEquals(0, matcher.isMatch(SAMPLE_TEXT, POSITION_BEFORE_TAB));
        assertEquals(1, matcher.isMatch(SAMPLE_TEXT, POSITION_OF_TAB));
        assertEquals(0, matcher.isMatch(SAMPLE_TEXT, POSITION_AFTER_TAB));
    }
}
