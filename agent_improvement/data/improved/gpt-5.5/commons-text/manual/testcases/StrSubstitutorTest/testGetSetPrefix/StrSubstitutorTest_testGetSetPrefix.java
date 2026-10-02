package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetPrefix {

    private static final String CHAR_MATCHER = "CharMatcher";
    private static final String STRING_MATCHER = "StringMatcher";

    private static void assertPrefixAndSuffixMatcherTypes(
            final StrSubstitutor substitutor,
            final String expectedPrefixMatcherType,
            final String expectedSuffixMatcherType) {
        StrMatcherTest.assertStrMatcherPrefixImpl(expectedPrefixMatcherType, substitutor);
        StrMatcherTest.assertStrMatcherSuffixImpl(expectedSuffixMatcherType, substitutor);
    }

    /**
     * Tests that each prefix setter updates only the prefix matcher, rejects null input,
     * and leaves the previously configured matcher in place after a failed update.
     */
    @Test
    void testGetSetPrefix() {
        final StrSubstitutor sub = new StrSubstitutor();

        assertPrefixAndSuffixMatcherTypes(sub, STRING_MATCHER, STRING_MATCHER);

        sub.setVariablePrefix('<');
        assertPrefixAndSuffixMatcherTypes(sub, CHAR_MATCHER, STRING_MATCHER);

        sub.setVariablePrefix("<<");
        assertPrefixAndSuffixMatcherTypes(sub, STRING_MATCHER, STRING_MATCHER);

        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefix((String) null));
        assertPrefixAndSuffixMatcherTypes(sub, STRING_MATCHER, STRING_MATCHER);

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariablePrefixMatcher(matcher);
        assertSame(matcher, sub.getVariablePrefixMatcher());

        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariablePrefixMatcher());
    }
}
