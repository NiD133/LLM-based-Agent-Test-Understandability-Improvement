package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingStringBuilderWithNonNull {

    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        // Use system-property lookup; the specific lookup source does not affect this test
        // because the StringBuilder content contains no resolvable variable expression.
        final StringLookup systemPropertyLookup = StringLookupFactory.INSTANCE.systemPropertyStringLookup();

        // Construct a substitutor whose variable prefix, suffix, and escape character are all
        // custom: prefix="b<H", suffix="b<H", escapeChar='\''.
        final StringSubstitutor substitutor = new StringSubstitutor(systemPropertyLookup, "b<H", "b<H", '\'');

        // Verify that the custom escape character was stored correctly.
        assertEquals('\'', substitutor.getEscapeChar());

        // The StringBuilder contains only "b<H", which matches the variable prefix but not a
        // complete "b<H...b<H" expression, so no substitution should occur and replaceIn
        // must return false.
        final StringBuilder input = new StringBuilder((CharSequence) "b<H");
        assertFalse(substitutor.replaceIn(input));
    }
}
