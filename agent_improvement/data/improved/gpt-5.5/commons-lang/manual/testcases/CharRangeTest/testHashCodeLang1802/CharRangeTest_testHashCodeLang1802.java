package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCodeLang1802 extends AbstractLangTest {

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-1802
     */
    @Test
    void testHashCodeLang1802() {
        final CharRange singleA = CharRange.is('a');
        final CharRange singleB = CharRange.is('b');
        final CharRange aToZ = CharRange.isIn('a', 'z');
        final CharRange bToZ = CharRange.isIn('b', 'z');
        final CharRange notA = CharRange.isNot('a');
        final CharRange notAToZ = CharRange.isNotIn('a', 'z');
        final CharRange notBToZ = CharRange.isNotIn('b', 'z');
        final CharRange oneToTwo = CharRange.isIn((char) 1, (char) 2);
        final CharRange notOneToTwo = CharRange.isNotIn((char) 1, (char) 2);

        final CharRange lang1802NegatedOneToTwo = CharRange.isNotIn((char) 1, (char) 2);
        final CharRange lang1802SingleTwo = CharRange.isIn((char) 2, (char) 2);
        assertNotEquals(lang1802NegatedOneToTwo, lang1802SingleTwo, "Different ranges should not be equal");
        assertNotEquals(lang1802NegatedOneToTwo.hashCode(), lang1802SingleTwo.hashCode(), "Different ranges should have different hash codes");

        final CharRange lang1802SingleFive = CharRange.isIn((char) 5, (char) 5);
        final CharRange lang1802NegatedFourToFive = CharRange.isNotIn((char) 4, (char) 5);
        assertNotEquals(lang1802SingleFive, lang1802NegatedFourToFive, "Different ranges should not be equal");
        assertNotEquals(lang1802SingleFive.hashCode(), lang1802NegatedFourToFive.hashCode(), "Different ranges should have different hash codes");

        final CharRange normal = CharRange.isIn('x', 'y');
        final CharRange negated = CharRange.isNotIn('x', 'y');
        assertNotEquals(normal, negated, "Negated and normal ranges should not be equal");
        assertNotEquals(normal.hashCode(), negated.hashCode(), "Negated and normal ranges should have different hash codes");

        assertNotEquals(singleA.hashCode(), singleB.hashCode(), "is('a') vs is('b')");
        assertNotEquals(singleA.hashCode(), aToZ.hashCode(), "is('a') vs isIn('a', 'z')");
        assertNotEquals(aToZ.hashCode(), bToZ.hashCode(), "isIn('a', 'z') vs isIn('b', 'z')");
        assertNotEquals(singleA.hashCode(), notA.hashCode(), "is('a') vs isNot('a')");
        assertNotEquals(aToZ.hashCode(), notAToZ.hashCode(), "isIn('a', 'z') vs isNotIn('a', 'z')");
        assertNotEquals(notAToZ.hashCode(), notBToZ.hashCode(), "isNotIn('a', 'z') vs isNotIn('b', 'z')");
        assertNotEquals(oneToTwo.hashCode(), notOneToTwo.hashCode(), "isIn(1, 2) vs isNotIn(1, 2)");

        final CharRange sameAsSingleA = CharRange.is('a');
        assertEquals(singleA, sameAsSingleA, "Equal ranges should be equal");
        assertEquals(singleA.hashCode(), sameAsSingleA.hashCode(), "Equal ranges should have equal hash codes");
    }
}
