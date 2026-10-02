package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNotIn_Normal extends AbstractLangTest {

    /**
     * Verifies that {@link CharRange#isNotIn(char, char)} creates a negated range spanning
     * from 'a' to 'e' inclusive. A negated range matches every character EXCEPT those
     * in the specified bounds. The string representation uses the "^" prefix to signal negation.
     */
    @Test
    void testConstructorAccessors_isNotIn_Normal() {
        final CharRange negatedRange = CharRange.isNotIn('a', 'e');

        assertEquals('a', negatedRange.getStart(), "Start character should be 'a'");
        assertEquals('e', negatedRange.getEnd(),   "End character should be 'e'");
        assertTrue(negatedRange.isNegated(),        "Range created with isNotIn should be negated");
        assertEquals("^a-e", negatedRange.toString(),
                "Negated range toString should use '^' prefix followed by start-end");
    }
}
