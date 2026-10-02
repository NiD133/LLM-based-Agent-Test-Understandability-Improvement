package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEquals_functionality {

    @Test
    void test_checkEquals_functionality() {
        // Partial matches (prefix/suffix/superstring) are not equal
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", ""));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "A"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "AB"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkEquals("", "ABC"));

        // Identical strings are equal
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEquals("", ""));

        // Null handling: null equals null, but null does not equal a non-null string
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkEquals(null, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEquals(null, null));
    }
}
