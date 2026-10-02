package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_oddNegate extends AbstractLangTest {

    @Test
    void testConstructor_String_oddNegate() {
        CharSet set;
        Set<CharRange> array;
        set = CharSet.getInstance("^");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        // "^"
        assertTrue(array.contains(CharRange.is('^')));
        set = CharSet.getInstance("^^");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        // "^^"
        assertTrue(array.contains(CharRange.isNot('^')));
        set = CharSet.getInstance("^^^");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        // "^^"
        assertTrue(array.contains(CharRange.isNot('^')));
        // "^"
        assertTrue(array.contains(CharRange.is('^')));
        set = CharSet.getInstance("^^^^");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        // "^^" x2
        assertTrue(array.contains(CharRange.isNot('^')));
        set = CharSet.getInstance("a^");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        // "a"
        assertTrue(array.contains(CharRange.is('a')));
        // "^"
        assertTrue(array.contains(CharRange.is('^')));
        set = CharSet.getInstance("^a-");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        // "^a"
        assertTrue(array.contains(CharRange.isNot('a')));
        // "-"
        assertTrue(array.contains(CharRange.is('-')));
        set = CharSet.getInstance("^^-c");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        // "^^-c"
        assertTrue(array.contains(CharRange.isNotIn('^', 'c')));
        set = CharSet.getInstance("^c-^");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        // "^c-^"
        assertTrue(array.contains(CharRange.isNotIn('c', '^')));
        set = CharSet.getInstance("^c-^d");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        // "^c-^"
        assertTrue(array.contains(CharRange.isNotIn('c', '^')));
        // "d"
        assertTrue(array.contains(CharRange.is('d')));
        set = CharSet.getInstance("^^-");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        // "^^"
        assertTrue(array.contains(CharRange.isNot('^')));
        // "-"
        assertTrue(array.contains(CharRange.is('-')));
    }
}
