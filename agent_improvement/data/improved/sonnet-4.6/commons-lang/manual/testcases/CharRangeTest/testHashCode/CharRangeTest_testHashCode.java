package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCode extends AbstractLangTest {

    @Test
    void testHashCode() {
        final CharRange singleChar   = CharRange.is('a');
        final CharRange rangeAtoE    = CharRange.isIn('a', 'e');
        final CharRange rangeBtoF    = CharRange.isIn('b', 'f');

        // Self-consistency: repeated calls on the same object return the same hash code
        assertEquals(singleChar.hashCode(), singleChar.hashCode());
        assertEquals(rangeAtoE.hashCode(),  rangeAtoE.hashCode());
        assertEquals(rangeBtoF.hashCode(),  rangeBtoF.hashCode());

        // Determinism: equal (equivalent) CharRange instances must produce the same hash code
        assertEquals(singleChar.hashCode(), CharRange.is('a').hashCode());
        assertEquals(rangeAtoE.hashCode(),  CharRange.isIn('a', 'e').hashCode());
        assertEquals(rangeBtoF.hashCode(),  CharRange.isIn('b', 'f').hashCode());

        // Distinctness: different ranges should (and do) produce different hash codes
        assertNotEquals(singleChar.hashCode(), rangeAtoE.hashCode());
        assertNotEquals(singleChar.hashCode(), rangeBtoF.hashCode());
        assertNotEquals(rangeAtoE.hashCode(),  singleChar.hashCode());
        assertNotEquals(rangeAtoE.hashCode(),  rangeBtoF.hashCode());
        assertNotEquals(rangeBtoF.hashCode(),  singleChar.hashCode());
        assertNotEquals(rangeBtoF.hashCode(),  rangeAtoE.hashCode());
    }
}
