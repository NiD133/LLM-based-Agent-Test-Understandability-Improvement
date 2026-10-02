package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSingleQuoteMatcher extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testSingleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.singleQuoteMatcher();
        assertSame(matcher, StrMatcher.singleQuoteMatcher());
        assertEquals(0, matcher.isMatch(BUFFER1, 10));
        assertEquals(1, matcher.isMatch(BUFFER1, 11));
        assertEquals(0, matcher.isMatch(BUFFER1, 12));
    }
}
