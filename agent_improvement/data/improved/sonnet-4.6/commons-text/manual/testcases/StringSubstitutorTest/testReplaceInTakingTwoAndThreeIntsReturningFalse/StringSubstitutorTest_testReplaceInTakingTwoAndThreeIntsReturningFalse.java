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
        // Build a substitutor backed by an empty lookup, using the tab character as
        // prefix/suffix/value-delimiter matchers and 'b' as the escape character.
        final Map<String, Object> emptyLookupMap = new HashMap<>();
        final StringLookup mapLookup = StringLookupFactory.INSTANCE.mapStringLookup(emptyLookupMap);
        final StringMatcher tabMatcher = StringMatcherFactory.INSTANCE.tabMatcher();
        final StringSubstitutor substitutor =
                new StringSubstitutor(mapLookup, tabMatcher, tabMatcher, 'b', tabMatcher);

        // replaceIn(StringBuilder, offset, length) must return false when the source is null,
        // regardless of the offset (1315) and length (-1369) supplied.
        assertFalse(substitutor.replaceIn((StringBuilder) null, 1315, -1369));

        // Confirm that the escape character was recorded as supplied.
        assertEquals('b', substitutor.getEscapeChar());

        // Confirm that escape preservation is disabled by default.
        assertFalse(substitutor.isPreserveEscapes());
    }
}
