package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingTwoAndThreeIntsReturningFalse {

    @Test
    void testReplaceInTakingTwoAndThreeIntsReturningFalse() {
        final Map<String, Object> emptyVariables = new HashMap<>();
        final StringLookup variableResolver = StringLookupFactory.INSTANCE.mapStringLookup(emptyVariables);
        final StringMatcher tabMatcher = StringMatcherFactory.INSTANCE.tabMatcher();
        final char escapeCharacter = 'b';
        final StringSubstitutor substitutor = new StringSubstitutor(
                variableResolver,
                tabMatcher,
                tabMatcher,
                escapeCharacter,
                tabMatcher);

        assertFalse(substitutor.replaceIn((StringBuilder) null, 1315, -1369));
        assertEquals(escapeCharacter, substitutor.getEscapeChar());
        assertFalse(substitutor.isPreserveEscapes());
    }
}
