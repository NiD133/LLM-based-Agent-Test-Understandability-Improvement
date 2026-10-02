package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testCreatesStrSubstitutorTakingStrLookupAndCallsReplaceTakingTwoAndThreeInts {

    @Test
    void testCreatesStrSubstitutorTakingStrLookupAndCallsReplaceTakingTwoAndThreeInts() {
        final Map<String, CharacterPredicates> values = new HashMap<>();
        final StrLookup<CharacterPredicates> lookup = StrLookup.mapLookup(values);
        final StrSubstitutor substitutor = new StrSubstitutor(lookup);

        assertNull(substitutor.replace((CharSequence) null, 0, 0));
        assertEquals('$', substitutor.getEscapeChar());
    }
}
