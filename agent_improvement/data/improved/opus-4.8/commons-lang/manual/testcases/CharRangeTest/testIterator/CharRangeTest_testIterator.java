package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link CharRange#iterator()} for the different kinds of ranges:
 * a single character, an inclusive span, and several negated ranges
 * (including the edge cases at the very start and very end of the character space).
 */
public class CharRangeTest_testIterator extends AbstractLangTest {

    @Test
    void testIterator() {
        // --- Single character range: iterates over exactly 'a'. ---
        final Iterator<Character> singleChar = CharRange.is('a').iterator();
        assertNotNull(singleChar);
        assertTrue(singleChar.hasNext());
        assertEquals(Character.valueOf('a'), singleChar.next());
        assertFalse(singleChar.hasNext());

        // --- Inclusive span 'a'..'d': iterates over 'a', 'b', 'c', 'd' in order. ---
        final Iterator<Character> span = CharRange.isIn('a', 'd').iterator();
        assertNotNull(span);
        assertTrue(span.hasNext());
        assertEquals(Character.valueOf('a'), span.next());
        assertEquals(Character.valueOf('b'), span.next());
        assertEquals(Character.valueOf('c'), span.next());
        assertEquals(Character.valueOf('d'), span.next());
        assertFalse(span.hasNext());

        // --- Negated single character: iterates over every char except 'a'. ---
        final Iterator<Character> everythingButA = CharRange.isNot('a').iterator();
        assertNotNull(everythingButA);
        assertTrue(everythingButA.hasNext());
        while (everythingButA.hasNext()) {
            final Character c = everythingButA.next();
            assertNotEquals('a', c.charValue());
        }

        // --- Negated full range [0 .. MAX_VALUE]: nothing is left, so the iterator is empty. ---
        final Iterator<Character> emptySet =
                CharRange.isNotIn((char) 0, Character.MAX_VALUE).iterator();
        assertNotNull(emptySet);
        assertFalse(emptySet.hasNext());
        assertThrows(NoSuchElementException.class, emptySet::next);

        // --- Negated [1 .. MAX_VALUE]: only character 0 remains. ---
        final Iterator<Character> onlyFirst =
                CharRange.isNotIn((char) 1, Character.MAX_VALUE).iterator();
        assertNotNull(onlyFirst);
        assertTrue(onlyFirst.hasNext());
        assertEquals(Character.valueOf((char) 0), onlyFirst.next());
        assertFalse(onlyFirst.hasNext());
        assertThrows(NoSuchElementException.class, onlyFirst::next);

        // --- Negated [0 .. MAX_VALUE - 1]: only character MAX_VALUE remains. ---
        final Iterator<Character> onlyLast =
                CharRange.isNotIn((char) 0, (char) (Character.MAX_VALUE - 1)).iterator();
        assertNotNull(onlyLast);
        assertTrue(onlyLast.hasNext());
        assertEquals(Character.valueOf(Character.MAX_VALUE), onlyLast.next());
        assertFalse(onlyLast.hasNext());
        assertThrows(NoSuchElementException.class, onlyLast::next);
    }
}
