package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#charSetMatcher(String)}.
 */
@Deprecated
public class StrMatcherTest_testCharSetMatcher_String extends AbstractLangTest {

    /** Result returned by {@code isMatch} when the character at the position matches. */
    private static final int MATCH = 1;

    /** Result returned by {@code isMatch} when the character at the position does not match. */
    private static final int NO_MATCH = 0;

    @Test
    void testCharSetMatcher_String() {
        // A matcher built from the set {a, c, e} should match exactly those characters.
        final StrMatcher matcher = StrMatcher.charSetMatcher("ace");
        final char[] text = "abcdef".toCharArray();

        assertEquals(MATCH,    matcher.isMatch(text, 0), "'a' is in the set");
        assertEquals(NO_MATCH, matcher.isMatch(text, 1), "'b' is not in the set");
        assertEquals(MATCH,    matcher.isMatch(text, 2), "'c' is in the set");
        assertEquals(NO_MATCH, matcher.isMatch(text, 3), "'d' is not in the set");
        assertEquals(MATCH,    matcher.isMatch(text, 4), "'e' is in the set");
        assertEquals(NO_MATCH, matcher.isMatch(text, 5), "'f' is not in the set");

        // An empty or null character set yields the shared "matches nothing" matcher.
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher(""),
                "empty set returns the none matcher");
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher((String) null),
                "null set returns the none matcher");

        // A single-character set is optimised into a CharMatcher.
        assertInstanceOf(StrMatcher.CharMatcher.class, StrMatcher.charSetMatcher("a"),
                "single-character set returns a CharMatcher");
    }
}
