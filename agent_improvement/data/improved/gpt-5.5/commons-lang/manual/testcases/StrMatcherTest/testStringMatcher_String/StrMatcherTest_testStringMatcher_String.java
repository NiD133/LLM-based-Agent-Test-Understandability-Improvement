package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testStringMatcher_String extends AbstractLangTest {

    private static final char[] TEXT = "abcdef".toCharArray();

    @Test
    void testStringMatcher_String() {
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");

        assertEquals(0, matcher.isMatch(TEXT, 0));
        assertEquals(2, matcher.isMatch(TEXT, 1));
        assertEquals(0, matcher.isMatch(TEXT, 2));
        assertEquals(0, matcher.isMatch(TEXT, 3));
        assertEquals(0, matcher.isMatch(TEXT, 4));
        assertEquals(0, matcher.isMatch(TEXT, 5));

        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(""));
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(null));
    }
}
