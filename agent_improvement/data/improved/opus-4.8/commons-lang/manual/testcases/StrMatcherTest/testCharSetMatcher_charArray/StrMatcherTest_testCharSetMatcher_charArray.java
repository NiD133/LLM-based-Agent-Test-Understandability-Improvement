package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#charSetMatcher(char...)} — the factory that builds a
 * matcher from a set of characters supplied as a {@code char[]}.
 */
@Deprecated
public class StrMatcherTest_testCharSetMatcher_charArray extends AbstractLangTest {

    /** Result returned by {@code isMatch} when the character at the position matches. */
    private static final int MATCH = 1;

    /** Result returned by {@code isMatch} when the character at the position does not match. */
    private static final int NO_MATCH = 0;

    /** Text scanned below; characters at even indices ('a', 'c', 'e') are in the matcher's set. */
    private static final char[] INPUT = "abcdef".toCharArray();

    @Test
    void testCharSetMatcher_charArray() {
        // A matcher built from the character set {a, c, e}.
        final StrMatcher matcher = StrMatcher.charSetMatcher("ace".toCharArray());

        // Scanning "abcdef": the set members a, c, e match; b, d, f do not.
        assertEquals(MATCH, matcher.isMatch(INPUT, 0));    // 'a' is in the set
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 1)); // 'b' is not
        assertEquals(MATCH, matcher.isMatch(INPUT, 2));    // 'c' is in the set
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 3)); // 'd' is not
        assertEquals(MATCH, matcher.isMatch(INPUT, 4));    // 'e' is in the set
        assertEquals(NO_MATCH, matcher.isMatch(INPUT, 5)); // 'f' is not

        // An empty character set yields the shared no-op matcher.
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher());
        // A null character set is treated the same as empty.
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher((char[]) null));
        // A single-character set is optimized into a CharMatcher.
        assertInstanceOf(StrMatcher.CharMatcher.class, StrMatcher.charSetMatcher("a".toCharArray()));
    }
}
