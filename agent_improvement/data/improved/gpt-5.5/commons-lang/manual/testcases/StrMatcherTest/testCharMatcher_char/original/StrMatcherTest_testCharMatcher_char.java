package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testCharMatcher_char extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testCharMatcher_char() {
        final StrMatcher matcher = StrMatcher.charMatcher('c');
        assertEquals(0, matcher.isMatch(BUFFER2, 0));
        assertEquals(0, matcher.isMatch(BUFFER2, 1));
        assertEquals(1, matcher.isMatch(BUFFER2, 2));
        assertEquals(0, matcher.isMatch(BUFFER2, 3));
        assertEquals(0, matcher.isMatch(BUFFER2, 4));
        assertEquals(0, matcher.isMatch(BUFFER2, 5));
    }
}
