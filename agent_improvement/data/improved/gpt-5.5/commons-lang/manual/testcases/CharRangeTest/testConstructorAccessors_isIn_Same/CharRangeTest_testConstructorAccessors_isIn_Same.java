package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Same extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Same() {
        final CharRange singleCharacterRange = CharRange.isIn('a', 'a');

        assertEquals('a', singleCharacterRange.getStart());
        assertEquals('a', singleCharacterRange.getEnd());
        assertFalse(singleCharacterRange.isNegated());
        assertEquals("a", singleCharacterRange.toString());
    }
}
