package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBuilderWithNonNull {

    private static final String PREFIX_AND_SUFFIX = "b<H";
    private static final char ESCAPE_CHARACTER = '\'';

    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final StrLookup<String> systemPropertiesLookup = StrLookup.systemPropertiesLookup();
        final StrSubstitutor substitutor = new StrSubstitutor(
                systemPropertiesLookup,
                PREFIX_AND_SUFFIX,
                PREFIX_AND_SUFFIX,
                ESCAPE_CHARACTER);
        final StringBuilder template = new StringBuilder((CharSequence) PREFIX_AND_SUFFIX);

        assertEquals(ESCAPE_CHARACTER, substitutor.getEscapeChar());
        assertFalse(substitutor.replaceIn(template));
    }
}
