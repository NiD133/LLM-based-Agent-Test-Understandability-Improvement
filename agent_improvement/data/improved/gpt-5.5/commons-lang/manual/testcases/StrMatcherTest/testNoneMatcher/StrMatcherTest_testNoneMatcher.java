package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testNoneMatcher extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final int NO_MATCH = 0;

    @Test
    void testNoneMatcher() {
        final StrMatcher matcher = StrMatcher.noneMatcher();

        assertSame(matcher, StrMatcher.noneMatcher());

        for (int position = 0; position < BUFFER1.length; position++) {
            assertEquals(NO_MATCH, matcher.isMatch(BUFFER1, position));
        }
    }
}
