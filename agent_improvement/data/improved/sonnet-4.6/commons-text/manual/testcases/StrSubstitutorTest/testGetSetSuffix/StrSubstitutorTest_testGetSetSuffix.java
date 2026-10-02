package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetSuffix {

    /**
     * Tests that the variable suffix can be changed via setVariableSuffix(char),
     * setVariableSuffix(String), and setVariableSuffixMatcher(StrMatcher), and that
     * passing null to any setter throws IllegalArgumentException without altering
     * the current matcher.
     */
    @Test
    void testGetSetSuffix() {
        final StrSubstitutor sub = new StrSubstitutor();

        // Default state: both prefix and suffix use a StringMatcher (e.g. "${" and "}")
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting the suffix to a single char produces a CharMatcher for the suffix;
        // the prefix remains a StringMatcher.
        sub.setVariableSuffix('<');
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("CharMatcher", sub);

        // Setting the suffix back to a multi-char string restores a StringMatcher.
        sub.setVariableSuffix("<<");
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Passing null as the suffix string must throw and leave the matcher unchanged.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffix((String) null));
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // setVariableSuffixMatcher accepts any StrMatcher; the getter must return the
        // exact same instance that was set.
        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariableSuffixMatcher(matcher);
        assertSame(matcher, sub.getVariableSuffixMatcher());

        // Passing null as the StrMatcher must throw and leave the matcher unchanged.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariableSuffixMatcher());
    }
}
