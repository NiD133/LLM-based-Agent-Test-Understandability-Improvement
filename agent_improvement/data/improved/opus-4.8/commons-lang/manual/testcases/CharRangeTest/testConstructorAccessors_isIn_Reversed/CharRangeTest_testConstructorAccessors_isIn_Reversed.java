package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharRange#isIn(char, char)} normalizes a reversed
 * start/end pair: when the start character comes after the end character,
 * the range constructor swaps them so that {@code start <= end}.
 */
public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Reversed() {
        // Build the range with the endpoints intentionally reversed ('e' before 'a').
        final CharRange range = CharRange.isIn('e', 'a');

        // The endpoints are swapped so the lower character becomes the start.
        assertEquals('a', range.getStart(), "start should be the lower endpoint after reversal");
        assertEquals('e', range.getEnd(), "end should be the higher endpoint after reversal");

        // isIn produces a normal (non-negated) range.
        assertFalse(range.isNegated(), "isIn ranges are never negated");

        // The string form reflects the normalized, ascending order.
        assertEquals("a-e", range.toString(), "reversed range renders in ascending order");
    }
}
