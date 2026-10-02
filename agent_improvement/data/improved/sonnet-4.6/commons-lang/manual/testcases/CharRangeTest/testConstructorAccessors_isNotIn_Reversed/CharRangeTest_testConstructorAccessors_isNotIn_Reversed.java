package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharRange#isNotIn(char, char)} normalises a reversed
 * (end-before-start) argument pair by swapping the two characters so that the
 * stored range always satisfies {@code start <= end}, while still marking the
 * range as negated.
 */
public class CharRangeTest_testConstructorAccessors_isNotIn_Reversed extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNotIn_Reversed() {
        // Pass arguments in reversed order: 'e' > 'a', so the constructor must swap them.
        final CharRange reversedNegatedRange = CharRange.isNotIn('e', 'a');

        // After normalisation the smaller character becomes the start …
        assertEquals('a', reversedNegatedRange.getStart(),
                "isNotIn should reorder a reversed pair so the smaller char is the start");

        // … and the larger character becomes the end.
        assertEquals('e', reversedNegatedRange.getEnd(),
                "isNotIn should reorder a reversed pair so the larger char is the end");

        // The range must be negated because it was created with isNotIn.
        assertTrue(reversedNegatedRange.isNegated(),
                "A range created with isNotIn must be negated");

        // The canonical string representation of a negated range is "^<start>-<end>".
        assertEquals("^a-e", reversedNegatedRange.toString(),
                "toString should produce the canonical negated-range notation");
    }
}
