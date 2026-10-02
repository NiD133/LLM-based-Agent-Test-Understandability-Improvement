package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_toString {

    @Test
    void test_toString() {
        // IOCase.toString() returns the human-readable name assigned to each constant
        assertEquals("Sensitive", IOCase.SENSITIVE.toString());
        assertEquals("Insensitive", IOCase.INSENSITIVE.toString());
        assertEquals("System", IOCase.SYSTEM.toString());
    }
}
