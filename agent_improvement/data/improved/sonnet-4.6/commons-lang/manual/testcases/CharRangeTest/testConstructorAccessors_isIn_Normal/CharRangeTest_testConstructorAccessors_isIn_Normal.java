package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_isIn_Normal extends AbstractLangTest {

    @Test
    void testConstructorAccessors_isIn_Normal() {
        // CharRange.isIn('a', 'e') creates a non-negated range from 'a' to 'e' inclusive
        final CharRange rangeAtoE = CharRange.isIn('a', 'e');

        assertEquals('a', rangeAtoE.getStart(), "Start character should be 'a'");
        assertEquals('e', rangeAtoE.getEnd(), "End character should be 'e'");
        assertFalse(rangeAtoE.isNegated(), "Range created by isIn() should not be negated");
        assertEquals("a-e", rangeAtoE.toString(), "String representation should be 'a-e'");
    }
}
