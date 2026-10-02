package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link IOCase#toString()} returns the human-readable name
 * of each enum constant.
 */
public class IOCaseTest_test_toString {

    @Test
    void test_toString() {
        assertEquals("Sensitive", IOCase.SENSITIVE.toString());
        assertEquals("Insensitive", IOCase.INSENSITIVE.toString());
        assertEquals("System", IOCase.SYSTEM.toString());
    }
}
