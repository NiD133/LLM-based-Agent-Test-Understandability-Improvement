package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_getName {

    @Test
    @DisplayName("getName() returns the human-readable label for each IOCase constant")
    void test_getName() {
        assertEquals("Sensitive",   IOCase.SENSITIVE.getName());
        assertEquals("Insensitive", IOCase.INSENSITIVE.getName());
        assertEquals("System",      IOCase.SYSTEM.getName());
    }
}
