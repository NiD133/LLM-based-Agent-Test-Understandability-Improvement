package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testStringMatcher_String extends AbstractLangTest {

    // Buffer used to test substring matching: positions map as a=0, b=1, c=2, d=3, e=4, f=5
    private static final char[] BUFFER2 = "abcdef".toCharArray();

    // Return values from isMatch: 0 means no match, 2 means the two-character string "bc" was matched
    private static final int NO_MATCH = 0;
    private static final int MATCH_LENGTH = 2; // length of the pattern "bc"

    @Test
    void testStringMatcher_String() {
        // A StringMatcher for "bc" should match exactly at position 1 in "abcdef"
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");

        assertEquals(NO_MATCH,    matcher.isMatch(BUFFER2, 0)); // 'a'  — pattern does not start here
        assertEquals(MATCH_LENGTH, matcher.isMatch(BUFFER2, 1)); // 'b'  — "bc" starts here, returns match length 2
        assertEquals(NO_MATCH,    matcher.isMatch(BUFFER2, 2)); // 'c'  — only 'c', not "bc"
        assertEquals(NO_MATCH,    matcher.isMatch(BUFFER2, 3)); // 'd'
        assertEquals(NO_MATCH,    matcher.isMatch(BUFFER2, 4)); // 'e'
        assertEquals(NO_MATCH,    matcher.isMatch(BUFFER2, 5)); // 'f'

        // Empty string and null should both return the shared noneMatcher singleton (no-op matcher)
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(""));
        assertSame(StrMatcher.noneMatcher(), StrMatcher.stringMatcher(null));
    }
}
