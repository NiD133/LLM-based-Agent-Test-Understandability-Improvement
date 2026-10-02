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

    /** Escape character configured on the substitutor under test. */
    private static final char ESCAPE_CHAR = 'b';

    @Test
    void replaceInOnNullStringBuilderReturnsFalse() {
        // Build a substitutor with an empty lookup, a tab matcher reused for the
        // variable prefix, suffix and value delimiter, and 'b' as the escape char.
        final StringLookup emptyLookup =
                StringLookupFactory.INSTANCE.mapStringLookup(new HashMap<String, Object>());
        final StringMatcher tabMatcher = StringMatcherFactory.INSTANCE.tabMatcher();
        final StringSubstitutor substitutor =
                new StringSubstitutor(emptyLookup, tabMatcher, tabMatcher, ESCAPE_CHAR, tabMatcher);

        // replaceIn on a null source returns false regardless of the offset/length
        // arguments (the null check short-circuits before they are used).
        final int offset = 1315;
        final int length = -1369;
        assertFalse(substitutor.replaceIn((StringBuilder) null, offset, length));

        // The constructor arguments are stored as configured.
        assertEquals(ESCAPE_CHAR, substitutor.getEscapeChar());
        assertFalse(substitutor.isPreserveEscapes());
    }
}
