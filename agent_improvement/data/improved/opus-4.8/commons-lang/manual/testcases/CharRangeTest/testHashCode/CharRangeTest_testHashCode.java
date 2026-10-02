package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCode extends AbstractLangTest {

    @Test
    void testHashCode() {
        // Three distinct ranges: a single character and two different spans.
        final CharRange singleA = CharRange.is('a');
        final CharRange spanAtoE = CharRange.isIn('a', 'e');
        final CharRange spanBtoF = CharRange.isIn('b', 'f');

        // hashCode() is consistent: the same range always yields the same value,
        // and equal ranges (rebuilt via the same factory calls) agree too.
        assertEquals(singleA.hashCode(), singleA.hashCode());
        assertEquals(singleA.hashCode(), CharRange.is('a').hashCode());
        assertEquals(spanAtoE.hashCode(), spanAtoE.hashCode());
        assertEquals(spanAtoE.hashCode(), CharRange.isIn('a', 'e').hashCode());
        assertEquals(spanBtoF.hashCode(), spanBtoF.hashCode());
        assertEquals(spanBtoF.hashCode(), CharRange.isIn('b', 'f').hashCode());

        // Ranges that differ produce different hash codes (checked both directions).
        assertNotEquals(singleA.hashCode(), spanAtoE.hashCode());
        assertNotEquals(singleA.hashCode(), spanBtoF.hashCode());
        assertNotEquals(spanAtoE.hashCode(), singleA.hashCode());
        assertNotEquals(spanAtoE.hashCode(), spanBtoF.hashCode());
        assertNotEquals(spanBtoF.hashCode(), singleA.hashCode());
        assertNotEquals(spanBtoF.hashCode(), spanAtoE.hashCode());
    }
}
