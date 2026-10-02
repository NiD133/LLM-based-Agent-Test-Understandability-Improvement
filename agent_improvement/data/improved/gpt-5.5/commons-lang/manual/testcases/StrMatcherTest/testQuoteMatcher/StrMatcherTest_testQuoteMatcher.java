package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testQuoteMatcher extends AbstractLangTest {

    private static final char[] BUFFER = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int NULL_CHARACTER_INDEX = 10;
    private static final int SINGLE_QUOTE_INDEX = 11;
    private static final int DOUBLE_QUOTE_INDEX = 12;

    @Test
    void testQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.quoteMatcher();

        assertSame(matcher, StrMatcher.quoteMatcher());
        assertEquals(0, matcher.isMatch(BUFFER, NULL_CHARACTER_INDEX));
        assertEquals(1, matcher.isMatch(BUFFER, SINGLE_QUOTE_INDEX));
        assertEquals(1, matcher.isMatch(BUFFER, DOUBLE_QUOTE_INDEX));
    }
}
