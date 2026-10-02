package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingTwoAndThreeIntsReturningFalse {

    @Test
    void testReplaceInWithNullStringBuilderReturnsFalseAndKeepsConstructorDefaults() {
        final Map<String, Object> variables = new HashMap<>();
        final StrLookup<Object> variableResolver = StrLookup.mapLookup(variables);
        final StrMatcher tabMatcher = StrMatcher.tabMatcher();
        final StrSubstitutor substitutor = new StrSubstitutor(
                variableResolver,
                tabMatcher,
                tabMatcher,
                'b',
                tabMatcher);

        assertFalse(substitutor.replaceIn((StringBuilder) null, 1315, -1369));
        assertEquals('b', substitutor.getEscapeChar());
        assertFalse(substitutor.isPreserveEscapes());
    }
}
