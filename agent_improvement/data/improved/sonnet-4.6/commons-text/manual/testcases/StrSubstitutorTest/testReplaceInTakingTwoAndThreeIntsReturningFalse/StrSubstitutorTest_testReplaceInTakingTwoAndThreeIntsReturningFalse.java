package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingTwoAndThreeIntsReturningFalse {

    @Test
    void testReplaceInTakingTwoAndThreeIntsReturningFalse() {
        // Set up a substitutor with an empty variable map, tab as prefix/suffix/delimiter,
        // and 'b' as the escape character.
        final Map<String, Object> emptyVariableMap = new HashMap<>();
        final StrLookup<Object> emptyMapLookup = StrLookup.mapLookup(emptyVariableMap);
        final StrMatcher tabMatcher = StrMatcher.tabMatcher();
        final StrSubstitutor substitutor = new StrSubstitutor(
                emptyMapLookup, tabMatcher, tabMatcher, 'b', tabMatcher);

        // replaceIn(StringBuilder, offset, length) must return false when source is null.
        assertFalse(substitutor.replaceIn((StringBuilder) null, 1315, -1369));

        // The escape character supplied to the constructor must be stored as-is.
        assertEquals('b', substitutor.getEscapeChar());

        // Preserve-escapes mode is off by default.
        assertFalse(substitutor.isPreserveEscapes());
    }
}
