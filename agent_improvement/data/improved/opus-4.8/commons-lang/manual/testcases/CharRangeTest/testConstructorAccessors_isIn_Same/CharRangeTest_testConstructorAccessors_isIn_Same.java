package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Same extends AbstractLangTest {

    /**
     * Verifies that {@link CharRange#isIn(char, char)} with identical start and end
     * characters produces a non-negated, single-character range whose accessors and
     * string representation all reflect that one character.
     */
    @Test
    void testConstructorAccessors_isIn_Same() {
        final CharRange singleCharRange = CharRange.isIn('a', 'a');

        assertEquals('a', singleCharRange.getStart());
        assertEquals('a', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
        assertEquals("a", singleCharRange.toString());
    }
}
