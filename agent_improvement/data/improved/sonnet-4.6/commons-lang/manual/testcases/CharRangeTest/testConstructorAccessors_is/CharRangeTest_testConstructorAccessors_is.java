package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_is extends AbstractLangTest {

    /**
     * CharRange.is(ch) creates a single-character, non-negated range.
     * Verifies that start, end, negation flag, and string representation are all set correctly.
     */
    @Test
    @DisplayName("CharRange.is('a') produces a single-character non-negated range for 'a'")
    void testConstructorAccessors_is() {
        final CharRange singleCharRange = CharRange.is('a');

        // A single-character range must have identical start and end
        assertEquals('a', singleCharRange.getStart(), "start should be the supplied character");
        assertEquals('a', singleCharRange.getEnd(),   "end should equal start for a single-character range");

        // CharRange.is() constructs a positive (non-negated) range
        assertFalse(singleCharRange.isNegated(), "a range created with is() must not be negated");

        // toString() of a single, non-negated character is just that character
        assertEquals("a", singleCharRange.toString(), "toString() should return the character itself");
    }
}
