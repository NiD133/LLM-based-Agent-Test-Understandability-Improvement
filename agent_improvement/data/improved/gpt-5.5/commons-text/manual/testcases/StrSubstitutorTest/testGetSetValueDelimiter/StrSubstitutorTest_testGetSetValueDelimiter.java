package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetValueDelimiter {

    private static void assertDefaultVariableMatchers(final StrSubstitutor substitutor) {
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", substitutor);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", substitutor);
    }

    /**
     * Tests get set.
     */
    @Test
    void testGetSetValueDelimiter() {
        final StrSubstitutor sub = new StrSubstitutor();

        assertDefaultVariableMatchers(sub);

        sub.setValueDelimiter(':');
        assertDefaultVariableMatchers(sub);

        sub.setValueDelimiter("||");
        assertDefaultVariableMatchers(sub);

        sub.setValueDelimiter((String) null);
        assertNull(sub.getValueDelimiterMatcher());

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setValueDelimiterMatcher(matcher);
        assertSame(matcher, sub.getValueDelimiterMatcher());

        sub.setValueDelimiterMatcher((StrMatcher) null);
        assertNull(sub.getValueDelimiterMatcher());
    }
}
