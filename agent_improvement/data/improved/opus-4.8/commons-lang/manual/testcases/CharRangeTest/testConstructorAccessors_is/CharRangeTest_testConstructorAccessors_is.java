package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CharRange#is(char)} builds a non-negated, single-character
 * range and that its accessors expose that single character correctly.
 */
public class CharRangeTest_testConstructorAccessors_is extends AbstractLangTest {

    /** The single character used to build the range under test. */
    private static final char SINGLE_CHAR = 'a';

    @Test
    void testConstructorAccessors_is() {
        final CharRange range = CharRange.is(SINGLE_CHAR);

        // A single-character range starts and ends at the same character...
        assertEquals(SINGLE_CHAR, range.getStart());
        assertEquals(SINGLE_CHAR, range.getEnd());
        // ...is not negated...
        assertFalse(range.isNegated());
        // ...and renders as just that character.
        assertEquals("a", range.toString());
    }
}
