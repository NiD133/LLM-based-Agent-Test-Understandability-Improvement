package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharRange#isNotIn(char, char)} builds a negated range
 * whose start, end, negation flag and string form reflect the given endpoints.
 */
public class CharRangeTest_testConstructorAccessors_isNotIn_Normal extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNotIn_Normal() {
        // Build a negated range covering the characters from 'a' to 'e'.
        final CharRange negatedRange = CharRange.isNotIn('a', 'e');

        // The endpoints are preserved exactly as supplied.
        assertEquals('a', negatedRange.getStart(), "start should be the first endpoint");
        assertEquals('e', negatedRange.getEnd(), "end should be the second endpoint");

        // isNotIn produces a negated range...
        assertTrue(negatedRange.isNegated(), "isNotIn should produce a negated range");

        // ...and a negated range renders with a leading '^'.
        assertEquals("^a-e", negatedRange.toString(), "negated range should render as ^a-e");
    }
}
