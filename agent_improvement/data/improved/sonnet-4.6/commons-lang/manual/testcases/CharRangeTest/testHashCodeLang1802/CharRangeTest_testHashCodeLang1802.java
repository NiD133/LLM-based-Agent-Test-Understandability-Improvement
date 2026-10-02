package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCodeLang1802 extends AbstractLangTest {

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-1802
     *
     * LANG-1802 reported that certain pairs of distinct CharRange instances produced
     * identical hash codes, violating the general contract that unequal objects should
     * (ideally) have different hash codes. This test verifies the fix covers:
     *   1. The originally reported collision cases.
     *   2. The broader hash-code contract across the four factory methods.
     *   3. The equality contract: equal ranges must have equal hash codes.
     */
    @Test
    void testHashCodeLang1802() {

        // --- LANG-1802 regression: formerly colliding pairs must now differ ---

        // Reported case 1: isNotIn(1,2) vs isIn(2,2)
        final CharRange a1 = CharRange.isNotIn((char) 1, (char) 2);
        final CharRange a2 = CharRange.isIn((char) 2, (char) 2);
        assertNotEquals(a1, a2, "Different ranges should not be equal");
        assertNotEquals(a1.hashCode(), a2.hashCode(), "Different ranges should have different hash codes");

        // Reported case 2: isIn(5,5) vs isNotIn(4,5)
        final CharRange b1 = CharRange.isIn((char) 5, (char) 5);
        final CharRange b2 = CharRange.isNotIn((char) 4, (char) 5);
        assertNotEquals(b1, b2, "Different ranges should not be equal");
        assertNotEquals(b1.hashCode(), b2.hashCode(), "Different ranges should have different hash codes");

        // --- General hash-code contract: distinct ranges must have distinct hash codes ---

        // Negation flag must affect the hash code
        final CharRange normal  = CharRange.isIn('x', 'y');
        final CharRange negated = CharRange.isNotIn('x', 'y');
        assertNotEquals(normal, negated, "Negated and normal ranges should not be equal");
        assertNotEquals(normal.hashCode(), negated.hashCode(), "Negated and normal ranges should have different hash codes");

        // Start character must affect the hash code
        final CharRange range1 = CharRange.is('a');
        final CharRange range2 = CharRange.is('b');
        assertNotEquals(range1.hashCode(), range2.hashCode(), "is('a') vs is('b')");

        // End character must affect the hash code (same start, different end)
        final CharRange range3 = CharRange.isIn('a', 'z');
        assertNotEquals(range1.hashCode(), range3.hashCode(), "is('a') vs isIn('a', 'z')");

        // Start character must affect the hash code for multi-char ranges
        final CharRange range4 = CharRange.isIn('b', 'z');
        assertNotEquals(range3.hashCode(), range4.hashCode(), "isIn('a', 'z') vs isIn('b', 'z')");

        // Negation must affect the hash code for single-char ranges
        final CharRange range5 = CharRange.isNot('a');
        assertNotEquals(range1.hashCode(), range5.hashCode(), "is('a') vs isNot('a')");

        // Negation must affect the hash code for multi-char ranges
        final CharRange range6 = CharRange.isNotIn('a', 'z');
        assertNotEquals(range3.hashCode(), range6.hashCode(), "isIn('a', 'z') vs isNotIn('a', 'z')");

        // Start character must affect the hash code for negated multi-char ranges
        final CharRange range7 = CharRange.isNotIn('b', 'z');
        assertNotEquals(range6.hashCode(), range7.hashCode(), "isNotIn('a', 'z') vs isNotIn('b', 'z')");

        // Negation must affect the hash code for low-value char ranges
        final CharRange range8 = CharRange.isIn((char) 1, (char) 2);
        final CharRange range9 = CharRange.isNotIn((char) 1, (char) 2);
        assertNotEquals(range8.hashCode(), range9.hashCode(), "isIn(1, 2) vs isNotIn(1, 2)");

        // --- Equality contract: equal ranges must have equal hash codes ---

        final CharRange sameAsRange1 = CharRange.is('a');
        assertEquals(range1, sameAsRange1, "Equal ranges should be equal");
        assertEquals(range1.hashCode(), sameAsRange1.hashCode(), "Equal ranges should have equal hash codes");
    }
}
