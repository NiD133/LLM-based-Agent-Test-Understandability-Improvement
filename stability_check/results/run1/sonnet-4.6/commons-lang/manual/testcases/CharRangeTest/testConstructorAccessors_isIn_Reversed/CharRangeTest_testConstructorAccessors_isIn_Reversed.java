package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    /**
     * Verifies that CharRange.isIn() automatically normalises reversed arguments:
     * when the caller passes (end, start) instead of (start, end), the range
     * silently swaps them so that start <= end is always true.
     *
     * Input:  isIn('e', 'a')  — 'e' > 'a', so the arguments are deliberately reversed.
     * Expected result: a non-negated range from 'a' to 'e', i.e. "a-e".
     */
    @Test
    void testConstructorAccessors_isIn_Reversed() {
        // Construct a range with the start and end characters intentionally swapped.
        final CharRange reversedRange = CharRange.isIn('e', 'a');

        // The range should have been normalised: smaller char becomes start, larger becomes end.
        assertEquals('a', reversedRange.getStart(), "start should be the smaller character 'a'");
        assertEquals('e', reversedRange.getEnd(),   "end should be the larger character 'e'");

        // isIn() always creates a non-negated range.
        assertFalse(reversedRange.isNegated(), "isIn range must not be negated");

        // toString() renders the normalised range as "start-end".
        assertEquals("a-e", reversedRange.toString(), "string form should reflect the normalised order");
    }
}
