package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharRange#isIn(char, char)} normalizes endpoints that are
 * supplied in reverse order. Building a range from {@code 'e'} down to {@code 'a'}
 * must produce the same range as {@code 'a'} to {@code 'e'}.
 */
public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Reversed() {
        // Endpoints are passed high-to-low; the range should swap them internally.
        final CharRange rangeFromReversedEndpoints = CharRange.isIn('e', 'a');

        assertEquals('a', rangeFromReversedEndpoints.getStart(), "start should be the lower character");
        assertEquals('e', rangeFromReversedEndpoints.getEnd(), "end should be the higher character");
        assertFalse(rangeFromReversedEndpoints.isNegated(), "isIn creates a non-negated range");
        assertEquals("a-e", rangeFromReversedEndpoints.toString(), "range renders in ascending order");
    }
}
