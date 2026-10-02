package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class StrMatcherTest_testTabMatcher {

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
    void testTabMatcher() {
        final StrMatcher matcher = StrMatcher.tabMatcher();
        assertSame(StrMatcher.tabMatcher(), matcher);
        assertEquals(0, matcher.isMatch(BUFFER1, 2));
        assertEquals(1, matcher.isMatch(BUFFER1, 3));
        assertEquals(0, matcher.isMatch(BUFFER1, 4));
    }
}
