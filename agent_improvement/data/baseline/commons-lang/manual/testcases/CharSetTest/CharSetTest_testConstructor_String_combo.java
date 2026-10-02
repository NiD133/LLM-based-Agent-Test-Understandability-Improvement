package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_combo extends AbstractLangTest {

    @Test
    void testConstructor_String_combo() {
        CharSet set;
        Set<CharRange> array;
        set = CharSet.getInstance("abc");
        array = set.getCharRanges();
        assertEquals(3, array.size());
        assertTrue(array.contains(CharRange.is('a')));
        assertTrue(array.contains(CharRange.is('b')));
        assertTrue(array.contains(CharRange.is('c')));
        set = CharSet.getInstance("a-ce-f");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.isIn('a', 'c')));
        assertTrue(array.contains(CharRange.isIn('e', 'f')));
        set = CharSet.getInstance("ae-f");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.is('a')));
        assertTrue(array.contains(CharRange.isIn('e', 'f')));
        set = CharSet.getInstance("e-fa");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.is('a')));
        assertTrue(array.contains(CharRange.isIn('e', 'f')));
        set = CharSet.getInstance("ae-fm-pz");
        array = set.getCharRanges();
        assertEquals(4, array.size());
        assertTrue(array.contains(CharRange.is('a')));
        assertTrue(array.contains(CharRange.isIn('e', 'f')));
        assertTrue(array.contains(CharRange.isIn('m', 'p')));
        assertTrue(array.contains(CharRange.is('z')));
    }
}
