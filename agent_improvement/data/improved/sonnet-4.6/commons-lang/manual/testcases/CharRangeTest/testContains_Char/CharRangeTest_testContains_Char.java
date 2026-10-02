package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testContains_Char extends AbstractLangTest {

    @Test
    void testContains_singleChar_onlyMatchesExactChar() {
        CharRange singleCharRange = CharRange.is('c');
        assertFalse(singleCharRange.contains('b'));
        assertTrue(singleCharRange.contains('c'));
        assertFalse(singleCharRange.contains('d'));
        assertFalse(singleCharRange.contains('e'));
    }

    @Test
    void testContains_ascendingRange_matchesCharsWithinBounds() {
        CharRange ascendingRange = CharRange.isIn('c', 'd');
        assertFalse(ascendingRange.contains('b'));
        assertTrue(ascendingRange.contains('c'));
        assertTrue(ascendingRange.contains('d'));
        assertFalse(ascendingRange.contains('e'));
    }

    @Test
    void testContains_reversedRange_normalizedToAscendingAndMatchesSameChars() {
        // isIn normalizes reversed endpoints, so isIn('d','c') behaves like isIn('c','d')
        CharRange reversedRange = CharRange.isIn('d', 'c');
        assertFalse(reversedRange.contains('b'));
        assertTrue(reversedRange.contains('c'));
        assertTrue(reversedRange.contains('d'));
        assertFalse(reversedRange.contains('e'));
    }

    @Test
    void testContains_negatedRange_excludesCharsWithinBoundsAndIncludesEverythingElse() {
        CharRange negatedRange = CharRange.isNotIn('c', 'd');
        assertTrue(negatedRange.contains('b'));
        assertFalse(negatedRange.contains('c'));
        assertFalse(negatedRange.contains('d'));
        assertTrue(negatedRange.contains('e'));
        assertTrue(negatedRange.contains((char) 0));
        assertTrue(negatedRange.contains(Character.MAX_VALUE));
    }
}
