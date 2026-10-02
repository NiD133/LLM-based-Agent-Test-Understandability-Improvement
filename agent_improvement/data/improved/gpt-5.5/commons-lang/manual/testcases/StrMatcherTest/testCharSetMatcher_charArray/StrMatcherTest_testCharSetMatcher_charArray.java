package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testCharSetMatcher_charArray extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    private static final char[] MATCHING_CHARACTERS = "ace".toCharArray();

    private static final char[] SINGLE_MATCHING_CHARACTER = "a".toCharArray();

    @Test
    void testCharSetMatcher_charArray() {
        final StrMatcher matcher = StrMatcher.charSetMatcher(MATCHING_CHARACTERS);

        assertEquals(1, matcher.isMatch(BUFFER2, 0));
        assertEquals(0, matcher.isMatch(BUFFER2, 1));
        assertEquals(1, matcher.isMatch(BUFFER2, 2));
        assertEquals(0, matcher.isMatch(BUFFER2, 3));
        assertEquals(1, matcher.isMatch(BUFFER2, 4));
        assertEquals(0, matcher.isMatch(BUFFER2, 5));

        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher());
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher((char[]) null));
        assertInstanceOf(StrMatcher.CharMatcher.class, StrMatcher.charSetMatcher(SINGLE_MATCHING_CHARACTER));
    }
}
