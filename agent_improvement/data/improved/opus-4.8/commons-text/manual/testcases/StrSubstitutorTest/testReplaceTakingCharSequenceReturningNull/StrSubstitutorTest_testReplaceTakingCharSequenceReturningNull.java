package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceTakingCharSequenceReturningNull {

    /**
     * When a substitutor is built with a null lookup, replacing a null
     * CharSequence yields null and the substitutor keeps its default
     * configuration (escapes are not preserved, '$' is the escape char).
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StrSubstitutor strSubstitutor = new StrSubstitutor((StrLookup<?>) null);

        assertNull(strSubstitutor.replace((CharSequence) null));
        assertFalse(strSubstitutor.isPreserveEscapes());
        assertEquals('$', strSubstitutor.getEscapeChar());
    }
}
