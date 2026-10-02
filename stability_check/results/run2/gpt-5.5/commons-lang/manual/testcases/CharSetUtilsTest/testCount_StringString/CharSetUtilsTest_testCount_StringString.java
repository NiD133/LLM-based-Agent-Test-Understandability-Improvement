package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Test
    void testCount_StringString() {
        assertEquals(0, CharSetUtils.count(null, (String) null), "Null input with a null set should not count anything");
        assertEquals(0, CharSetUtils.count(null, ""), "Null input with an empty set should not count anything");

        assertEquals(0, CharSetUtils.count("", (String) null), "Empty input with a null set should not count anything");
        assertEquals(0, CharSetUtils.count("", ""), "Empty input with an empty set should not count anything");
        assertEquals(0, CharSetUtils.count("", "a-e"), "Empty input with a populated set should not count anything");

        assertEquals(0, CharSetUtils.count("hello", (String) null), "Null set should not match characters");
        assertEquals(0, CharSetUtils.count("hello", ""), "Empty set should not match characters");
        assertEquals(1, CharSetUtils.count("hello", "a-e"), "Range a-e should match only the 'e' in hello");
        assertEquals(3, CharSetUtils.count("hello", "l-p"), "Range l-p should match both 'l' characters and 'o'");
    }
}
