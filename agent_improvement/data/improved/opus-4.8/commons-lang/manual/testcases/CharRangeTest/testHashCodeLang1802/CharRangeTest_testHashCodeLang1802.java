package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCodeLang1802 extends AbstractLangTest {

    /**
     * Verifies the hash-code contract for {@link CharRange} as required by
     * <a href="https://issues.apache.org/jira/browse/LANG-1802">LANG-1802</a>:
     * <ul>
     *   <li>ranges that differ in any way must produce different hash codes, and</li>
     *   <li>ranges that are equal must produce the same hash code.</li>
     * </ul>
     */
    @Test
    void testHashCodeLang1802() {
        // Distinct ranges that previously collided in LANG-1802: the negated and
        // non-negated variants must be unequal and have different hash codes.
        final CharRange negatedOneToTwo = CharRange.isNotIn((char) 1, (char) 2);
        final CharRange justTwo = CharRange.isIn((char) 2, (char) 2);
        assertNotEquals(negatedOneToTwo, justTwo, "Different ranges should not be equal");
        assertNotEquals(negatedOneToTwo.hashCode(), justTwo.hashCode(),
                "Different ranges should have different hash codes");

        final CharRange justFive = CharRange.isIn((char) 5, (char) 5);
        final CharRange negatedFourToFive = CharRange.isNotIn((char) 4, (char) 5);
        assertNotEquals(justFive, negatedFourToFive, "Different ranges should not be equal");
        assertNotEquals(justFive.hashCode(), negatedFourToFive.hashCode(),
                "Different ranges should have different hash codes");

        // A negated range and a non-negated range with the same bounds must differ.
        final CharRange normal = CharRange.isIn('x', 'y');
        final CharRange negated = CharRange.isNotIn('x', 'y');
        assertNotEquals(normal, negated, "Negated and normal ranges should not be equal");
        assertNotEquals(normal.hashCode(), negated.hashCode(),
                "Negated and normal ranges should have different hash codes");

        // Ranges differing in start, end, or negation must have different hash codes.
        final CharRange isA = CharRange.is('a');
        final CharRange isB = CharRange.is('b');
        final CharRange aToZ = CharRange.isIn('a', 'z');
        final CharRange bToZ = CharRange.isIn('b', 'z');
        final CharRange isNotA = CharRange.isNot('a');
        final CharRange notAToZ = CharRange.isNotIn('a', 'z');
        final CharRange notBToZ = CharRange.isNotIn('b', 'z');
        final CharRange oneToTwo = CharRange.isIn((char) 1, (char) 2);
        final CharRange notOneToTwo = CharRange.isNotIn((char) 1, (char) 2);

        assertNotEquals(isA.hashCode(), isB.hashCode(), "is('a') vs is('b')");
        assertNotEquals(isA.hashCode(), aToZ.hashCode(), "is('a') vs isIn('a', 'z')");
        assertNotEquals(aToZ.hashCode(), bToZ.hashCode(), "isIn('a', 'z') vs isIn('b', 'z')");
        assertNotEquals(isA.hashCode(), isNotA.hashCode(), "is('a') vs isNot('a')");
        assertNotEquals(aToZ.hashCode(), notAToZ.hashCode(), "isIn('a', 'z') vs isNotIn('a', 'z')");
        assertNotEquals(notAToZ.hashCode(), notBToZ.hashCode(), "isNotIn('a', 'z') vs isNotIn('b', 'z')");
        assertNotEquals(oneToTwo.hashCode(), notOneToTwo.hashCode(), "isIn(1, 2) vs isNotIn(1, 2)");

        // Equal ranges must be equal and share the same hash code.
        final CharRange sameAsIsA = CharRange.is('a');
        assertEquals(isA, sameAsIsA, "Equal ranges should be equal");
        assertEquals(isA.hashCode(), sameAsIsA.hashCode(), "Equal ranges should have equal hash codes");
    }
}
