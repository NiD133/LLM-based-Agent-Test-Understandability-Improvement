package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testContainsAny_StringString extends AbstractLangTest {

    @Test
    void testContainsAny_StringString() {
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, ""));
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", ""));
        assertFalse(CharSetUtils.containsAny("", "a-e"));

        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertFalse(CharSetUtils.containsAny("hello", ""));

        assertTrue(CharSetUtils.containsAny("hello", "a-e"));
        assertTrue(CharSetUtils.containsAny("hello", "l-p"));
    }
}
