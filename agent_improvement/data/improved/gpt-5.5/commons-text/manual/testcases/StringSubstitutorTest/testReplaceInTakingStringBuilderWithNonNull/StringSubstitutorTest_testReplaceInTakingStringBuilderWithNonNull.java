package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingStringBuilderWithNonNull {

    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final StringLookup systemPropertyLookup = StringLookupFactory.INSTANCE.systemPropertyStringLookup();
        final StringSubstitutor substitutor = new StringSubstitutor(systemPropertyLookup, "b<H", "b<H", '\'');
        final StringBuilder source = new StringBuilder((CharSequence) "b<H");

        assertEquals('\'', substitutor.getEscapeChar());
        assertFalse(substitutor.replaceIn(source));
    }
}
