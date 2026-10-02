package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testMatcherIndices extends AbstractLangTest {

    private static final char[] BUFFER = "abcdef".toCharArray();

    @Test
    void testMatcherIndices() {
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");
        final int matchStart = 1;
        final int activeBufferStart = 0;
        final int fullMatchEnd = 3;
        final int truncatedMatchEnd = 2;

        assertEquals(2, matcher.isMatch(BUFFER, matchStart, matchStart, BUFFER.length));
        assertEquals(2, matcher.isMatch(BUFFER, matchStart, activeBufferStart, fullMatchEnd));
        assertEquals(0, matcher.isMatch(BUFFER, matchStart, activeBufferStart, truncatedMatchEnd));
    }
}
