package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testDoubleQuoteMatcher extends AbstractLangTest {

    private static final char[] QUOTE_BUFFER = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();
    private static final int SINGLE_QUOTE_INDEX = 11;
    private static final int DOUBLE_QUOTE_INDEX = 12;
    private static final int NO_MATCH = 0;
    private static final int ONE_CHARACTER_MATCH = 1;

    @Test
    void testDoubleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.doubleQuoteMatcher();

        assertSame(matcher, StrMatcher.doubleQuoteMatcher());
        assertEquals(NO_MATCH, matcher.isMatch(QUOTE_BUFFER, SINGLE_QUOTE_INDEX));
        assertEquals(ONE_CHARACTER_MATCH, matcher.isMatch(QUOTE_BUFFER, DOUBLE_QUOTE_INDEX));
    }
}
