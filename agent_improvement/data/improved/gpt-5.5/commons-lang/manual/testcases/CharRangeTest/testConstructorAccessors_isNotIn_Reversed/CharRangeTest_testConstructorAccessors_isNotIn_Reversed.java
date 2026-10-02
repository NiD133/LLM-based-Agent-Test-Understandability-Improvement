package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNotIn_Reversed extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNotIn_Reversed() {
        final CharRange negatedRangeWithReversedBounds = CharRange.isNotIn('e', 'a');

        assertEquals('a', negatedRangeWithReversedBounds.getStart());
        assertEquals('e', negatedRangeWithReversedBounds.getEnd());
        assertTrue(negatedRangeWithReversedBounds.isNegated());
        assertEquals("^a-e", negatedRangeWithReversedBounds.toString());
    }
}
