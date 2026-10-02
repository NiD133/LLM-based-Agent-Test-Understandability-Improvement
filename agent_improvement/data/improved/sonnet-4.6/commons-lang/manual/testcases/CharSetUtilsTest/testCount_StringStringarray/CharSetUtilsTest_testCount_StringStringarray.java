package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringStringarray extends AbstractLangTest {

    @Test
    void testCount_nullString_returnsZero() {
        assertEquals(0, CharSetUtils.count(null, (String[]) null));
        assertEquals(0, CharSetUtils.count(null));
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, "a-e"));
    }

    @Test
    void testCount_emptyString_returnsZero() {
        assertEquals(0, CharSetUtils.count("", (String[]) null));
        assertEquals(0, CharSetUtils.count(""));
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", "a-e"));
    }

    @Test
    void testCount_validString_withNullOrEmptySet_returnsZero() {
        assertEquals(0, CharSetUtils.count("hello", (String[]) null));
        assertEquals(0, CharSetUtils.count("hello"));
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));
    }

    @Test
    void testCount_validString_withMatchingCharSet() {
        assertEquals(1, CharSetUtils.count("hello", "a-e"));
        assertEquals(3, CharSetUtils.count("hello", "el"));
        assertEquals(2, CharSetUtils.count("hello", "e-i"));
        assertEquals(5, CharSetUtils.count("hello", "a-z"));
    }

    @Test
    void testCount_validString_withNonMatchingCharSet_returnsZero() {
        assertEquals(0, CharSetUtils.count("hello", "x"));
    }
}
