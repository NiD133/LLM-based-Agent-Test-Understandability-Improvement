package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testContains_Char extends AbstractLangTest {

    @Test
    void testContains_Char() {
        // A single-character range only contains that character.
        CharRange range = CharRange.is('c');
        assertFalse(range.contains('b'));
        assertTrue(range.contains('c'));
        assertFalse(range.contains('d'));
        assertFalse(range.contains('e'));

        // A normal inclusive range contains both endpoints.
        range = CharRange.isIn('c', 'd');
        assertFalse(range.contains('b'));
        assertTrue(range.contains('c'));
        assertTrue(range.contains('d'));
        assertFalse(range.contains('e'));

        // Reversed endpoints are normalized to the same inclusive range.
        range = CharRange.isIn('d', 'c');
        assertFalse(range.contains('b'));
        assertTrue(range.contains('c'));
        assertTrue(range.contains('d'));
        assertFalse(range.contains('e'));

        // A negated range contains everything except the endpoints and interior.
        range = CharRange.isNotIn('c', 'd');
        assertTrue(range.contains('b'));
        assertFalse(range.contains('c'));
        assertFalse(range.contains('d'));
        assertTrue(range.contains('e'));
        assertTrue(range.contains((char) 0));
        assertTrue(range.contains(Character.MAX_VALUE));
    }
}
