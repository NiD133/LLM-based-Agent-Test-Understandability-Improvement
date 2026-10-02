package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class StrMatcherTest_testCharMatcher_char {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    static void assertStrMatcherImpl(final String internalSimpleName, final StrMatcher obj) {
        assertEquals(StrMatcher.class.getName() + "$" + internalSimpleName, obj.getClass().getName());
    }

    static void assertStrMatcherPrefixImpl(final String internalSimpleName, final StrSubstitutor obj) {
        assertStrMatcherImpl(internalSimpleName, obj.getVariablePrefixMatcher());
    }

    static void assertStrMatcherSuffixImpl(final String internalSimpleName, final StrSubstitutor obj) {
        assertStrMatcherImpl(internalSimpleName, obj.getVariableSuffixMatcher());
    }

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
