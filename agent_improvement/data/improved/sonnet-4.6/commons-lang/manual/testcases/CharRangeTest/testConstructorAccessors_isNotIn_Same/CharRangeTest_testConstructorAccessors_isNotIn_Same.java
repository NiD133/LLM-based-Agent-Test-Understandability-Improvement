package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNotIn_Same extends AbstractLangTest {

    /**
     * Verifies that {@code CharRange.isNotIn('a', 'a')} produces a negated single-character
     * range — i.e. a range that excludes only 'a' and includes every other character.
     *
     * <p>When both endpoints are identical the range is collapsed to a single character, so
     * {@code start} and {@code end} must both equal 'a'.  Because the range is negated the
     * string representation uses the '^' prefix.</p>
     */
    @Test
    @DisplayName("isNotIn with identical start and end produces a negated single-character range")
    void testConstructorAccessors_isNotIn_Same() {
        // A negated range whose start == end spans exactly one excluded character.
        final CharRange excludeOnlyA = CharRange.isNotIn('a', 'a');

        // The range boundaries must both be 'a' (no normalization needed when endpoints are equal).
        assertEquals('a', excludeOnlyA.getStart(), "start should be 'a'");
        assertEquals('a', excludeOnlyA.getEnd(),   "end should be 'a'");

        // isNotIn always produces a negated range.
        assertTrue(excludeOnlyA.isNegated(), "range created with isNotIn must be negated");

        // A single-character negated range is rendered as "^<char>" with no dash.
        assertEquals("^a", excludeOnlyA.toString(), "toString of a negated single-char range should be '^a'");
    }
}
