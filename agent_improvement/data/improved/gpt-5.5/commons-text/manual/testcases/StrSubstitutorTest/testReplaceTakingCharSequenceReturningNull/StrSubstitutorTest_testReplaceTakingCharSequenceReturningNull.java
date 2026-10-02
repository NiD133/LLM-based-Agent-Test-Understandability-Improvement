package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceTakingCharSequenceReturningNull {

    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StrSubstitutor substitutorWithoutLookup = new StrSubstitutor((StrLookup<?>) null);

        assertNull(substitutorWithoutLookup.replace((CharSequence) null));
        assertFalse(substitutorWithoutLookup.isPreserveEscapes());
        assertEquals('$', substitutorWithoutLookup.getEscapeChar());
    }
}
