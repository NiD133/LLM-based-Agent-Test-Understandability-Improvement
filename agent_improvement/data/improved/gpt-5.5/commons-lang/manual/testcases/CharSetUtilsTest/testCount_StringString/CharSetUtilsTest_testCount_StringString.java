package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Test
    void testCount_StringString() {
        assertEquals(0, CharSetUtils.count(null, (String) null), "null input and null set");
        assertEquals(0, CharSetUtils.count(null, ""), "null input and empty set");
        assertEquals(0, CharSetUtils.count("", (String) null), "empty input and null set");
        assertEquals(0, CharSetUtils.count("", ""), "empty input and empty set");
        assertEquals(0, CharSetUtils.count("", "a-e"), "empty input has no characters to count");

        assertEquals(0, CharSetUtils.count("hello", (String) null), "null set matches no characters");
        assertEquals(0, CharSetUtils.count("hello", ""), "empty set matches no characters");

        assertEquals(1, CharSetUtils.count("hello", "a-e"), "range a-e matches only 'e'");
        assertEquals(3, CharSetUtils.count("hello", "l-p"), "range l-p matches 'l', 'l', and 'o'");
    }
}
