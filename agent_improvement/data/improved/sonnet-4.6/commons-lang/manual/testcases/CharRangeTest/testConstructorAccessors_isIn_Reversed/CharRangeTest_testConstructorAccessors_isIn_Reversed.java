package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    /**
     * When isIn() is given arguments in reversed order (end < start), CharRange
     * silently swaps them so the stored range always has start <= end.
     */
    @Test
    void testConstructorAccessors_isIn_Reversed() {
        // Pass 'e' as start and 'a' as end — deliberately reversed
        final CharRange rangea = CharRange.isIn('e', 'a');

        // The range must be normalized: smaller char becomes start, larger becomes end
        assertEquals('a', rangea.getStart());
        assertEquals('e', rangea.getEnd());
        assertFalse(rangea.isNegated());
        assertEquals("a-e", rangea.toString());
    }
}
