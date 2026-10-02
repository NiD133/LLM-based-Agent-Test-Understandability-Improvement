package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNotIn_Reversed extends AbstractLangTest {

    /**
     * Verifies that {@link CharRange#isNotIn(char, char)} normalizes endpoints
     * given in descending order. When called with {@code ('e', 'a')}, the range
     * should reorder them so that start is the smaller character ('a') and end is
     * the larger character ('e'), while still being marked as negated.
     */
    @Test
    void testConstructorAccessors_isNotIn_Reversed() {
        // Create a negated range with the endpoints supplied in reverse order.
        final CharRange negatedRange = CharRange.isNotIn('e', 'a');

        // Endpoints are reordered: 'a' becomes the start, 'e' becomes the end.
        assertEquals('a', negatedRange.getStart(), "start should be the smaller endpoint");
        assertEquals('e', negatedRange.getEnd(), "end should be the larger endpoint");

        // The range is negated (it represents everything outside 'a'-'e').
        assertTrue(negatedRange.isNegated(), "isNotIn should produce a negated range");

        // A negated, multi-character range renders as "^start-end".
        assertEquals("^a-e", negatedRange.toString(), "negated range should render with a leading '^'");
    }
}
