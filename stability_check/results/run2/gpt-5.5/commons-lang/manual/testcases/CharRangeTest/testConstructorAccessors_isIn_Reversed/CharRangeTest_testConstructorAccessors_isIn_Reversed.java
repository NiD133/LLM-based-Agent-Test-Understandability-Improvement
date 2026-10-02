package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Reversed extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Reversed() {
        final CharRange reversedRange = CharRange.isIn('e', 'a');

        assertEquals('a', reversedRange.getStart());
        assertEquals('e', reversedRange.getEnd());
        assertFalse(reversedRange.isNegated());
        assertEquals("a-e", reversedRange.toString());
    }
}
