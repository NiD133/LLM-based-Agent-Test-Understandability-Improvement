package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEndsWith_functionality {

    @Test
    void test_checkEndsWith_functionality() {
        final IOCase sensitiveCase = IOCase.SENSITIVE;

        assertTrue(sensitiveCase.checkEndsWith("ABC", ""));
        assertFalse(sensitiveCase.checkEndsWith("ABC", "A"));
        assertFalse(sensitiveCase.checkEndsWith("ABC", "AB"));
        assertTrue(sensitiveCase.checkEndsWith("ABC", "ABC"));
        assertTrue(sensitiveCase.checkEndsWith("ABC", "BC"));
        assertTrue(sensitiveCase.checkEndsWith("ABC", "C"));
        assertFalse(sensitiveCase.checkEndsWith("ABC", "ABCD"));

        assertFalse(sensitiveCase.checkEndsWith("", "ABC"));
        assertTrue(sensitiveCase.checkEndsWith("", ""));

        assertFalse(sensitiveCase.checkEndsWith("ABC", null));
        assertFalse(sensitiveCase.checkEndsWith(null, "ABC"));
        assertFalse(sensitiveCase.checkEndsWith(null, null));
    }
}
