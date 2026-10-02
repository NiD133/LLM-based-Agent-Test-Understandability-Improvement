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

public class CharRangeTest_testIterator extends AbstractLangTest {

    @Test
    void testIterator() {
        final CharRange a = CharRange.is('a');
        final CharRange ad = CharRange.isIn('a', 'd');
        final CharRange nota = CharRange.isNot('a');
        final CharRange emptySet = CharRange.isNotIn((char) 0, Character.MAX_VALUE);
        final CharRange notFirst = CharRange.isNotIn((char) 1, Character.MAX_VALUE);
        final CharRange notLast = CharRange.isNotIn((char) 0, (char) (Character.MAX_VALUE - 1));

        assertSingleCharacterIterator(a);
        assertMultiCharacterIterator(ad);
        assertNegatedSingleCharacterIterator(nota);
        assertEmptyNegatedRangeIterator(emptySet);
        assertNegatedRangeExcludingFirstCharacters(notFirst);
        assertNegatedRangeExcludingLastCharacters(notLast);
    }

    private void assertSingleCharacterIterator(final CharRange range) {
        final Iterator<Character> iterator = range.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf('a'), iterator.next());
        assertFalse(iterator.hasNext());
    }

    private void assertMultiCharacterIterator(final CharRange range) {
        final Iterator<Character> iterator = range.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf('a'), iterator.next());
        assertEquals(Character.valueOf('b'), iterator.next());
        assertEquals(Character.valueOf('c'), iterator.next());
        assertEquals(Character.valueOf('d'), iterator.next());
        assertFalse(iterator.hasNext());
    }

    private void assertNegatedSingleCharacterIterator(final CharRange range) {
        final Iterator<Character> iterator = range.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        while (iterator.hasNext()) {
            final Character c = iterator.next();
            assertNotEquals('a', c.charValue());
        }
    }

    private void assertEmptyNegatedRangeIterator(final CharRange range) {
        final Iterator<Character> iterator = range.iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    private void assertNegatedRangeExcludingFirstCharacters(final CharRange range) {
        final Iterator<Character> iterator = range.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf((char) 0), iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    private void assertNegatedRangeExcludingLastCharacters(final CharRange range) {
        final Iterator<Character> iterator = range.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(Character.valueOf(Character.MAX_VALUE), iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
