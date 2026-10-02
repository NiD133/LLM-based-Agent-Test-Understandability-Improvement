package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor#replaceIn(StringBuilder, int, int)} returns {@code false}
 * (no substitution performed) when given a {@code null} source, and that the substitutor faithfully
 * exposes the escape character and preserve-escapes flag it was constructed with.
 */
public class StrSubstitutorTest_testReplaceInTakingTwoAndThreeIntsReturningFalse {

    @Test
    void replaceInNullStringBuilderReturnsFalseAndConstructorSettingsArePreserved() {
        // Build a substitutor with a tab matcher for prefix/suffix/value-delimiter and 'b' as the escape char.
        final Map<String, Object> noVariables = new HashMap<>();
        final StrLookup<Object> lookup = StrLookup.mapLookup(noVariables);
        final StrMatcher tabMatcher = StrMatcher.tabMatcher();
        final char escapeChar = 'b';
        final StrSubstitutor substitutor =
                new StrSubstitutor(lookup, tabMatcher, tabMatcher, escapeChar, tabMatcher);

        // A null source cannot be modified, so replaceIn must report that nothing was replaced.
        final int offset = 1315;
        final int length = -1369;
        assertFalse(substitutor.replaceIn((StringBuilder) null, offset, length));

        // The substitutor should expose exactly the settings it was constructed with.
        assertEquals(escapeChar, substitutor.getEscapeChar());
        assertFalse(substitutor.isPreserveEscapes());
    }
}
