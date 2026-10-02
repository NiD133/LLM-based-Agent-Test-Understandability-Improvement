package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#stringMatcher(String)}.
 */
@Deprecated
public class StrMatcherTest_testStringMatcher_String extends AbstractLangTest {

    /** Text scanned by the matcher: the substring "bc" occurs once, at index 1. */
    private static final char[] INPUT = "abcdef".toCharArray();

    @Test
    void testStringMatcher_String() {
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");

        // isMatch returns the number of matched characters at the given position,
        // or 0 when "bc" does not start there. It only matches at index 1.
        assertEquals(0, matcher.isMatch(INPUT, 0), "no match at 'a'");
        assertEquals(2, matcher.isMatch(INPUT, 1), "'bc' matches at index 1 (2 chars)");
        assertEquals(0, matcher.isMatch(INPUT, 2), "no match at 'c'");
        assertEquals(0, matcher.isMatch(INPUT, 3), "no match at 'd'");
        assertEquals(0, matcher.isMatch(INPUT, 4), "no match at 'e'");
        assertEquals(0, matcher.isMatch(INPUT, 5), "no match at 'f'");

        // An empty or null search string yields the shared no-op matcher.
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(""));
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(null));
    }
}
