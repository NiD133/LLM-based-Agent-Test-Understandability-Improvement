package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testCreatesStrSubstitutorTakingStrLookupAndCallsReplaceTakingTwoAndThreeInts {

    @Test
    void testCreatesStrSubstitutorTakingStrLookupAndCallsReplaceTakingTwoAndThreeInts() {
        // Create a StrSubstitutor backed by a StrLookup over an empty map
        final Map<String, CharacterPredicates> map = new HashMap<>();
        final StrLookup<CharacterPredicates> lookup = StrLookup.mapLookup(map);
        final StrSubstitutor sub = new StrSubstitutor(lookup);

        // replace(CharSequence, int, int) on null input should return null
        assertNull(sub.replace((CharSequence) null, 0, 0));

        // Default escape character is '$'
        assertEquals('$', sub.getEscapeChar());
    }
}
