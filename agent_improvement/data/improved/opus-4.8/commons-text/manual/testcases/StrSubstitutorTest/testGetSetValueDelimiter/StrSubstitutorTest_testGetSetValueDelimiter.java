package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Verifies the getters and setters for the "value delimiter" of {@link StrSubstitutor}.
 *
 * <p>The value delimiter separates a variable name from its default value (for example the
 * {@code ':'} in {@code ${name:-default}}). It can be configured through three setters:
 * {@link StrSubstitutor#setValueDelimiter(char)}, {@link StrSubstitutor#setValueDelimiter(String)}
 * and {@link StrSubstitutor#setValueDelimiterMatcher(StrMatcher)}.</p>
 */
public class StrSubstitutorTest_testGetSetValueDelimiter {

    @Test
    void testGetSetValueDelimiter() {
        final StrSubstitutor sub = new StrSubstitutor();

        // The variable prefix/suffix matchers are unaffected by changes to the value delimiter.
        // Re-check them after every value-delimiter change to confirm they stay the default
        // string matchers.
        assertPrefixAndSuffixAreStringMatchers(sub);

        // Set the value delimiter via a single character.
        sub.setValueDelimiter(':');
        assertPrefixAndSuffixAreStringMatchers(sub);

        // Set the value delimiter via a string.
        sub.setValueDelimiter("||");
        assertPrefixAndSuffixAreStringMatchers(sub);

        // Setting a null string delimiter clears the value-delimiter matcher.
        sub.setValueDelimiter((String) null);
        assertNull(sub.getValueDelimiterMatcher());

        // Setting an explicit matcher stores exactly that instance.
        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setValueDelimiterMatcher(matcher);
        assertSame(matcher, sub.getValueDelimiterMatcher());

        // Setting a null matcher clears the value-delimiter matcher again.
        sub.setValueDelimiterMatcher((StrMatcher) null);
        assertNull(sub.getValueDelimiterMatcher());
    }

    /**
     * Asserts that both the variable prefix and suffix matchers of the given substitutor are the
     * default {@code StringMatcher} implementation (identified by its simple class name).
     */
    private static void assertPrefixAndSuffixAreStringMatchers(final StrSubstitutor sub) {
        assertEquals("StringMatcher", sub.getVariablePrefixMatcher().getClass().getSimpleName());
        assertEquals("StringMatcher", sub.getVariableSuffixMatcher().getClass().getSimpleName());
    }
}
