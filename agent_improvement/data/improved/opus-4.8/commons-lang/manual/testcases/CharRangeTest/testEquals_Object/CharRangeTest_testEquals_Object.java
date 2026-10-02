package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharRange#equals(Object)}.
 *
 * <p>Two {@link CharRange} instances are equal only when they describe the
 * same start, end and negation. This test verifies the contract from three
 * angles: a range is never equal to {@code null}, a range is equal to itself
 * and to any other range built the same way, and ranges that cover different
 * characters are not equal.</p>
 */
public class CharRangeTest_testEquals_Object extends AbstractLangTest {

    @Test
    void testEquals_Object() {
        // Three distinct ranges: a single character and two different spans.
        final CharRange single_a = CharRange.is('a');
        final CharRange span_a_to_e = CharRange.isIn('a', 'e');
        final CharRange span_b_to_f = CharRange.isIn('b', 'f');

        // A range is never equal to null.
        assertNotEquals(null, single_a);

        // A range equals itself and any range constructed identically.
        assertEquals(single_a, single_a);
        assertEquals(single_a, CharRange.is('a'));
        assertEquals(span_a_to_e, span_a_to_e);
        assertEquals(span_a_to_e, CharRange.isIn('a', 'e'));
        assertEquals(span_b_to_f, span_b_to_f);
        assertEquals(span_b_to_f, CharRange.isIn('b', 'f'));

        // Ranges covering different characters are not equal, in either order.
        assertNotEquals(single_a, span_a_to_e);
        assertNotEquals(single_a, span_b_to_f);
        assertNotEquals(span_a_to_e, single_a);
        assertNotEquals(span_a_to_e, span_b_to_f);
        assertNotEquals(span_b_to_f, single_a);
        assertNotEquals(span_b_to_f, span_a_to_e);
    }
}
