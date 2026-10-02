package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the variable-suffix configuration of {@link StrSubstitutor}.
 *
 * <p>The suffix can be set in three ways, and each call swaps in a different
 * {@link StrMatcher} implementation:</p>
 * <ul>
 *   <li>by default it is a multi-character {@code StringMatcher} (for {@code "}"}),</li>
 *   <li>{@link StrSubstitutor#setVariableSuffix(char)} installs a single-character {@code CharMatcher},</li>
 *   <li>{@link StrSubstitutor#setVariableSuffix(String)} installs a {@code StringMatcher} again.</li>
 * </ul>
 *
 * <p>Each section below verifies the concrete matcher type behind the suffix while
 * confirming the prefix matcher is left untouched. The helper
 * {@code StrMatcherTest.assertStrMatcher*Impl} asserts the matcher's simple class name.</p>
 */
public class StrSubstitutorTest_testGetSetSuffix {

    @Test
    void testGetSetSuffix() {
        final StrSubstitutor sub = new StrSubstitutor();

        // Default configuration: both prefix and suffix use a StringMatcher.
        assertPrefixMatcherType("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // Setting a single-character suffix switches the suffix to a CharMatcher.
        sub.setVariableSuffix('<');
        assertPrefixMatcherType("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("CharMatcher", sub);

        // Setting a multi-character suffix switches the suffix back to a StringMatcher.
        sub.setVariableSuffix("<<");
        assertPrefixMatcherType("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // A null String suffix is rejected and leaves the existing matchers unchanged.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffix((String) null));
        assertPrefixMatcherType("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        // A custom suffix matcher can be set and read back.
        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariableSuffixMatcher(matcher);
        assertSame(matcher, sub.getVariableSuffixMatcher());

        // A null suffix matcher is rejected and the previously set matcher is retained.
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariableSuffixMatcher());
    }
}
