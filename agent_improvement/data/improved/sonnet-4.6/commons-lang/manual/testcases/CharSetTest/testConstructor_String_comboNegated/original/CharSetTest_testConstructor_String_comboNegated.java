package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_comboNegated extends AbstractLangTest {

    @Test
    void testConstructor_String_comboNegated() {
        CharSet set;
        Set<CharRange> array;
        set = CharSet.getInstance("^abc");
        array = set.getCharRanges();
        assertEquals(3, array.size());
        assertTrue(array.contains(CharRange.isNot('a')));
        assertTrue(array.contains(CharRange.is('b')));
        assertTrue(array.contains(CharRange.is('c')));
        set = CharSet.getInstance("b^ac");
        array = set.getCharRanges();
        assertEquals(3, array.size());
        assertTrue(array.contains(CharRange.is('b')));
        assertTrue(array.contains(CharRange.isNot('a')));
        assertTrue(array.contains(CharRange.is('c')));
        set = CharSet.getInstance("db^ac");
        array = set.getCharRanges();
        assertEquals(4, array.size());
        assertTrue(array.contains(CharRange.is('d')));
        assertTrue(array.contains(CharRange.is('b')));
        assertTrue(array.contains(CharRange.isNot('a')));
        assertTrue(array.contains(CharRange.is('c')));
        set = CharSet.getInstance("^b^a");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.isNot('b')));
        assertTrue(array.contains(CharRange.isNot('a')));
        set = CharSet.getInstance("b^a-c^z");
        array = set.getCharRanges();
        assertEquals(3, array.size());
        assertTrue(array.contains(CharRange.isNotIn('a', 'c')));
        assertTrue(array.contains(CharRange.isNot('z')));
        assertTrue(array.contains(CharRange.is('b')));
    }
}
