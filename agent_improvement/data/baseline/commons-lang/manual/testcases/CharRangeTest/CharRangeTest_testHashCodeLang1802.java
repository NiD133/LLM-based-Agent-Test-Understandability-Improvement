package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testHashCodeLang1802 extends AbstractLangTest {

    /**
     * Tests https://issues.apache.org/jira/browse/LANG-1802
     */
    @Test
    void testHashCodeLang1802() {
        // Test various combinations of different ranges
        final CharRange range1 = CharRange.is('a');
        final CharRange range2 = CharRange.is('b');
        final CharRange range3 = CharRange.isIn('a', 'z');
        final CharRange range4 = CharRange.isIn('b', 'z');
        final CharRange range5 = CharRange.isNot('a');
        final CharRange range6 = CharRange.isNotIn('a', 'z');
        final CharRange range7 = CharRange.isNotIn('b', 'z');
        final CharRange range8 = CharRange.isIn((char) 1, (char) 2);
        final CharRange range9 = CharRange.isNotIn((char) 1, (char) 2);
        // Previously problematic cases from LANG-1802 should now have different hash codes
        final CharRange a1 = CharRange.isNotIn((char) 1, (char) 2);
        final CharRange a2 = CharRange.isIn((char) 2, (char) 2);
        assertNotEquals(a1, a2, "Different ranges should not be equal");
        assertNotEquals(a1.hashCode(), a2.hashCode(), "Different ranges should have different hash codes");
        final CharRange b1 = CharRange.isIn((char) 5, (char) 5);
        final CharRange b2 = CharRange.isNotIn((char) 4, (char) 5);
        assertNotEquals(b1, b2, "Different ranges should not be equal");
        assertNotEquals(b1.hashCode(), b2.hashCode(), "Different ranges should have different hash codes");
        // Test that negated and non-negated ranges with same bounds have different hash codes
        final CharRange normal = CharRange.isIn('x', 'y');
        final CharRange negated = CharRange.isNotIn('x', 'y');
        assertNotEquals(normal, negated, "Negated and normal ranges should not be equal");
        assertNotEquals(normal.hashCode(), negated.hashCode(), "Negated and normal ranges should have different hash codes");
        // Test that ranges with different start/end produce different hash codes
        assertNotEquals(range1.hashCode(), range2.hashCode(), "is('a') vs is('b')");
        assertNotEquals(range1.hashCode(), range3.hashCode(), "is('a') vs isIn('a', 'z')");
        assertNotEquals(range3.hashCode(), range4.hashCode(), "isIn('a', 'z') vs isIn('b', 'z')");
        assertNotEquals(range1.hashCode(), range5.hashCode(), "is('a') vs isNot('a')");
        assertNotEquals(range3.hashCode(), range6.hashCode(), "isIn('a', 'z') vs isNotIn('a', 'z')");
        assertNotEquals(range6.hashCode(), range7.hashCode(), "isNotIn('a', 'z') vs isNotIn('b', 'z')");
        assertNotEquals(range8.hashCode(), range9.hashCode(), "isIn(1, 2) vs isNotIn(1, 2)");
        // Test that equal ranges have equal hash codes
        final CharRange sameAsRange1 = CharRange.is('a');
        assertEquals(range1, sameAsRange1, "Equal ranges should be equal");
        assertEquals(range1.hashCode(), sameAsRange1.hashCode(), "Equal ranges should have equal hash codes");
    }
}
