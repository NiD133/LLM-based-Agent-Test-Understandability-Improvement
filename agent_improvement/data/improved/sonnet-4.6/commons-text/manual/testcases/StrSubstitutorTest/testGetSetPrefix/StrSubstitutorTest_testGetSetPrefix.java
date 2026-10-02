package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testGetSetPrefix {

    /**
     * Tests that setting the variable prefix via char, String, and StrMatcher
     * correctly updates the internal prefix matcher, and that null arguments
     * are rejected with IllegalArgumentException without changing state.
     */
    @Test
    void testGetSetPrefix() {
        final StrSubstitutor sub = new StrSubstitutor();

        // Default constructor uses "${" (String-based) prefix and "}" (String-based) suffix.
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting a single-char prefix switches the prefix to a CharMatcher;
        // the suffix remains a StringMatcher.
        sub.setVariablePrefix('<');
        StrMatcherTest.assertStrMatcherPrefixImpl("CharMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting a multi-char String prefix switches the prefix back to a StringMatcher.
        sub.setVariablePrefix("<<");
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // A null String prefix must be rejected; existing prefix must be unchanged.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefix((String) null));
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting a custom StrMatcher via setVariablePrefixMatcher stores the exact instance.
        final StrMatcher commaMatcher = StrMatcher.commaMatcher();
        sub.setVariablePrefixMatcher(commaMatcher);
        assertSame(commaMatcher, sub.getVariablePrefixMatcher());

        // A null StrMatcher must be rejected; existing prefix matcher must be unchanged.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefixMatcher((StrMatcher) null));
        assertSame(commaMatcher, sub.getVariablePrefixMatcher());
    }
}
