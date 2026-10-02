package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_oddDash extends AbstractLangTest {

    @Test
    void testConstructor_String_oddDash() {
        CharSet set;
        Set<CharRange> array;
        set = CharSet.getInstance("-");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.is('-')));
        set = CharSet.getInstance("--");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.is('-')));
        set = CharSet.getInstance("---");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.is('-')));
        set = CharSet.getInstance("----");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.is('-')));
        set = CharSet.getInstance("-a");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.is('-')));
        assertTrue(array.contains(CharRange.is('a')));
        set = CharSet.getInstance("a-");
        array = set.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.is('a')));
        assertTrue(array.contains(CharRange.is('-')));
        set = CharSet.getInstance("a--");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.isIn('a', '-')));
        set = CharSet.getInstance("--a");
        array = set.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.isIn('-', 'a')));
    }
}
