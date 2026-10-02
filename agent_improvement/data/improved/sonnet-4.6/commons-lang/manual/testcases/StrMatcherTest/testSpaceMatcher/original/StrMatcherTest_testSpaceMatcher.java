package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testSpaceMatcher extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testSpaceMatcher() {
        final StrMatcher matcher = StrMatcher.spaceMatcher();
        assertSame(matcher, StrMatcher.spaceMatcher());
        assertEquals(0, matcher.isMatch(BUFFER1, 4));
        assertEquals(1, matcher.isMatch(BUFFER1, 5));
        assertEquals(0, matcher.isMatch(BUFFER1, 6));
    }
}
