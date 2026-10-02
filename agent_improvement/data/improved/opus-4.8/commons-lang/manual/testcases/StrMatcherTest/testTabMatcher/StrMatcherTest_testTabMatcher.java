package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher#tabMatcher()}, which matches a single tab character.
 */
@Deprecated
public class StrMatcherTest_testTabMatcher extends AbstractLangTest {

    /**
     * Sample text whose first characters are, by index:
     * <pre>
     *   index : 0    1    2    3     4
     *   char  : '0'  ','  '1'  tab   '2'
     * </pre>
     * Only index 3 holds a tab, so the tab matcher should match there and nowhere else.
     */
    private static final char[] TEXT = "0,1\\t2 3\\n\\r\\f\\u0000'\\\"".toCharArray();

    /** A successful match returns the number of matching characters; one for a single tab. */
    private static final int MATCH = 1;

    /** A failed match returns zero. */
    private static final int NO_MATCH = 0;

    @Test
    void testTabMatcher() {
        final StrMatcher tabMatcher = StrMatcher.tabMatcher();

        // tabMatcher() is a factory that always returns the same shared instance.
        assertSame(tabMatcher, StrMatcher.tabMatcher());

        // Index 2 holds '1', not a tab.
        assertEquals(NO_MATCH, tabMatcher.isMatch(TEXT, 2));
        // Index 3 holds the tab, the only matching position.
        assertEquals(MATCH, tabMatcher.isMatch(TEXT, 3));
        // Index 4 holds '2', not a tab.
        assertEquals(NO_MATCH, tabMatcher.isMatch(TEXT, 4));
    }
}
