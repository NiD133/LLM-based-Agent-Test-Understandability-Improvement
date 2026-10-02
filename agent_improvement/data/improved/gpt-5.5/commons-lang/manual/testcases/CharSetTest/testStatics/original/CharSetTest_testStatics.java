package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testStatics extends AbstractLangTest {

    @Test
    void testStatics() {
        Set<CharRange> array;
        array = CharSet.EMPTY.getCharRanges();
        assertEquals(0, array.size());
        array = CharSet.ASCII_ALPHA.getCharRanges();
        assertEquals(2, array.size());
        assertTrue(array.contains(CharRange.isIn('a', 'z')));
        assertTrue(array.contains(CharRange.isIn('A', 'Z')));
        array = CharSet.ASCII_ALPHA_LOWER.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.isIn('a', 'z')));
        array = CharSet.ASCII_ALPHA_UPPER.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.isIn('A', 'Z')));
        array = CharSet.ASCII_NUMERIC.getCharRanges();
        assertEquals(1, array.size());
        assertTrue(array.contains(CharRange.isIn('0', '9')));
    }
}
