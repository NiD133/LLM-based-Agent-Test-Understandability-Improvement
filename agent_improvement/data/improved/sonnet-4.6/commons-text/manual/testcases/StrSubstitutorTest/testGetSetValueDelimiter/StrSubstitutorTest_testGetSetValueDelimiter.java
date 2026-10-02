package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetValueDelimiter {

    /**
     * Tests that setValueDelimiter(char), setValueDelimiter(String), and
     * setValueDelimiterMatcher(StrMatcher) all update the value-delimiter matcher
     * correctly, and that passing null clears it to null.
     *
     * Prefix/suffix matchers must remain as StringMatcher instances throughout
     * because setValueDelimiter only affects the value delimiter, not the variable
     * prefix or suffix.
     */
    @Test
    void testGetSetValueDelimiter() {
        final StrSubstitutor sub = new StrSubstitutor();

        // Initially the prefix and suffix matchers are StringMatcher instances.
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting a single-char value delimiter wraps it in a CharMatcher internally,
        // but must not touch the variable prefix or suffix matchers.
        sub.setValueDelimiter(':');
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting a multi-char value delimiter wraps it in a StringMatcher internally,
        // and must likewise leave the variable prefix and suffix matchers unchanged.
        sub.setValueDelimiter("||");
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Passing null as the String delimiter clears the value-delimiter matcher to null.
        sub.setValueDelimiter((String) null);
        assertNull(sub.getValueDelimiterMatcher());

        // setValueDelimiterMatcher stores the provided matcher and getValueDelimiterMatcher
        // returns the exact same instance (identity, not equality).
        final StrMatcher commaMatcher = StrMatcher.commaMatcher();
        sub.setValueDelimiterMatcher(commaMatcher);
        assertSame(commaMatcher, sub.getValueDelimiterMatcher());

        // Passing null as the StrMatcher clears the value-delimiter matcher back to null.
        sub.setValueDelimiterMatcher((StrMatcher) null);
        assertNull(sub.getValueDelimiterMatcher());
    }
}
