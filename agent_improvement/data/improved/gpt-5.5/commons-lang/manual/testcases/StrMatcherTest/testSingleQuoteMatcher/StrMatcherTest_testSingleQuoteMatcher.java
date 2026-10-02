package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSingleQuoteMatcher extends AbstractLangTest {

    private static final char[] SAMPLE_BUFFER = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int NULL_CHARACTER_POSITION = 10;
    private static final int SINGLE_QUOTE_POSITION = 11;
    private static final int DOUBLE_QUOTE_POSITION = 12;

    private static final int NO_MATCH_LENGTH = 0;
    private static final int SINGLE_CHARACTER_MATCH_LENGTH = 1;

    @Test
    void testSingleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.singleQuoteMatcher();

        assertSame(matcher, StrMatcher.singleQuoteMatcher());
        assertEquals(NO_MATCH_LENGTH, matcher.isMatch(SAMPLE_BUFFER, NULL_CHARACTER_POSITION));
        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH, matcher.isMatch(SAMPLE_BUFFER, SINGLE_QUOTE_POSITION));
        assertEquals(NO_MATCH_LENGTH, matcher.isMatch(SAMPLE_BUFFER, DOUBLE_QUOTE_POSITION));
    }
}
