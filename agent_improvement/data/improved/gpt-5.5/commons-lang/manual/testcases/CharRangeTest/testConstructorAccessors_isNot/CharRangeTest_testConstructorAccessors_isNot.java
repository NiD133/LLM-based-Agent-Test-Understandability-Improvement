package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isNot extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isNot() {
        final CharRange notA = CharRange.isNot('a');

        assertEquals('a', notA.getStart());
        assertEquals('a', notA.getEnd());
        assertTrue(notA.isNegated());
        assertEquals("^a", notA.toString());
    }
}
