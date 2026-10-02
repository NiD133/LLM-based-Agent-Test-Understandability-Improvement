package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Test
    void testCount_StringString() {
        // null string or null set always yields zero
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, ""));
        assertEquals(0, CharSetUtils.count("", (String) null));

        // empty string with any set yields zero
        assertEquals(0, CharSetUtils.count("", ""));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // non-empty string with null or empty set yields zero
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));

        // "hello" contains one character in the range a-e (only 'e')
        assertEquals(1, CharSetUtils.count("hello", "a-e"));

        // "hello" contains three characters in the range l-p ('l', 'l', 'o')
        assertEquals(3, CharSetUtils.count("hello", "l-p"));
    }
}
