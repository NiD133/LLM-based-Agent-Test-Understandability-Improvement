package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.text.lookup.StringLookup;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replace(CharSequence)} when the substitutor has no variable resolver.
 */
public class StringSubstitutorTest_testReplaceTakingCharSequenceReturningNull {

    /**
     * Replacing a {@code null} CharSequence should return {@code null}, even when the substitutor was
     * built without a variable resolver. The constructor and accessors should still expose the
     * documented defaults: escaping is not preserved and the escape character is {@code '$'}.
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StringSubstitutor substitutorWithoutResolver = new StringSubstitutor((StringLookup) null);

        assertNull(substitutorWithoutResolver.replace((CharSequence) null));
        assertFalse(substitutorWithoutResolver.isPreserveEscapes());
        assertEquals('$', substitutorWithoutResolver.getEscapeChar());
    }
}
