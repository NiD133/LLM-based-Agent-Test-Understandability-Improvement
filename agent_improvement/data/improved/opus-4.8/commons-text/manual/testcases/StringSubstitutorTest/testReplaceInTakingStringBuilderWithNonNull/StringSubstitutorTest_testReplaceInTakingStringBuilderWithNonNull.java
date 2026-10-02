package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingStringBuilderWithNonNull {

    /**
     * When the variable prefix and suffix are configured to the same literal text
     * ("b<H") and the target {@link StringBuilder} contains exactly that text, there
     * is no enclosed variable name to resolve. The substitutor must therefore leave
     * the builder unchanged and report that no replacement happened.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final String prefixAndSuffix = "b<H";
        final char escapeChar = '\'';

        final StringLookup systemPropertyLookup = StringLookupFactory.INSTANCE.systemPropertyStringLookup();
        final StringSubstitutor substitutor =
                new StringSubstitutor(systemPropertyLookup, prefixAndSuffix, prefixAndSuffix, escapeChar);
        final StringBuilder target = new StringBuilder(prefixAndSuffix);

        assertEquals(escapeChar, substitutor.getEscapeChar());

        final boolean replacementHappened = substitutor.replaceIn(target);
        assertFalse(replacementHappened);
    }
}
