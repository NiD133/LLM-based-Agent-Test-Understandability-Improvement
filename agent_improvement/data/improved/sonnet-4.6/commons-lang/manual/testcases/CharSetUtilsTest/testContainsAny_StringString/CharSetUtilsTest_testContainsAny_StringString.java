package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testContainsAny_StringString extends AbstractLangTest {

    @Test
    void testContainsAny_StringString() {
        // null string input always returns false regardless of the set argument
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, ""));

        // empty string input always returns false regardless of the set argument
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", ""));
        assertFalse(CharSetUtils.containsAny("", "a-e"));

        // null or empty set argument always returns false regardless of the string
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertFalse(CharSetUtils.containsAny("hello", ""));

        // non-empty string with a matching character range returns true
        assertTrue(CharSetUtils.containsAny("hello", "a-e")); // 'e' is in range a-e
        assertTrue(CharSetUtils.containsAny("hello", "l-p")); // 'l', 'o' are in range l-p
    }
}
