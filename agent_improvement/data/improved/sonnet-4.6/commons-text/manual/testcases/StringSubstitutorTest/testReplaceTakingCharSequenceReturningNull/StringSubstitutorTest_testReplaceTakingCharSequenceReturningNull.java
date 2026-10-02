package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.lookup.StringLookup;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceTakingCharSequenceReturningNull {

    /**
     * Verifies that a StringSubstitutor created with a null StringLookup:
     * - returns null when replacing a null CharSequence
     * - has preserveEscapes disabled by default
     * - uses '$' as the default escape character
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StringSubstitutor substitutor = new StringSubstitutor((StringLookup) null);

        assertNull(substitutor.replace((CharSequence) null));
        assertFalse(substitutor.isPreserveEscapes());
        assertEquals('$', substitutor.getEscapeChar());
    }
}
