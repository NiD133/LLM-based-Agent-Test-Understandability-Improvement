package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#spaceMatcher()}, the matcher that recognizes a single
 * ASCII space character (' ').
 */
@Deprecated
public class StrMatcherTest_testSpaceMatcher extends AbstractLangTest {

    /**
     * Sample text laid out so a space sits between two non-space characters.
     * <pre>
     *   index : 0  1  2  3   4  5      6  ...
     *   char  : 0  ,  1  \t  2 (space) 3 ...
     * </pre>
     * Index 5 holds the only space among indices 4-6, so a space matcher must
     * match at index 5 and reject indices 4 and 6.
     */
    private static final char[] TEXT_WITH_SPACE_AT_INDEX_5 = "0,1\\t2 3\\n\\r\\f\\u0000'\"".toCharArray();

    private static final int NO_MATCH = 0;
    private static final int MATCHED_ONE_CHAR = 1;

    @Test
    void testSpaceMatcher() {
        final StrMatcher spaceMatcher = StrMatcher.spaceMatcher();

        // spaceMatcher() returns a shared singleton instance.
        assertSame(spaceMatcher, StrMatcher.spaceMatcher());

        // Index 4 is '2' -> no match.
        assertEquals(NO_MATCH, spaceMatcher.isMatch(TEXT_WITH_SPACE_AT_INDEX_5, 4));
        // Index 5 is the space -> matches one character.
        assertEquals(MATCHED_ONE_CHAR, spaceMatcher.isMatch(TEXT_WITH_SPACE_AT_INDEX_5, 5));
        // Index 6 is '3' -> no match.
        assertEquals(NO_MATCH, spaceMatcher.isMatch(TEXT_WITH_SPACE_AT_INDEX_5, 6));
    }
}
