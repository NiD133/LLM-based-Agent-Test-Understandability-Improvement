package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the variable-prefix configuration of {@link StrSubstitutor}.
 *
 * <p>The prefix can be configured in three ways:</p>
 * <ul>
 *   <li>{@code setVariablePrefix(char)}   &rarr; backed by a {@code CharMatcher}</li>
 *   <li>{@code setVariablePrefix(String)} &rarr; backed by a {@code StringMatcher}</li>
 *   <li>{@code setVariablePrefixMatcher(StrMatcher)} &rarr; the matcher is stored as-is</li>
 * </ul>
 *
 * <p>{@link #assertPrefixImpl} / {@link #assertSuffixImpl} assert which {@link StrMatcher}
 * implementation currently backs the prefix / suffix. The suffix is never changed here, so
 * it must always remain a {@code StringMatcher}.</p>
 */
public class StrSubstitutorTest_testGetSetPrefix {

    /** Asserts the simple class name of the matcher currently used for the variable prefix. */
    private static void assertPrefixImpl(final String expectedMatcherClass, final StrSubstitutor sub) {
        assertEquals(expectedMatcherClass, sub.getVariablePrefixMatcher().getClass().getSimpleName());
    }

    /** Asserts the simple class name of the matcher currently used for the variable suffix. */
    private static void assertSuffixImpl(final String expectedMatcherClass, final StrSubstitutor sub) {
        assertEquals(expectedMatcherClass, sub.getVariableSuffixMatcher().getClass().getSimpleName());
    }

    @Test
    void testGetSetPrefix() {
        final StrSubstitutor sub = new StrSubstitutor();

        // Default: both prefix and suffix are StringMatchers.
        assertPrefixImpl("StringMatcher", sub);
        assertSuffixImpl("StringMatcher", sub);

        // A char prefix is stored as a CharMatcher; the suffix is left untouched.
        sub.setVariablePrefix('<');
        assertPrefixImpl("CharMatcher", sub);
        assertSuffixImpl("StringMatcher", sub);

        // A String prefix switches the backing matcher back to a StringMatcher.
        sub.setVariablePrefix("<<");
        assertPrefixImpl("StringMatcher", sub);
        assertSuffixImpl("StringMatcher", sub);

        // A null String prefix is rejected and leaves the existing matcher in place.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefix((String) null));
        assertPrefixImpl("StringMatcher", sub);
        assertSuffixImpl("StringMatcher", sub);

        // A custom matcher is stored and returned exactly as supplied.
        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariablePrefixMatcher(matcher);
        assertSame(matcher, sub.getVariablePrefixMatcher());

        // A null matcher is rejected and leaves the existing matcher in place.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariablePrefixMatcher());
    }
}
