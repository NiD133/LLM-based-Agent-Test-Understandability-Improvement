package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharRange#contains(char)} for the four ways a range can be built:
 * a single character, an inclusive span, a reversed span, and a negated span.
 */
public class CharRangeTest_testContains_Char extends AbstractLangTest {

    @Test
    void testContains_Char() {
        // A single-character range contains only that character.
        final CharRange singleChar = CharRange.is('c');
        assertFalse(singleChar.contains('b'));
        assertTrue(singleChar.contains('c'));
        assertFalse(singleChar.contains('d'));
        assertFalse(singleChar.contains('e'));

        // An inclusive range 'c'..'d' contains both endpoints but nothing outside.
        final CharRange inclusiveRange = CharRange.isIn('c', 'd');
        assertFalse(inclusiveRange.contains('b'));
        assertTrue(inclusiveRange.contains('c'));
        assertTrue(inclusiveRange.contains('d'));
        assertFalse(inclusiveRange.contains('e'));

        // Reversed endpoints 'd'..'c' are normalized to 'c'..'d', so it behaves identically.
        final CharRange reversedRange = CharRange.isIn('d', 'c');
        assertFalse(reversedRange.contains('b'));
        assertTrue(reversedRange.contains('c'));
        assertTrue(reversedRange.contains('d'));
        assertFalse(reversedRange.contains('e'));

        // A negated range 'c'..'d' contains everything except 'c' and 'd',
        // including the extreme character values.
        final CharRange negatedRange = CharRange.isNotIn('c', 'd');
        assertTrue(negatedRange.contains('b'));
        assertFalse(negatedRange.contains('c'));
        assertFalse(negatedRange.contains('d'));
        assertTrue(negatedRange.contains('e'));
        assertTrue(negatedRange.contains((char) 0));
        assertTrue(negatedRange.contains(Character.MAX_VALUE));
    }
}
