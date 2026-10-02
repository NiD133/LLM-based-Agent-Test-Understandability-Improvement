package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNotIn_Same extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNotIn_Same() {
        final CharRange negatedSingleCharacterRange = CharRange.isNotIn('a', 'a');

        assertEquals('a', negatedSingleCharacterRange.getStart());
        assertEquals('a', negatedSingleCharacterRange.getEnd());
        assertTrue(negatedSingleCharacterRange.isNegated());
        assertEquals("^a", negatedSingleCharacterRange.toString());
    }
}
