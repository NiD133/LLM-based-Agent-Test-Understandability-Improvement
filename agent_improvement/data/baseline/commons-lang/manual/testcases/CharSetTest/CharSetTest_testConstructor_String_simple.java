package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testConstructor_String_simple extends AbstractLangTest {

    @Test
    void testConstructor_String_simple() {
        CharSet set;
        Set<CharRange> array;
        set = CharSet.getInstance((String) null);
        array = set.getCharRanges();
        assertEquals("[]", set.toString());
        assertEquals(0, array.size());
        set = CharSet.getInstance("");
        array = set.getCharRanges();
        assertEquals("[]", set.toString());
        assertEquals(0, array.size());
        set = CharSet.getInstance("a");
        array = set.getCharRanges();
        assertEquals("[a]", set.toString());
        assertEquals(1, array.size());
        assertEquals("a", array.iterator().next().toString());
        set = CharSet.getInstance("^a");
        array = set.getCharRanges();
        assertEquals("[^a]", set.toString());
        assertEquals(1, array.size());
        assertEquals("^a", array.iterator().next().toString());
        set = CharSet.getInstance("a-e");
        array = set.getCharRanges();
        assertEquals("[a-e]", set.toString());
        assertEquals(1, array.size());
        assertEquals("a-e", array.iterator().next().toString());
        set = CharSet.getInstance("^a-e");
        array = set.getCharRanges();
        assertEquals("[^a-e]", set.toString());
        assertEquals(1, array.size());
        assertEquals("^a-e", array.iterator().next().toString());
    }
}
