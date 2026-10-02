package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceTakingCharSequenceReturningNull {

    /**
     * Verifies that replacing a null CharSequence returns null when the substitutor
     * was constructed with a null resolver, and that the default escape character
     * and preserveEscapes flag remain at their expected defaults ('$' and false).
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StrSubstitutor substitutorWithNullResolver = new StrSubstitutor((StrLookup<?>) null);

        assertNull(substitutorWithNullResolver.replace((CharSequence) null));
        assertFalse(substitutorWithNullResolver.isPreserveEscapes());
        assertEquals('$', substitutorWithNullResolver.getEscapeChar());
    }
}
