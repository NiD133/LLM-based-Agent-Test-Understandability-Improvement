package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testCreatesStrSubstitutorTakingStrLookupAndCallsReplaceTakingTwoAndThreeInts {

    /**
     * A StrSubstitutor built from a StrLookup should:
     * <ul>
     *   <li>return {@code null} when asked to replace a {@code null} CharSequence, and</li>
     *   <li>expose the default escape character {@code '$'}.</li>
     * </ul>
     */
    @Test
    void testReplaceNullCharSequenceReturnsNullAndDefaultEscapeChar() {
        final Map<String, CharacterPredicates> emptyLookupValues = new HashMap<>();
        final StrLookup<CharacterPredicates> lookup = StrLookup.mapLookup(emptyLookupValues);
        final StrSubstitutor substitutor = new StrSubstitutor(lookup);

        final int offset = 0;
        final int length = 0;
        assertNull(substitutor.replace((CharSequence) null, offset, length));

        assertEquals('$', substitutor.getEscapeChar());
    }
}
