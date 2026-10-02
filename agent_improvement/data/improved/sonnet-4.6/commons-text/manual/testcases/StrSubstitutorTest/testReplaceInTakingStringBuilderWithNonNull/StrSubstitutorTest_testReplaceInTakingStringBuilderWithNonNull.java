package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBuilderWithNonNull {

    /**
     * Verifies that replaceIn(StringBuilder) returns false (no substitution performed)
     * when the input contains only the variable prefix/suffix delimiter with no variable
     * content inside, and that the configured escape character is preserved correctly.
     *
     * The substitutor uses "b<H" as both prefix and suffix, so a variable would look like
     * "b<H...b<H". The input "b<H" matches only the delimiter itself — there is no closing
     * delimiter, so no variable is resolved and replaceIn reports false.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final String delimiter = "b<H";
        final char escapeChar = '\'';

        // Build a substitutor that reads from system properties and uses a custom delimiter pair
        final StrLookup<String> systemPropertiesLookup = StrLookup.systemPropertiesLookup();
        final StrSubstitutor substitutor = new StrSubstitutor(systemPropertiesLookup, delimiter, delimiter, escapeChar);

        // Input equals the delimiter string alone — no variable body, so no replacement can occur
        final StringBuilder input = new StringBuilder((CharSequence) delimiter);

        assertEquals(escapeChar, substitutor.getEscapeChar());
        assertFalse(substitutor.replaceIn(input));
    }
}
