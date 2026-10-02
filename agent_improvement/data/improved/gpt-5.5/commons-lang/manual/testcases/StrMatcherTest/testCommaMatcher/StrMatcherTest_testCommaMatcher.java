package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testCommaMatcher extends AbstractLangTest {

    private static final char[] TEXT_WITH_COMMA_AT_INDEX_ONE = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    @Test
    void testCommaMatcher() {
        final StrMatcher matcher = StrMatcher.commaMatcher();

        assertSame(matcher, StrMatcher.commaMatcher());
        assertEquals(0, matcher.isMatch(TEXT_WITH_COMMA_AT_INDEX_ONE, 0));
        assertEquals(1, matcher.isMatch(TEXT_WITH_COMMA_AT_INDEX_ONE, 1));
        assertEquals(0, matcher.isMatch(TEXT_WITH_COMMA_AT_INDEX_ONE, 2));
    }
}
