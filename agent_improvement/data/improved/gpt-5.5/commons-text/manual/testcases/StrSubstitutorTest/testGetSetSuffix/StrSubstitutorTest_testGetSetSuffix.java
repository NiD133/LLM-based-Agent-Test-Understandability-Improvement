package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetSuffix {

    private static final String STRING_MATCHER = "StringMatcher";

    private static void assertDefaultPrefixAndSuffix(final StrSubstitutor substitutor) {
        assertPrefixAndSuffix(substitutor, STRING_MATCHER, STRING_MATCHER);
    }

    private static void assertDefaultPrefixAndCharSuffix(final StrSubstitutor substitutor) {
        assertPrefixAndSuffix(substitutor, STRING_MATCHER, "CharMatcher");
    }

    private static void assertPrefixAndSuffix(final StrSubstitutor substitutor, final String expectedPrefix,
            final String expectedSuffix) {
        StrMatcherTest.assertStrMatcherPrefixImpl(expectedPrefix, substitutor);
        StrMatcherTest.assertStrMatcherSuffixImpl(expectedSuffix, substitutor);
    }

    /**
     * Tests get set.
     */
    @Test
    void testGetSetSuffix() {
        final StrSubstitutor substitutor = new StrSubstitutor();
        assertDefaultPrefixAndSuffix(substitutor);

        substitutor.setVariableSuffix('<');
        assertDefaultPrefixAndCharSuffix(substitutor);

        substitutor.setVariableSuffix("<<");
        assertDefaultPrefixAndSuffix(substitutor);

        assertThrows(IllegalArgumentException.class, () -> substitutor.setVariableSuffix((String) null));
        assertDefaultPrefixAndSuffix(substitutor);

        final StrMatcher matcher = StrMatcher.commaMatcher();
        substitutor.setVariableSuffixMatcher(matcher);
        assertSame(matcher, substitutor.getVariableSuffixMatcher());

        assertThrows(IllegalArgumentException.class, () -> substitutor.setVariableSuffixMatcher((StrMatcher) null));
        assertSame(matcher, substitutor.getVariableSuffixMatcher());
    }
}
